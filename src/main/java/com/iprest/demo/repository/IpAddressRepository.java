package com.iprest.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.iprest.demo.entity.IpAddress;

public interface IpAddressRepository extends CrudRepository<IpAddress, Integer> {
	IpAddress findByIpAddress(String ipAddress);
}