package com.example.forsearch.repository;

import com.example.forsearch.entity.Fraud;
import com.example.forsearch.entity.Prison;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrisonRepository extends JpaRepository<Prison, Integer> {

    @Query(value = "select fr from fraud as fr where id = ?1")
    Optional<Fraud> findByFraudId(Integer fraudId);

    Boolean existsByCountry(String country);

    Page<Prison> findAllByCountryContaining(String country, Pageable pageable);

}