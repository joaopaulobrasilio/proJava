package com.example.projava.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JWT {

    String username;
    String password;
    Long timestamp;
    List<String> roles;


    public JWT() {

    }

    public JWT(String username, String password, Long timestamp, List<String> roles) {
        this.username = username;
        this.password = password;
        this.timestamp = timestamp;
        this.roles = roles;
    }
}
