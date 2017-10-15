package com.zapeka.demo.service.impl;

import com.zapeka.demo.domain.Country;
import com.zapeka.demo.domain.Question;
import com.zapeka.demo.domain.RestrictedWord;
import com.zapeka.demo.exception.RestrictedWordException;
import com.zapeka.demo.repository.CountryRepository;
import com.zapeka.demo.repository.QuestionRepository;
import com.zapeka.demo.repository.RestrictedWordRepository;
import com.zapeka.demo.service.LocationService;
import com.zapeka.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Volodymyr on 12.10.2017.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private RestrictedWordRepository restrictedWordRepository;

    private CountryRepository countryRepository;

    private QuestionRepository questionRepository;

    private LocationService locationService;

    @Value("${zapeka.question.contain.restricted.word}")
    private String ERROR_MESSAGE;

    @Transactional(readOnly = false)
    @Override
    public Question addQuestion(String text, String ipAddress) {
        validateRestrictedWords(text);

        Country country = locationService.getOrCreateCountry(ipAddress);

        Question question = new Question();
        question.setContent(text);
        question.setCountry(country);
        question.setCreatedTime(LocalDateTime.now());

        return questionRepository.save(question);
    }

    @Override
    public List<Question> getQuestions(String countryCode, Integer offset, Integer limit) {
        if (countryCode == null || countryCode.length() == 0) {
            return questionRepository.findQuestionsByCountry(null, offset, limit);
        }
        Optional<Country> country = countryRepository.getCountryByCountryCode(countryCode);
        if (country.isPresent()) {
            return questionRepository.findQuestionsByCountry(country.get(), offset, limit);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public int getQuestionsCount(String countryCode) {
        if (countryCode == null || countryCode.length() == 0) {
            return questionRepository.countQuestionsByCountry(null);
        }
        Optional<Country> country = countryRepository.getCountryByCountryCode(countryCode);
        if (country.isPresent()) {
            return questionRepository.countQuestionsByCountry(country.get());
        } else {
            return 0;
        }
    }

    private void validateRestrictedWords(String text) {
        Iterable<RestrictedWord> restrictedWords = restrictedWordRepository.findAll();
        String[] words = text.split(" ");
        for (String word : words) {
            restrictedWords.forEach(restrictedWord -> {
                if (word.equals(restrictedWord.getWord())) {
                    throw new RestrictedWordException(ERROR_MESSAGE);
                }
            });
        }
    }

    @Autowired
    public void setRestrictedWordRepository(RestrictedWordRepository restrictedWordRepository) {
        this.restrictedWordRepository = restrictedWordRepository;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}
