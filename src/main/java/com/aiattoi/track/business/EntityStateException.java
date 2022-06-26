package com.aiattoi.track.business;

public class EntityStateException extends RuntimeException {
    public <E> EntityStateException(E entity) {
        super("Illegal state of entity " + entity);
    }
}
