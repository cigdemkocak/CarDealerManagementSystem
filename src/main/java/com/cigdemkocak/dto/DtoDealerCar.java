package com.cigdemkocak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoDealerCar extends DtoBase{

	private DtoCarDealer carDealer; 
	
	private DtoCar car;
}
