package org.assimbly.util.exception;

public class DovetailEnvironmentException extends RuntimeException{

    public DovetailEnvironmentException() {}

    public DovetailEnvironmentException(String s) {
        super(s);
    }

    public DovetailEnvironmentException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DovetailEnvironmentException(Throwable throwable) {
        super(throwable);
    }
}
