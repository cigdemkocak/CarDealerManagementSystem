package com.cigdemkocak.controller.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cigdemkocak.config.AppConfig;
import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.controller.impl.RestCustomerControllerImpl;
import com.cigdemkocak.dto.DtoAccount;
import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoCustomer;
import com.cigdemkocak.dto.DtoCustomerIU;
import com.cigdemkocak.exception.BaseException;
import com.cigdemkocak.exception.ErrorMessage;
import com.cigdemkocak.exception.MessageType;
import com.cigdemkocak.model.Address;
import com.cigdemkocak.service.ICustomerService;
import com.cigdemkocak.starter.CarDealerManagementSystemApplicationStarter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

@WebMvcTest(controllers = RestCustomerControllerImpl.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class},
excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = AppConfig.class))
@ContextConfiguration(classes = {RestCustomerControllerImpl.class})
//@AutoConfigureMockMvc(addFilters = false)
public class RestCustomerControllerWebLayerTest {
	
	@MockitoBean
	private ICustomerService customerService;
	
	@Autowired
	private MockMvc mockMvc;
	
    private DtoCustomerIU dtoCustomerIU;

    @BeforeEach
    void setUp() throws Exception {
    	dtoCustomerIU = new DtoCustomerIU();
    	dtoCustomerIU.setFirstName("cigdem");
    	dtoCustomerIU.setLastName("kocak");
    	dtoCustomerIU.setTckn("12345678910");
    	dtoCustomerIU.setBirthOfDate(new SimpleDateFormat("yyyy-MM-dd").parse("1997-06-29"));
    	dtoCustomerIU.setAddressId(1L);
    	dtoCustomerIU.setAccountId(1L);
    }
	
	@Test
	@DisplayName("Customer can be created")
	void testCreateCustomer_whenValidCustomerDetailsProvided_returnsCreatedCustomerDetails() throws Exception {
		//Arrange

		DtoCustomer mockResponse = new DtoCustomer();
		mockResponse.setFirstName(dtoCustomerIU.getFirstName());
		mockResponse.setLastName(dtoCustomerIU.getLastName());
		mockResponse.setTckn(dtoCustomerIU.getTckn());
		mockResponse.setBirthOfDate(dtoCustomerIU.getBirthOfDate());

		DtoAddress dtoAddress = new DtoAddress();
		dtoAddress.setId(dtoCustomerIU.getAddressId());
		mockResponse.setAddress(dtoAddress);

		DtoAccount dtoAccount = new DtoAccount();
		dtoAccount.setId(dtoCustomerIU.getAccountId());
		mockResponse.setAccount(dtoAccount);

		when(customerService.saveCustomer(any(DtoCustomerIU.class))).thenReturn(mockResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(dtoCustomerIU));
		
		
		//Act
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String responseBodyAsString = mvcResult.getResponse().getContentAsString();
		RootEntity<DtoCustomer> createdCustomer = new ObjectMapper().readValue(
			    responseBodyAsString,
			    new TypeReference<RootEntity<DtoCustomer>>() {}
			);
		
		//Assert
		Assertions.assertEquals(200, createdCustomer.getStatus());
		Assertions.assertNotNull(createdCustomer.getPayload());
		Assertions.assertEquals(dtoCustomerIU.getFirstName(), createdCustomer.getPayload().getFirstName(), "The returned customer first name is most likely  incorrect");
		Assertions.assertEquals(dtoCustomerIU.getLastName(), createdCustomer.getPayload().getLastName(), "The returned customer last name is most likely  incorrect");
		Assertions.assertEquals(dtoCustomerIU.getTckn(), createdCustomer.getPayload().getTckn(), "The returned customer tckn is most likely  incorrect");
		Assertions.assertEquals(dtoCustomerIU.getBirthOfDate(), createdCustomer.getPayload().getBirthOfDate(), "The returned customer birth of date is most likely  incorrect");
	    Assertions.assertEquals(dtoCustomerIU.getAddressId(), createdCustomer.getPayload().getAddress().getId(),"The returned customer address is most likely  incorrect");
	    Assertions.assertEquals(dtoCustomerIU.getAccountId(), createdCustomer.getPayload().getAccount().getId(), "The returned customer account is most likely  incorrect");
		
	}
	
	@Test
	@DisplayName("First name is not empty")
	void testCreateCustomer_whenFirstNameIsNotProvided_returns400StatusCode() throws Exception {

	    dtoCustomerIU.setFirstName("");

	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(dtoCustomerIU));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),"Incorrect HTTP Status Code returned");
		
		verify(customerService, never()).saveCustomer(any(DtoCustomerIU.class));
	}
	
	@Test
	@DisplayName("Last name is not empty")
	void testCreateCustomer_whenLastNameIsNotProvided_returns400StatusCode() throws Exception {
		
		dtoCustomerIU.setLastName("");

	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(dtoCustomerIU));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),"Incorrect HTTP Status Code returned");
		
		verify(customerService, never()).saveCustomer(any(DtoCustomerIU.class));

	}
	
	
	@Test
	@DisplayName("Tckn name is not empty")
	void testCreateCustomer_whenTcknIsNotProvided_returns400StatusCode() throws Exception {
		
	    dtoCustomerIU.setTckn("");

	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(dtoCustomerIU));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),"Incorrect HTTP Status Code returned");
		
		verify(customerService, never()).saveCustomer(any(DtoCustomerIU.class));

	}
	
	@Test
	@DisplayName("Address id is null")
	void testCreateCustomer_whenAddressIdIsNull_returns400StatusCode() throws Exception {
		
		dtoCustomerIU.setAddressId(null);
		
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(dtoCustomerIU));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),"Incorrect HTTP Status Code returned");
		
		verify(customerService, never()).saveCustomer(any(DtoCustomerIU.class));


	}

	@Test
	@DisplayName("Account id is null")
	void testCreateCustomer_whenAccountIdIsNull_returns400StatusCode() throws Exception {

		dtoCustomerIU.setAccountId(null);
	    
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rest/api/customer/save")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(dtoCustomerIU));

		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(),"Incorrect HTTP Status Code returned");
		
		verify(customerService, never()).saveCustomer(any(DtoCustomerIU.class));

	}
	
}
