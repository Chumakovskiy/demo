package com.zapeka.demo.service.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Volodymyr on 12.10.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerResponseData {
    public String country_code;

    public ServerResponseData() {
    }

    public ServerResponseData(String country_code) {
        this.country_code = country_code;
    }


}