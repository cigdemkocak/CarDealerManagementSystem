package com.cigdemkocak.service;

import com.cigdemkocak.dto.DtoAccount;
import com.cigdemkocak.dto.DtoAccountIU;

public interface IAccountService {

	public DtoAccount saveAccount(DtoAccountIU dtoAccountIU);
}
