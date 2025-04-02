package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.io.Serializable;

public class UserInternal implements Serializable {
    @NonNull
    private String fingerprint;
    @NonNull
    private String name;
    @NonNull
    private String email;

    @JsonCreator
    public UserInternal(@JsonProperty("fingerprint") @NonNull String fingerprint, @JsonProperty("name") @NonNull String name, @JsonProperty("email") @NonNull String email) {
        this.fingerprint = fingerprint;
        this.name = name;
        this.email = email;
    }

    @NonNull
    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(@NonNull String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }
}
