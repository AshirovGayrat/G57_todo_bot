package org.example.entity;

import org.example.enums.UserStep;

public class UserEntity {

    private Long id;
    private String name;
    private String phone;
    private String password;
    private UserStep userStep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStep getUserStep() {
        return userStep;
    }

    public void setUserStep(UserStep userStep) {
        this.userStep = userStep;
    }
}
