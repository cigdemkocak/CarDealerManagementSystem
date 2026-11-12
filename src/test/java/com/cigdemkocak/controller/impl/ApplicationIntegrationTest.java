package com.cigdemkocak.controller.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.cigdemkocak.controller.RootEntity;
import com.cigdemkocak.dto.AuthRequest;
import com.cigdemkocak.dto.AuthResponse;
import com.cigdemkocak.dto.CurrencyRatesItems;
import com.cigdemkocak.dto.CurrencyRatesResponse;
import com.cigdemkocak.dto.DtoAccount;
import com.cigdemkocak.dto.DtoAccountIU;
import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoAddressIU;
import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoCarDealerIU;
import com.cigdemkocak.dto.DtoCarIU;
import com.cigdemkocak.dto.DtoCustomer;
import com.cigdemkocak.dto.DtoCustomerIU;
import com.cigdemkocak.dto.DtoDealerCar;
import com.cigdemkocak.dto.DtoDealerCarIU;
import com.cigdemkocak.dto.DtoSaledCar;
import com.cigdemkocak.dto.DtoSaledCarIU;
import com.cigdemkocak.dto.DtoUser;
import com.cigdemkocak.enums.CarStatusType;
import com.cigdemkocak.enums.CurrencyType;
import com.cigdemkocak.service.ICurrencyRatesService;
import com.cigdemkocak.starter.CarDealerManagementSystemApplicationStarter;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CarDealerManagementSystemApplicationStarter.class,
				webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class ApplicationIntegrationTest {
	
	String token;
	
    @Autowired
    TestRestTemplate rest;

    Long addressId, accountId, customerId, dealerId, carId, dealerCarId, dealerAddressId;
    
    @MockitoBean
    ICurrencyRatesService currencyRatesService;

    @BeforeEach
    void mockCurrencyRates() {
        CurrencyRatesItems item = new CurrencyRatesItems();
        item.setDate("24-07-2026");
        item.setUsd("47.14670000");

        CurrencyRatesResponse response = new CurrencyRatesResponse();
        response.setTotalCount(1);
        response.setItems(List.of(item));

        when(currencyRatesService.getCurrencyRates(any(), any())).thenReturn(response);
    }
	
	@Container
	@ServiceConnection
	private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15-alpine");
			
//	@DynamicPropertySource
//	static void overrideProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//		registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//		registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//	}
	
	@Test @Order(0)
	@DisplayName("0- The PostgreSQL container is created and is running")
	void testContainerIsRunning() {
		assertTrue(postgreSQLContainer.isCreated(), "PostgreSQL container has not been created");
		assertTrue(postgreSQLContainer.isRunning(), "PostgreSQL container is not running");
	}
	
	
	@Test @Order(1)
	@DisplayName("1- User can register")
	void testRegister_whenValidCredentialsProvided_returnsRegisteredUser() {
	    AuthRequest request = new AuthRequest();
	    request.setUsername("ckocak");
	    request.setPassword("1");

	    DtoUser user = post("/register", request, false, new ParameterizedTypeReference<RootEntity<DtoUser>>() {});

	    assertEquals("ckocak", user.getUsername());
	}
	

	@Test @Order(2)
	@DisplayName("2- User can authenticate and receive a token")
	void testAuthenticate_whenValidCredentialsProvided_returnsAccessToken() {
	    AuthRequest request = new AuthRequest();
	    request.setUsername("ckocak");
	    request.setPassword("1");

	    AuthResponse auth = post("/authenticate", request, false, new ParameterizedTypeReference<RootEntity<AuthResponse>>() {});

	    token = auth.getAccessToken();
	    assertFalse(token == null || token.isBlank(), "Token should not be empty");
	}
	
	@Test @Order(3)
	@DisplayName("3- Address can be saved")
	void testSaveAddress_whenValidDetailsProvided_returnsSavedAddress() {
	    DtoAddressIU request = new DtoAddressIU();
	    request.setCity("İstanbul");
	    request.setDistrict("Çekmeköy");
	    request.setNeighborhood("Ormanköy");
	    request.setStreet("Demir");

	    DtoAddress saved = post("/rest/api/address/save", request, true, new ParameterizedTypeReference<RootEntity<DtoAddress>>() {});

	    addressId = saved.getId();
	    assertTrue(addressId > 0);
	    assertEquals("İstanbul", saved.getCity());
	    assertEquals(request.getDistrict(), saved.getDistrict());
	    assertEquals(request.getNeighborhood(), saved.getNeighborhood());
	    assertEquals(request.getStreet(), saved.getStreet());
	}

	@Test @Order(4)
	@DisplayName("4- Account can be saved")
	void testSaveAccount_whenValidDetailsProvided_returnsSavedAccount() {
	    DtoAccountIU request = new DtoAccountIU();
	    request.setAccountNo("966896979");
	    request.setIban("TRXX 0001 5001 5800 7292 5642 28");
	    request.setAmount(new BigDecimal("50000000"));
	    request.setCurrencyType(CurrencyType.TL);

	    DtoAccount saved = post("/rest/api/account/save", request, true, new ParameterizedTypeReference<RootEntity<DtoAccount>>() {});

	    accountId = saved.getId();
	    assertTrue(accountId > 0);
	    assertEquals("966896979", saved.getAccountNo());
	    assertEquals(request.getIban(), saved.getIban());
	    assertEquals(0, request.getAmount().compareTo(saved.getAmount()), "Amount should match");
	    assertEquals(request.getCurrencyType(), saved.getCurrencyType());
	}

	@Test @Order(5)
	@DisplayName("5- Customer can be saved")
	void testSaveCustomer_whenValidDetailsProvided_returnsSavedCustomer() {
	    DtoCustomerIU request = new DtoCustomerIU();
	    request.setFirstName("Çiğdem");
	    request.setLastName("Koçak");
	    request.setTckn(randomTckn());
	    request.setBirthOfDate(java.sql.Date.valueOf("1997-06-29"));
	    request.setAddressId(addressId);
	    request.setAccountId(accountId);

	    DtoCustomer saved = post("/rest/api/customer/save", request, true, new ParameterizedTypeReference<RootEntity<DtoCustomer>>() {});

	    customerId = saved.getId();
	    assertTrue(customerId > 0);
	    assertEquals("Çiğdem", saved.getFirstName());
	    assertEquals(request.getLastName(), saved.getLastName());
	    assertEquals(request.getTckn(), saved.getTckn());
	    assertNotNull(saved.getAddress(), "Address should be returned");
	    assertEquals(addressId, saved.getAddress().getId());
	    assertNotNull(saved.getAccount(), "Account should be returned");
	    assertEquals(accountId, saved.getAccount().getId());
	}
	
	@Test @Order(6)
	@DisplayName("6- Car dealer can be saved")
	void testSaveCarDealer_whenValidDetailsProvided_returnsSavedCarDealer() {
	    dealerAddressId = createAddress();  

	    DtoCarDealerIU request = new DtoCarDealerIU();
	    request.setFirstName("Nilay");
	    request.setLastName("Koçak");
	    request.setAddressId(dealerAddressId);

	    DtoCarDealer saved = post("/rest/api/carDealer/save", request, true, new ParameterizedTypeReference<RootEntity<DtoCarDealer>>() {});

	    dealerId = saved.getId();
	    assertNotNull(dealerId, "Car dealer id should not be null");
	    assertTrue(dealerId > 0);
	    assertEquals(request.getFirstName(), saved.getFirstName());
	    assertEquals(request.getLastName(), saved.getLastName());
	    assertNotNull(saved.getAddress(), "Address should be returned");
	    assertEquals(dealerAddressId, saved.getAddress().getId());
	    assertNotEquals(addressId, saved.getAddress().getId(), "Dealer should have a different address than the customer");
	}
	
	@Test @Order(7)
	@DisplayName("7- Car can be saved")
	void testSaveCar_whenValidDetailsProvided_returnsSavedCar() {
	    DtoCarIU request = new DtoCarIU();
	    request.setPlaka("34 XYZ 574");
	    request.setBrand("Honda");
	    request.setModel("Civic");
	    request.setProductionYear(2020);
	    request.setPrice(new BigDecimal("20000"));
	    request.setCurrencyType(CurrencyType.USD);
	    request.setDamagePrice(new BigDecimal("6000"));
	    request.setCarStatusType(CarStatusType.SALABLE);

	    DtoCar saved = post("/rest/api/car/save", request, true, new ParameterizedTypeReference<RootEntity<DtoCar>>() {});

	    carId = saved.getId();
	    assertNotNull(carId, "Car id should not be null");
	    assertTrue(carId > 0);
	    assertEquals(request.getPlaka(), saved.getPlaka());
	    assertEquals(request.getBrand(), saved.getBrand());
	    assertEquals(request.getModel(), saved.getModel());
	    assertEquals(request.getProductionYear(), saved.getProductionYear());
	    assertEquals(0, request.getPrice().compareTo(saved.getPrice()), "Price should match");
	    assertEquals(request.getCurrencyType(), saved.getCurrencyType());
	    assertEquals(0, request.getDamagePrice().compareTo(saved.getDamagePrice()), "Damage price should match");
	    assertEquals(request.getCarStatusType(), saved.getCarStatusType());
	}
	
	@Test @Order(8)
	@DisplayName("8- Dealer car can be saved")
	void testSaveDealerCar_whenValidDetailsProvided_returnsSavedDealerCar() {
	    DtoDealerCarIU request = new DtoDealerCarIU();
	    request.setCarDealerId(dealerId);
	    request.setCarId(carId);

	    DtoDealerCar saved = post("/rest/api/dealerCar/save", request, true, new ParameterizedTypeReference<RootEntity<DtoDealerCar>>() {});

	    dealerCarId = saved.getId();
	    assertNotNull(dealerCarId, "Dealer car id should not be null");
	    assertTrue(dealerCarId > 0);

	    assertNotNull(saved.getCarDealer(), "Car dealer should be returned");
	    assertEquals(dealerId, saved.getCarDealer().getId());

	    assertNotNull(saved.getCar(), "Car should be returned");
	    assertEquals(carId, saved.getCar().getId());
	}
	
	@Test @Order(9)
	@DisplayName("9- Car can be bought")
	void testBuyCar_whenValidDetailsProvided_returnsSaledCar() {
	    DtoSaledCarIU request = new DtoSaledCarIU();
	    request.setCustomerId(customerId);
	    request.setCarDealerId(dealerId);
	    request.setCarId(carId);

	    DtoSaledCar saved = post("/rest/api/saled-car/save", request, true,  new ParameterizedTypeReference<RootEntity<DtoSaledCar>>() {});

	    assertNotNull(saved.getId(), "Saled car id should not be null");
	    assertTrue(saved.getId() > 0);

	    assertNotNull(saved.getCustomer(), "Customer should be returned");
	    assertEquals(customerId, saved.getCustomer().getId());

	    assertNotNull(saved.getCarDealer(), "Car dealer should be returned");
	    assertEquals(dealerId, saved.getCarDealer().getId());

	    assertNotNull(saved.getCar(), "Car should be returned");
	    assertEquals(carId, saved.getCar().getId());
	    assertEquals(CarStatusType.SALED, saved.getCar().getCarStatusType(), "Car should be marked as SALED after purchase");
	    
	    verify(currencyRatesService, atLeastOnce()).getCurrencyRates(any(), any());
	}
	
	@Test @Order(10)
	@DisplayName("10- Customer cannot be saved without a token")
	void testSaveCustomer_whenNoTokenProvided_returnsUnauthorizedOrForbidden() {
	    DtoCustomerIU request = new DtoCustomerIU();
	    request.setFirstName("Test");
	    request.setLastName("Kullanici");
	    request.setTckn(randomTckn());
	    request.setBirthOfDate(java.sql.Date.valueOf("1997-06-29"));
	    request.setAddressId(addressId);
	    request.setAccountId(accountId);

	    ResponseEntity<String> response = postRaw("/rest/api/customer/save", request, false);

	    assertTrue(
	        response.getStatusCode() == HttpStatus.UNAUTHORIZED
	            || response.getStatusCode() == HttpStatus.FORBIDDEN,
	        "Expected 401 or 403 but got " + response.getStatusCode());
	}

	@Test @Order(11)
	@DisplayName("11- Car dealer cannot be saved with an already used address")
	void testSaveCarDealer_whenAddressAlreadyUsed_returnsError() {
		
	    DtoCarDealerIU request = new DtoCarDealerIU();
	    request.setFirstName("Baska");
	    request.setLastName("Bayi");
	    request.setAddressId(dealerAddressId);

	    ResponseEntity<String> response = postRaw("/rest/api/carDealer/save", request, true);

	    assertFalse(response.getStatusCode().is2xxSuccessful(), "Same address should not be accepted for a second car dealer");
	}
	
	@Test @Order(12)
	@DisplayName("12- A sold car cannot be sold again")
	void testBuyCar_whenCarAlreadySold_returnsError() {
	    DtoSaledCarIU request = new DtoSaledCarIU();
	    request.setCustomerId(customerId);
	    request.setCarDealerId(dealerId);
	    request.setCarId(carId);   // 9. testte satıldı, durumu artık SALED

	    ResponseEntity<String> response = postRaw("/rest/api/saled-car/save", request, true);

	    assertFalse(response.getStatusCode().is2xxSuccessful(),
	            "A car with status SALED should not be sold a second time, but got " + response.getStatusCode());
	}
	
    
    private <T> T post(String url, Object body, boolean withToken, ParameterizedTypeReference<RootEntity<T>> responseType) {
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	if (withToken) {
    		headers.setBearerAuth(token);
    	}
    	ResponseEntity<RootEntity<T>> response = rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), responseType);

    	RootEntity<T> root = response.getBody();
    	assertTrue(response.getStatusCode().is2xxSuccessful(),
    			"POST " + url + " failed: HTTP " + response.getStatusCode() + (root != null ? ", errorMessage=" + root.getErrorMessage() : ""));
    	assertNotNull(root, "POST " + url + " returned an empty body");
    	assertNotNull(root.getPayload(), "POST " + url + " returned no payload, errorMessage=" + root.getErrorMessage());
    	return root.getPayload();
    }
    
	private ResponseEntity<String> postRaw(String url, Object body, boolean withToken) {
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    if (withToken) {
	        headers.setBearerAuth(token);
	    }
	    return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), String.class);
	}
    
    private Long createAddress() {
        DtoAddressIU request = new DtoAddressIU();
        request.setCity("İstanbul");
        request.setDistrict("Kadıköy");
        request.setNeighborhood("Moda");
        request.setStreet("Deniz");

        DtoAddress saved = post("/rest/api/address/save", request, true, new ParameterizedTypeReference<RootEntity<DtoAddress>>() {});
        return saved.getId();
    }
    
    private String randomTckn() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(10_000_000_000L, 99_999_999_999L));
    }

}
