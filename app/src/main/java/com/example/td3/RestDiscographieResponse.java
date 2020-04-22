package com.example.td3;

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
