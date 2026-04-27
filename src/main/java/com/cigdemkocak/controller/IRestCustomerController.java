package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoCustomer;
import com.cigdemkocak.dto.DtoCustomerIU;

public interface IRestCustomerController {

	public RootEntity<DtoCustomer> saveCustomer(DtoCustomerIU dtoCustomerIU);
}
