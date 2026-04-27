package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarIU;

public interface ICarService {

	public DtoCar saveCar(DtoCarIU dtoCarUI);
}
