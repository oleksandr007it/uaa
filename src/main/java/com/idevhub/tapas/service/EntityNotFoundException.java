package com.idevhub.tapas.service;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(entityClass.getSimpleName() + " not found by id=" + id.toString());
    }

    public EntityNotFoundException(Class<?> entityClass) {
        super(entityClass.getSimpleName() + " not found.");
    }
}
