package com.frederik.appocoord;

import org.springframework.lang.NonNull;

public class User {
    @NonNull
    private String name;
    @NonNull
    private String email;

    public User(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
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
