package com.example.td3.presentation.presentation.model;

import com.example.td3.presentation.presentation.model.Discographie;

import java.util.List;

public class RestDiscographieResponse {

    private Integer count;
    private List<Discographie> results;

    public Integer getCount() {
        return count;
    }

    public List<Discographie> getResults() {
        return results;
    }
}
