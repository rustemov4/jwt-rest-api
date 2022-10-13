package com.example.proj.request;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String price;
    private String description;
    private String image;
    private String category;
}
