package com.crusher.work.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class Page {
    private String result;

    public String getResult() {
        return result;
    }
}
