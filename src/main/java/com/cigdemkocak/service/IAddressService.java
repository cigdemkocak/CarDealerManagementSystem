package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoAddressIU;

public interface IAddressService {
	
	public DtoAddress saveAddress(DtoAddressIU dtoAddressIU);
	
}
