package org.example.entity;

import org.example.enums.IsCompletedEnum;

import java.time.LocalDate;

public class TodoEntity {

    private Long userId;

    private String title;

    public TodoEntity(Long userId, String title) {
        this.userId = userId;
        this.title = title;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
