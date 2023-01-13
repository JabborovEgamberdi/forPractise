package com.example.forsearch.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "prison")
@Document(indexName = "prisonindex")
public class Prison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Field(type = FieldType.Text, name = "country")
    private String country;

    @Field(type = FieldType.Text, name = "city")
    private String city;

    @Field(type = FieldType.Text, name = "prisonName")
    private String prisonName;

    @Field(type = FieldType.Nested, includeInParent = true)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Fraud> fraud;

}