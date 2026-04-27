package com.cigdemkocak.controller;

import com.cigdemkocak.dto.AuthRequest;
import com.cigdemkocak.dto.AuthResponse;
import com.cigdemkocak.dto.DtoUser;
import com.cigdemkocak.dto.RefreshTokenRequest;

public interface IRestAuthenticationController {
	
	public RootEntity<DtoUser> register(AuthRequest input);
	
	public RootEntity<AuthResponse> authenticate(AuthRequest input);
	
	public RootEntity<AuthResponse> refreshToken(RefreshTokenRequest input);

}
