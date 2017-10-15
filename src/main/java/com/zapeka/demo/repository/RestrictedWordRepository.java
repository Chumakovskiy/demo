package com.zapeka.demo.repository;

import com.zapeka.demo.domain.RestrictedWord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Volodymyr on 11.10.2017.
 */
public interface RestrictedWordRepository extends JpaRepository<RestrictedWord, Integer> {

}
