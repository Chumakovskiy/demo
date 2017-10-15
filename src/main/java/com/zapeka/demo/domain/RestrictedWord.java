package com.zapeka.demo.domain;

import javax.persistence.*;

/**
 * Created by Volodymyr on 11.10.2017.
 */
@Entity
@Table(name = "RESTRICTED_WORD")
public class RestrictedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "word", nullable = false, updatable = false)
    private String word;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RestrictedWord that = (RestrictedWord) o;

        if (!id.equals(that.id)) {
            return false;
        }
        return word.equals(that.word);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + word.hashCode();
        return result;
    }
}
