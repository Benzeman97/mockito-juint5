package com.benz.junit.api.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String msg)
    {
        super(msg);
    }
}
