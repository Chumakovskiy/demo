package com.zapeka.demo.exception;

/**
 * Created by Volodymyr on 12.10.2017.
 */
public class TooManyQuestionsFromSingleCountry extends RuntimeException {
    public TooManyQuestionsFromSingleCountry(String message) {
        super(message);
    }
}
