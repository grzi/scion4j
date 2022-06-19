package dev.grzi.scion.exception;

public class ScionRuntimeException extends RuntimeException {
    public ScionRuntimeException() {
    }

    public ScionRuntimeException(String message) {
        super(message);
    }
}
