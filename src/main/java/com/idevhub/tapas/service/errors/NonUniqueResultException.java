package com.idevhub.tapas.service.errors;

public class NonUniqueResultException extends RuntimeException {

    public NonUniqueResultException(int resultCount, Class<?> entityClass) {
        super(String.format("%d %s objects with the same number code are present in the result set", resultCount, entityClass.getSimpleName()));
    }
}
