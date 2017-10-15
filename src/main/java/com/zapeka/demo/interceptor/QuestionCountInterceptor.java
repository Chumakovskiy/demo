package com.zapeka.demo.interceptor;

import com.zapeka.demo.domain.Country;
import com.zapeka.demo.exception.TooManyQuestionsFromSingleCountry;
import com.zapeka.demo.repository.QuestionRepository;
import com.zapeka.demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

/**
 * Created by Volodymyr on 12.10.2017.
 */
public class QuestionCountInterceptor extends HandlerInterceptorAdapter {

    private QuestionRepository questionRepository;

    private LocationService locationService;

    @Value("${zapeka.count.allowed.questions}")
    private Integer allowedQuestionsCountFromSingleCountry;

    @Value("${zapeka.time.interval}")
    private Integer interval;      //in seconds

    @Value("${zapeka.count.allowed.questions.exceeded.message}")
    private String ERROR_MESSAGE;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethod().getName().equals("addQuestion") && request != null) {
                Optional<Country> country = locationService.getCountry(request.getRemoteAddr());
                if (country.isPresent()) {
                    LocalDateTime nowSubtractInterval = LocalDateTime.now().minus(interval, ChronoUnit.SECONDS);
                    int actualQuestionsCountFromSingleCountry = questionRepository.getQuestionsCountFromCountryByTimeLimit(country.get(), nowSubtractInterval);
                    if (actualQuestionsCountFromSingleCountry > allowedQuestionsCountFromSingleCountry) {
                        throw new TooManyQuestionsFromSingleCountry("Allowed number of questions from your country exceeded. Wait for some time.");
                    }
                }
            }
        }

        return true;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }
}
