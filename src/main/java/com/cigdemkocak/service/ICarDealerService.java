package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoCarDealerIU;

public interface ICarDealerService {

	public DtoCarDealer saveCarDealer(DtoCarDealerIU dtoCarDealerIU);
}
