package com.zyf.entity;

import lombok.Data;

@Data
public class Book {
    int bid;
    String title;
    String description;
    double price;
}