package com.adobe.prj.validation;

import com.adobe.prj.exception.JsonSchemaLoadingFailedException;
import com.adobe.prj.exception.JsonValidationFailedException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JsonSchemaValidator implements HandlerMethodArgumentResolver{
	
	private final ObjectMapper objectMapper;
	private final ResourcePatternResolver resourcePatternResolver;
	private final Map<String, JsonSchema> schemaCache;
	
	public JsonSchemaValidator(ObjectMapper objectMapper, ResourcePatternResolver resourcePatternResolver) {
		this.objectMapper = objectMapper;
		this.resourcePatternResolver = resourcePatternResolver;
		this.schemaCache = new ConcurrentHashMap<>();
	}
	
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(ValidJson.class) != null;
	}
	
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer , 
			NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory ) throws Exception {
		
		String schemaPath = methodParameter.getParameterAnnotation(ValidJson.class).value();
		
		JsonSchema schema = getJsonSchema(schemaPath);
		
		JsonNode json = objectMapper.readTree(getJsonPayload(nativeWebRequest));
		
		Set<ValidationMessage> validationResult = schema.validate(json);
		if(validationResult.isEmpty()) {
			return objectMapper.treeToValue(json, methodParameter.getParameterType());
		}
		
		throw new JsonValidationFailedException(validationResult);
	}
	
	private String getJsonPayload(NativeWebRequest nativeWebRequest) throws IOException {
		HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		 return StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
	}
	
	private JsonSchema getJsonSchema(String schemaPath) {
		return schemaCache.computeIfAbsent(schemaPath, path -> {
			Resource resource = resourcePatternResolver.getResource(path);
			if(!resource.exists()) {
				throw new JsonSchemaLoadingFailedException("Schema file does not exist " + path);
			}
			JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
			try(InputStream schemaStream = resource.getInputStream()){
				return schemaFactory.getSchema(schemaStream);
			} catch (Exception e) {
				throw new JsonSchemaLoadingFailedException("Error while loading schema " + path, e);
			}
		});
	}
}