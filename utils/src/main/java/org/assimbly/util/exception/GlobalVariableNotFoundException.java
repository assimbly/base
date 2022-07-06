package org.assimbly.util.exception;

public class GlobalVariableNotFoundException extends RuntimeException {

    public GlobalVariableNotFoundException() {}

    public GlobalVariableNotFoundException(String s) {
        super(s);
    }

    public GlobalVariableNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public GlobalVariableNotFoundException(Throwable throwable) {
        super(throwable);
    }

}
