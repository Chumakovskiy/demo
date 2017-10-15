package com.zapeka.demo.repository;

import com.zapeka.demo.domain.Country;
import com.zapeka.demo.domain.Question;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Volodymyr on 11.10.2017.
 */
public interface QuestionRepository {

    int getQuestionsCountFromCountryByTimeLimit(Country country, LocalDateTime nowSubtractInterval);

    List<Question> findQuestionsByCountry(Country country, int offset, int limit);

    Question save(Question question);

    int countQuestionsByCountry(Country country);
}