package com.example.forgetdatafromapi;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@lombok.Data
@Entity
public class Data {

    @Id
    @GeneratedValue
    private Integer id;
    private String expand;
    private String key;
    private String releaseDate;
}