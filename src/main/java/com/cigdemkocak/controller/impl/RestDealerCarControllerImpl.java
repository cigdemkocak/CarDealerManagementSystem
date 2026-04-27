package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestDealerCarController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoDealerCar;
import com.cigdemkocak.dto.DtoDealerCarIU;
import com.cigdemkocak.service.IDealerCarService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/dealerCar")
public class RestDealerCarControllerImpl extends RestBaseController implements IRestDealerCarController{

	@Autowired
	private IDealerCarService dealerCarService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoDealerCar> saveDealerCar(@Valid @RequestBody DtoDealerCarIU dtoDealerCarIU) {
		return ok(dealerCarService.saveDealerCar(dtoDealerCarIU));
	}

}
