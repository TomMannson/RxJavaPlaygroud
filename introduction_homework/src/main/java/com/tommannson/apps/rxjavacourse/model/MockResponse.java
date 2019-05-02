
package com.tommannson.apps.rxjavacourse.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MockResponse {

    @SerializedName("configuration")
    @Expose
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

}
