package com.cigdemkocak.service;

import com.cigdemkocak.dto.AuthRequest;
import com.cigdemkocak.dto.AuthResponse;
import com.cigdemkocak.dto.DtoUser;
import com.cigdemkocak.dto.RefreshTokenRequest;

public interface IAuthenticationService {

	public DtoUser register(AuthRequest input);
	
	public AuthResponse authenticate(AuthRequest input);
	
	public AuthResponse refreshToken(RefreshTokenRequest input);
}
