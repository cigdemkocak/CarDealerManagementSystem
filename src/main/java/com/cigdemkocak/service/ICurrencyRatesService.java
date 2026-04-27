package com.cigdemkocak.service;

import com.cigdemkocak.dto.CurrencyRatesResponse;

public interface ICurrencyRatesService {

	public CurrencyRatesResponse getCurrencyRates(String startDate,String endDate);
}
