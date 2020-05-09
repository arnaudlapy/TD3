package com.example.td3.data;

import com.example.td3.presentation.presentation.model.Discographie;
import java.util.List;

public interface DiscCallback {

    void onSuccess(List<Discographie> response);
    void onFailed();
}


