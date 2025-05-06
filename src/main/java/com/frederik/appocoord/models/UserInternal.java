package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
public class UserInternal {
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
}
