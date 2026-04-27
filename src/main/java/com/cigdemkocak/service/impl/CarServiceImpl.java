package com.cigdemkocak.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cigdemkocak.dto.DtoCar;
import com.cigdemkocak.dto.DtoCarIU;
import com.cigdemkocak.model.Car;
import com.cigdemkocak.repository.CarRepository;
import com.cigdemkocak.service.ICarService;

@Service
public class CarServiceImpl implements ICarService{
	
	@Autowired
	private CarRepository carRepository;
	
	private Car createCar(DtoCarIU dtoCarUI) {
		Car car = new Car();
		car.setCreateTime(new Date());
		
		BeanUtils.copyProperties(dtoCarUI, car);
		return car;	
	}

	@Override
	public DtoCar saveCar(DtoCarIU dtoCarUI) {
		DtoCar dtoCar = new DtoCar();
		Car savedCar = carRepository.save(createCar(dtoCarUI));
		BeanUtils.copyProperties(savedCar, dtoCar);
		return dtoCar;
	}

}
