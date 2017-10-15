package com.zapeka.demo.exception;

/**
 * Created by Volodymyr on 12.10.2017.
 */
public class RestrictedWordException extends RuntimeException {
    public RestrictedWordException(String message) {
        super(message);
    }
}