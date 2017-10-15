package com.zapeka.demo.repository.impl;

import com.zapeka.demo.domain.Country;
import com.zapeka.demo.domain.Question;
import com.zapeka.demo.repository.QuestionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Volodymyr on 13.10.2017.
 */
@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Question save(Question question) {
        entityManager.persist(question);
        entityManager.flush();
        return question;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getQuestionsCountFromCountryByTimeLimit(Country country, LocalDateTime nowSubtractInterval) {
        Query query = entityManager.createQuery("select count(q.id) from Question q where q.country = :COUNTRY and q.createdTime > :NOW_SUBTRACT_INTERVAL");
        query.setParameter("COUNTRY", country);
        query.setParameter("NOW_SUBTRACT_INTERVAL", nowSubtractInterval);

        return (int) query.getSingleResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Question> findQuestionsByCountry(Country country, int offset, int limit) {
        TypedQuery<Question> query = entityManager.createQuery("select q from Question q where :COUNTRY is null or q.country = :COUNTRY order by q.createdTime", Question.class);
        query.setParameter("COUNTRY", country);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int countQuestionsByCountry(Country country) {
        Query query = entityManager.createQuery("select count(q.id) from Question q where :COUNTRY is null or q.country = :COUNTRY");
        query.setParameter("COUNTRY", country);

        Long result = (Long) query.getSingleResult();
        return result.intValue();
    }
}