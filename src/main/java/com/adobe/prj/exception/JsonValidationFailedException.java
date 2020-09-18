package com.adobe.prj.exception;

import java.util.Set;

import com.networknt.schema.ValidationMessage;

public class JsonValidationFailedException extends RuntimeException{
    private final Set<ValidationMessage> validationMessages;

    public JsonValidationFailedException(Set<ValidationMessage> validationMessages) {
        super("Json validation failed: " + validationMessages);
        this.validationMessages = validationMessages;
    }

    public Set<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }
}
