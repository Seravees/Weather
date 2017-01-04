package com.qqq.androidweather4.util;

/**
 * Created by qqq on 2016-12-21.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
