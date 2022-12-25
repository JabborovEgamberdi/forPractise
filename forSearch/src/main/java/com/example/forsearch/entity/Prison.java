package com.example.forsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "prison")
//@Document(indexName = "blog" /*type = "prison"*/)
public class Prison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;

    private String city;

    private String prisonName;

    //    @JsonIgnoreProperties({"firstName", "lastName", "crimeCommit", "crimeTime"})
    //    @JsonIgnore
    //    @Field(type = FieldType.Nested, includeInParent = true)
    //    @OnDelete(action = OnDeleteAction.CASCADE)

    @OneToMany//(/*/*fetch = FetchType.EAGER*/ cascade = CascadeType.ALL)
    private List<Fraud> fraud;

//    public void addFraud(Fraud addFraud) {
//        fraud.add(addFraud);
//    }

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fraud_id", referencedColumnName = "fraud_id")
//    private List<Fraud> fraud;

}