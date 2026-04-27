package com.cigdemkocak.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dealer_car", 
uniqueConstraints = {@UniqueConstraint(columnNames = {"car_dealer_id","car_id"},name ="uq_carDealer_car" )}) //Galericinin arabaları
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DealerCar extends BaseEntity{
	
	@ManyToOne
	private CarDealer carDealer;
	
	@ManyToOne
	private Car car;

}
