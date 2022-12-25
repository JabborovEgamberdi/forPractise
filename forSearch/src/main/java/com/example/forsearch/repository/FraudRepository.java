package com.example.forsearch.repository;

import com.example.forsearch.entity.Fraud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudRepository extends JpaRepository<Fraud, Integer> {

    Fraud findByLastNameAndFirstNameAndCrimeCommit(String lastName,
                                                   String firstName,
                                                   String crimeCommit);

}
