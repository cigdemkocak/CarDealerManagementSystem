package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestAddressController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoAddressIU;
import com.cigdemkocak.service.IAddressService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Address", description = "Address operations")
@RestController
@RequestMapping("/rest/api/address")
public class RestAddressControllerImpl extends RestBaseController implements IRestAddressController{
	
	@Autowired
	private IAddressService addressService;

	@PostMapping("/save")
	@Override
	public RootEntity<DtoAddress> savedAddress(@Valid @RequestBody DtoAddressIU dtoAddressIU) {
		return ok(addressService.saveAddress(dtoAddressIU));
	}

}
