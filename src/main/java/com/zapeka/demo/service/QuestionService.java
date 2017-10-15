package com.zapeka.demo.service;

import com.zapeka.demo.domain.Question;

import java.util.List;

/**
 * Created by Volodymyr on 12.10.2017.
 */
public interface QuestionService {

    Question addQuestion(String text, String ipAddress);

    List<Question> getQuestions(String countryCode, Integer offset, Integer limit);

    int getQuestionsCount(String countryCode);
}
