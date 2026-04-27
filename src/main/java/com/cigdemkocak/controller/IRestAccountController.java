package com.cigdemkocak.controller;

import com.cigdemkocak.dto.DtoAccount;
import com.cigdemkocak.dto.DtoAccountIU;

public interface IRestAccountController {

	public RootEntity<DtoAccount> saveAccount(DtoAccountIU dtoAccountIU);
}
