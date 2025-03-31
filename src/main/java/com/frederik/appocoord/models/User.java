package com.frederik.appocoord.models;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public class User implements Serializable {
    @NonNull private String fingerprint;
    @NonNull
    private String name;
    @NonNull
    private String email;

    public User(@NonNull String fingerprint, @NonNull String name, @NonNull String email) {
        this.fingerprint = fingerprint;
        this.name = name;
        this.email = email;
    }

    @NonNull
    public String getFingerprint() {
        return "";
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
