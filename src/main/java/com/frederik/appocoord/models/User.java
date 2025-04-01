package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.SpringContext;
import org.springframework.lang.NonNull;

import java.io.*;

public class User extends UserInternal implements Serializable {
    public User(@NonNull String fingerprint, @NonNull String name, @NonNull String email) {
        super(fingerprint, name, email);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        String id = SpringContext.getBean(RedisService.class).createOrUpdate(super.getFingerprint(), new UserInternal(super.getFingerprint(), super.getName(), super.getEmail()));
        out.writeObject(id);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String id = (String) in.readObject();
        UserInternal data = (UserInternal) SpringContext.getBean(RedisService.class).getData(id);
        this.setFingerprint(data.getFingerprint());
        this.setName(data.getName());
        this.setEmail(data.getEmail());
    }

    @JsonIgnore
    public void censor(String fingerprint) {
        if (!fingerprint.equals(this.getFingerprint())) {
            this.setFingerprint("");
        }
    }
}