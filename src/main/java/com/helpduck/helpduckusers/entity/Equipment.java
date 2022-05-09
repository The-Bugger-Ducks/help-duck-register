package com.helpduck.helpduckusers.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document()
public class Equipment {
    
    @Id
    private String id;

    private String name;
    private String model;

    private String brand;
    private String type;
    private String department;


}
