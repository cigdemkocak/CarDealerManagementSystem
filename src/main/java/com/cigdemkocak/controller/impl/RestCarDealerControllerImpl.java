package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestCarDealerController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoCarDealerIU;
import com.cigdemkocak.service.ICarDealerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rest/api/carDealer")
public class RestCarDealerControllerImpl extends RestBaseController implements IRestCarDealerController{

	@Autowired
	private ICarDealerService carDealerService;
	
	@PostMapping("/save")
	@Override
	public RootEntity<DtoCarDealer> saveCarDealer(@Valid @RequestBody DtoCarDealerIU dtoCarDealerIU) {
		return ok(carDealerService.saveCarDealer(dtoCarDealerIU));
	}

}
