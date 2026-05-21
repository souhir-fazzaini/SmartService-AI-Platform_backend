package com.example.serviceplatform.dto;

import lombok.Data;

@Data
public class ReviewResponse {

    private Long id;
    private String username;
    private Long serviceId;
    private int rating;
    private String comment;


}
