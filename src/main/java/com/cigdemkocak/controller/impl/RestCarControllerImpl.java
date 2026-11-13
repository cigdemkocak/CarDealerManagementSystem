package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestCarController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarIU;
import com.cigdemkocak.service.ICarService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Car", description = "Car operations")
@RestController
@RequestMapping("/rest/api/car")
public class RestCarControllerImpl extends RestBaseController implements IRestCarController{
	
	@Autowired
	private ICarService carService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoCar> saveCar(@Valid @RequestBody DtoCarIU dtoCarIU) {
		return ok(carService.saveCar(dtoCarIU));
	}
	

}
