package com.adobe.prj.exception;

public class JsonSchemaLoadingFailedException extends RuntimeException {

    public JsonSchemaLoadingFailedException(String message) {
        super(message);
    }

    public JsonSchemaLoadingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}