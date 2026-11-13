package com.cigdemkocak.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cigdemkocak.controller.IRestCustomerController;
import com.cigdemkocak.controller.RestBaseController;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.DtoCustomer;
import com.cigdemkocak.dto.DtoCustomerIU;
import com.cigdemkocak.service.ICustomerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Customer", description = "Customer operations")
@RestController
@RequestMapping("/rest/api/customer")
public class RestCustomerControllerImpl extends RestBaseController implements IRestCustomerController{
	
	@Autowired
	private ICustomerService customerService;


	@PostMapping("/save")
	@Override
	public RootEntity<DtoCustomer> saveCustomer(@Valid @RequestBody DtoCustomerIU dtoCustomerIU) {
		return ok(customerService.saveCustomer(dtoCustomerIU));
	}

}
