package com.cigdemkocak.controller;

import com.cigdemkocak.dto.CurrencyRatesResponse;

public interface IRestCurrencyRatesController {
	
	public RootEntity<CurrencyRatesResponse> getCurrencyRates(String startDate, String endDate);

}
