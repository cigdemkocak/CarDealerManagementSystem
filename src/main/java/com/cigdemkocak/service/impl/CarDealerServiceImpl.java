package com.cigdemkocak.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoCarDealerIU;
import com.cigdemkocak.exception.BaseException;
import com.cigdemkocak.exception.ErrorMessage;
import com.cigdemkocak.exception.MessageType;
import com.cigdemkocak.model.Address;
import com.cigdemkocak.model.CarDealer;
import com.cigdemkocak.repository.AddressRepository;
import com.cigdemkocak.repository.CarDealerRepository;
import com.cigdemkocak.service.ICarDealerService;

@Service
public class CarDealerServiceImpl implements ICarDealerService{
	
	@Autowired
	private CarDealerRepository carDealerRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	private CarDealer createCarDealer(DtoCarDealerIU dtoCarDealerIU) {
		
		Optional<Address> optAddress = addressRepository.findById(dtoCarDealerIU.getAddressId());
		if(optAddress.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, dtoCarDealerIU.getAddressId().toString()));
		}
		
		CarDealer carDealer = new CarDealer();
		carDealer.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoCarDealerIU, carDealer);
		carDealer.setAddress(optAddress.get());
		return carDealer;	
	}

	@Override
	public DtoCarDealer saveCarDealer(DtoCarDealerIU dtoCarDealerIU) {
		DtoCarDealer dtoCarDealer = new DtoCarDealer();
		DtoAddress dtoAddress = new DtoAddress();
		CarDealer savedCarDealer = carDealerRepository.save(createCarDealer(dtoCarDealerIU));
		BeanUtils.copyProperties(savedCarDealer, dtoCarDealer);
		BeanUtils.copyProperties(savedCarDealer.getAddress(), dtoAddress);
		
		dtoCarDealer.setAddress(dtoAddress);
		return dtoCarDealer;
	}

}
