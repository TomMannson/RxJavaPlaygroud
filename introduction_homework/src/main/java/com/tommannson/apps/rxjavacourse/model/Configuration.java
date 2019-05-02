
package com.tommannson.apps.rxjavacourse.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configuration {

    @SerializedName("disabled")
    @Expose
    private Boolean disabled;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("endpoint")
    @Expose
    private String endpoint;

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

}
