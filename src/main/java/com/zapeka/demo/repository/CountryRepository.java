package com.zapeka.demo.repository;

import com.zapeka.demo.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by Volodymyr on 11.10.2017.
 */
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> getCountryByCountryCode(String countryCode);
}
