package org.pi.demo.Exceptions;
public class SamePasswordException extends Exception {
    public SamePasswordException(String message) {
        super(message);
    }
}
