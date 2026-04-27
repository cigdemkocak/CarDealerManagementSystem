package com.cigdemkocak.exception;

import lombok.Getter;

@Getter
public enum MessageType {
	
	NO_RECORD_EXIST("1004", "Kayıt bulunamadı"),
	TOKEN_IS_EXPIRED("1005", "Tokenın süresi bitmiştir"),
	USERNAME_NOT_FOUND("1006","Username bulunamadı"),
	USERNAME_OR_PASSWORD_INVALID("1007","Kullanıcı adı veya şifre hatalı"),
	REFRESH_TOKEN_NOT_FOUND("1008","refresh token bulunamadı"),
	REFRESH_TOKEN_IS_EXPIRED("1009","refresh tokenın süresi bitmiştir"),
	CURRENCY_RATES_IS_OCCURED("1010","döviz kuru alınamadı"),
	CUSTOMER_AMOUNT_IS_NOT_ENOUGH("1011","müşterinin parası yeterli değildir"),
	CAR_STATUS_IS_ALREADY_SALED("1012", "araba satılmış göründüğü için satılamaz"),
	GENERAL_EXCEPTION("9999", "Genel bir hata oluştu");
	
	private String code;
	
	private String message;
	
	MessageType(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
}
