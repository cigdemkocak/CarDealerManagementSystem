package com.cigdemkocak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cigdemkocak.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
