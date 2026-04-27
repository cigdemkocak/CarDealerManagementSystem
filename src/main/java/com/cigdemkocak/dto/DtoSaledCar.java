package com.cigdemkocak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoSaledCar extends DtoBase{

	private DtoCustomer customer;
	
	private DtoCarDealer carDealer;
	
	private DtoCar car;
}
