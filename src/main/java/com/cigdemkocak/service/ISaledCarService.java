package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoSaledCar;
import com.cigdemkocak.dto.DtoSaledCarIU;

public interface ISaledCarService {

	public DtoSaledCar buyCar(DtoSaledCarIU dtoSaledCarIU);
}
