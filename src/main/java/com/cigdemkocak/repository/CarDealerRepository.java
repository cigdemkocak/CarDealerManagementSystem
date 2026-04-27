package com.cigdemkocak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cigdemkocak.model.CarDealer;

@Repository
public interface CarDealerRepository extends JpaRepository<CarDealer, Long>{

}
