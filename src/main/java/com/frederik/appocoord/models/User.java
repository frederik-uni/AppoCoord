package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

public class User extends UserInternal {
    public User(@NonNull String fingerprint, @NonNull String name, @NonNull String email) {
        super(fingerprint, name, email);
    }

    @JsonIgnore
    public void censor(String fingerprint) {
        if (!fingerprint.equals(this.getFingerprint())) {
            this.setFingerprint("");
        }
    }
}