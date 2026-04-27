package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoCarDealerIU;

public interface IRestCarDealerController {
	
	public RootEntity<DtoCarDealer> saveCarDealer(DtoCarDealerIU dtoCarDealerIU);

}
