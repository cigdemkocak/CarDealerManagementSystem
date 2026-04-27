package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoCustomer;
import com.cigdemkocak.dto.DtoCustomerIU;

public interface ICustomerService {

	public DtoCustomer saveCustomer(DtoCustomerIU dtoCustomerIU);
}
