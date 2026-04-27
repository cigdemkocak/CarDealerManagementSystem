package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoSaledCar;
import com.cigdemkocak.dto.DtoSaledCarIU;

public interface IRestSaledCarController {

	public RootEntity<DtoSaledCar> buyCar(DtoSaledCarIU dtoSaledCarIU);
}
