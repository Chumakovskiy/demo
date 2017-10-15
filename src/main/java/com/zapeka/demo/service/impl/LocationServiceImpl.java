package com.zapeka.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zapeka.demo.domain.Country;
import com.zapeka.demo.repository.CountryRepository;
import com.zapeka.demo.service.LocationService;
import com.zapeka.demo.service.util.ServerResponseData;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Created by Volodymyr on 12.10.2017.
 */
@Service
public class LocationServiceImpl implements LocationService {
    final static Logger logger = Logger.getLogger(LocationServiceImpl.class);

    private CloseableHttpAsyncClient httpClient;

    private CountryRepository countryRepository;

    private Country defaultCountry;

    @Value("${zapeka.default.country.code}")
    private String DEFAULT_COUNTRY_CODE;

    public LocationServiceImpl() {
        httpClient = HttpAsyncClients.createDefault();
        httpClient.start();
    }

    @Transactional(readOnly = false)
    @Override
    public Country getOrCreateCountry(String ipAddress) {
        //TODO подумать где инициализировать defaultCountry
        if (defaultCountry == null) {
            defaultCountry = countryRepository.getCountryByCountryCode(DEFAULT_COUNTRY_CODE).get();
        }

        if (StringUtils.isEmpty(ipAddress)) {
            return defaultCountry;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpGet getRequest = new HttpGet("http://freegeoip.net/json/" + ipAddress);
            Future<HttpResponse> future = httpClient.execute(getRequest, null);
            HttpResponse response = future.get();
            String entity = EntityUtils.toString(response.getEntity());
            ServerResponseData data = objectMapper.readValue(entity, ServerResponseData.class);
            if (data.country_code != null && data.country_code.length() > 0) {
                Optional<Country> country = countryRepository.getCountryByCountryCode(data.country_code.toLowerCase());
                if (country.isPresent()) {
                    return country.get();
                } else {
                    Country newCountry = new Country();
                    newCountry.setCountryCode(data.country_code.toLowerCase());
                    return countryRepository.save(newCountry);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not retrieve data from remote service", ex);
        }
        return defaultCountry;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Country> getCountry(String ipAddress) {
        if (StringUtils.isEmpty(ipAddress)) {
            return Optional.empty();
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpGet getRequest = new HttpGet("http://freegeoip.net/json/" + ipAddress);
            Future<HttpResponse> future = httpClient.execute(getRequest, null);
            HttpResponse response = future.get();
            String entity = EntityUtils.toString(response.getEntity());
            ServerResponseData data = objectMapper.readValue(entity, ServerResponseData.class);
            if (data.country_code != null && data.country_code.length() > 0) {
                return countryRepository.getCountryByCountryCode(data.country_code.toLowerCase());
            }
        } catch (Exception ex) {
            logger.error("Could not retrieve data from remote service", ex);
        }
        return Optional.empty();
    }

    @Autowired
    public void setCountryRepository(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}