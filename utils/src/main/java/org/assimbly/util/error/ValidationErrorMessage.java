package org.assimbly.util.error;

public class ValidationErrorMessage {

    private String error;

    public ValidationErrorMessage() {
    }

    public ValidationErrorMessage(String error) {
        this.error = error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationErrorMessage that = (ValidationErrorMessage) o;

        if (error != null ? !error.equals(that.error) : that.error != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
