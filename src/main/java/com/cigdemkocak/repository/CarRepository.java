package com.cigdemkocak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cigdemkocak.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

}
