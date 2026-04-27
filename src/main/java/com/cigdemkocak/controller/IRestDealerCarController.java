package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoDealerCar;
import com.cigdemkocak.dto.DtoDealerCarIU;

public interface IRestDealerCarController {

	public RootEntity<DtoDealerCar> saveDealerCar(DtoDealerCarIU dtoDealerCarIU);
}
