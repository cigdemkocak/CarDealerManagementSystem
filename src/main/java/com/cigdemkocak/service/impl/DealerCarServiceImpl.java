package com.cigdemkocak.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cigdemkocak.dto.DtoAddress;
import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarDealer;
import com.cigdemkocak.dto.DtoDealerCar;
import com.cigdemkocak.dto.DtoDealerCarIU;
import com.cigdemkocak.exception.BaseException;
import com.cigdemkocak.exception.ErrorMessage;
import com.cigdemkocak.exception.MessageType;
import com.cigdemkocak.model.Car;
import com.cigdemkocak.model.CarDealer;
import com.cigdemkocak.model.DealerCar;
import com.cigdemkocak.repository.CarDealerRepository;
import com.cigdemkocak.repository.CarRepository;
import com.cigdemkocak.repository.DealerCarRepository;
import com.cigdemkocak.service.IDealerCarService;

@Service
public class DealerCarServiceImpl implements IDealerCarService{
	
	@Autowired
	private DealerCarRepository dealerCarRepository;
	
	@Autowired
	private CarDealerRepository carDealerRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	private DealerCar createDealerCar(DtoDealerCarIU dtoDealerCarIU) {
		Optional<CarDealer> optCarDealer = carDealerRepository.findById(dtoDealerCarIU.getCarDealerId());
		if(optCarDealer.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoDealerCarIU.getCarDealerId().toString()));
		}
		Optional<Car> optCar = carRepository.findById(dtoDealerCarIU.getCarId());
		if(optCar.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,dtoDealerCarIU.getCarId().toString()));
		}
		
		DealerCar dealerCar = new DealerCar();
		dealerCar.setCreateTime(new Date());
		dealerCar.setCarDealer(optCarDealer.get());
		dealerCar.setCar(optCar.get());
		return dealerCar;
	}

	@Override
	public DtoDealerCar saveDealerCar(DtoDealerCarIU dtoDealerCarIU) {
		DtoDealerCar dtoDealerCar = new DtoDealerCar();
		DtoCarDealer dtoCarDealer = new DtoCarDealer();
		DtoCar dtoCar = new DtoCar();
		
		DtoAddress dtoAddress = new DtoAddress();
		
		DealerCar savedDealerCar =  dealerCarRepository.save(createDealerCar(dtoDealerCarIU));
		BeanUtils.copyProperties(savedDealerCar, dtoDealerCar);
		BeanUtils.copyProperties(savedDealerCar.getCarDealer(), dtoCarDealer);
		BeanUtils.copyProperties(savedDealerCar.getCarDealer().getAddress(), dtoAddress);
		BeanUtils.copyProperties(savedDealerCar.getCar(), dtoCar);
		
		dtoCarDealer.setAddress(dtoAddress);
		
		dtoDealerCar.setCarDealer(dtoCarDealer);
		dtoDealerCar.setCar(dtoCar);
		return dtoDealerCar;
	}

}
