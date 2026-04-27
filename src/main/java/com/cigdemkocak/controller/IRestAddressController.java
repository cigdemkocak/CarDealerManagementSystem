package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoAddressIU;

public interface IRestAddressController {
	
	public RootEntity<DtoAddress> savedAddress(DtoAddressIU dtoAddressIU);

}
