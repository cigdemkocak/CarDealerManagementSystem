package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarIU;

public interface IRestCarController {

	public RootEntity<DtoCar> saveCar(DtoCarIU dtoCarIU);
}
