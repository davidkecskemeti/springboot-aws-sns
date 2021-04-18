package com.dk.aws.sns.dto;

import lombok.Data;

@Data
public class Subscription {
    private String protocol;
    private String value;
}
