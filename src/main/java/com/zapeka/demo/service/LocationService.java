package com.zapeka.demo.service;

import com.zapeka.demo.domain.Country;

import java.util.Optional;

/**
 * Created by Volodymyr on 12.10.2017.
 */
public interface LocationService {

    Country getOrCreateCountry(String ipAddress);

    Optional<Country> getCountry(String ipAddress);
}
