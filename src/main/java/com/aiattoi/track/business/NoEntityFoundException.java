package com.aiattoi.track.business;

public class NoEntityFoundException extends RuntimeException {

    public NoEntityFoundException(String entityName) {
        super(entityName + " not found.");
    }
}
