package com.zapeka.demo.domain;

import javax.persistence.*;

/**
 * Created by Volodymyr on 11.10.2017.
 */
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_code", nullable = false, updatable = false)
    private String countryCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Country country = (Country) o;

        if (!id.equals(country.id)) {
            return false;
        }
        return countryCode.equals(country.countryCode);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + countryCode.hashCode();
        return result;
    }
}
