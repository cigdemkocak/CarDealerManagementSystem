package com.cigdemkocak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoCarDealer extends DtoBase{
	
	private String firstName;

	private String lastName;

	private DtoAddress address;
	
}
