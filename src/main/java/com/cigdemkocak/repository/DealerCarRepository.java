package com.cigdemkocak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cigdemkocak.model.DealerCar;

@Repository
public interface DealerCarRepository extends JpaRepository<DealerCar, Long>{

}
