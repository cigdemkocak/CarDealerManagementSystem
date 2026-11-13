package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestSaledCarController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoSaledCar;
import com.cigdemkocak.dto.DtoSaledCarIU;
import com.cigdemkocak.service.ISaledCarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Car Sales", description = "Car sales transactions")
@RestController
@RequestMapping("/rest/api/saled-car")
public class RestSaledCarContollerImpl extends RestBaseController implements IRestSaledCarController{
	
	@Autowired
	private ISaledCarService saledCarService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoSaledCar> buyCar(@Valid @RequestBody DtoSaledCarIU dtoSaledCarIU) {
		return ok(saledCarService.buyCar(dtoSaledCarIU));
	}
	

}
