package com.zapeka.demo.api;

import com.zapeka.demo.domain.Question;
import com.zapeka.demo.exception.RestrictedWordException;
import com.zapeka.demo.exception.TooManyQuestionsFromSingleCountry;
import com.zapeka.demo.service.LocationService;
import com.zapeka.demo.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Volodymyr on 11.10.2017.
 */
@RestController
@RequestMapping(value = "/api/1.0/rest/questions",
        consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class QuestionController {

    private QuestionService questionService;
    private LocationService locationService;

    @Value("${zapeka.question.empty}")
    private String ERROR_MESSAGE;

    /**
     * @param info
     * @return
     */
    @PostMapping("")
    public QuestionInfo addQuestion(@RequestBody QuestionManageInfo info, HttpServletRequest request) {
        if (StringUtils.isEmpty(info.text)) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
        return toQuestionInfo(questionService.addQuestion(info.text, request.getRemoteAddr()));
    }

    /**
     * @param countryCode
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("")
    public List<QuestionInfo> getQuestions(@RequestParam(value = "countryCode", required = false) String countryCode,
                                           @RequestParam(value = "offset", required = false) Integer offset,
                                           @RequestParam(value = "limit", required = false) Integer limit) {
        offset = offset == null ? 0 : offset;
        limit = limit == null ? 100 : limit;

        List<Question> questions = questionService.getQuestions(countryCode, offset, limit);
        return questions.stream().map(QuestionController::toQuestionInfo).collect(Collectors.toList());
    }

    /**
     * @param countryCode
     * @return
     */
    @GetMapping("/count")
    public int getQuestionCount(@RequestParam(value = "countryCode", required = false) String countryCode) {
        return questionService.getQuestionsCount(countryCode);
    }

    private static QuestionInfo toQuestionInfo(Question question) {
        QuestionInfo result = new QuestionInfo();

        result.id = question.getId();
        result.text = question.getContent();
        result.countryCode = question.getCountry().getCountryCode();
        result.createdTime = question.getCreatedTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        return result;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * Exceptions
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Question contains at least one restricted word.")
    @ExceptionHandler(RestrictedWordException.class)
    private void restrictedWord() {

    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Allowed number of questions from your country exceeded. Wait for some time.")
    @ExceptionHandler(TooManyQuestionsFromSingleCountry.class)
    private void manyQuestions() {

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid argument.")
    @ExceptionHandler(RuntimeException.class)
    public void invalidArgument() {

    }
}
