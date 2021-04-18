package com.dk.aws.sns.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Notification implements Serializable {
    private String message;
}
