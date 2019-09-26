package com.iprest.demo.controller;

import org.apache.commons.net.util.SubnetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iprest.demo.entity.IpAddress;
import com.iprest.demo.repository.IpAddressRepository;

@RestController
@RequestMapping(path="/api")
public class IpAddressController {
	@Autowired
	private IpAddressRepository ipAddressRepository;

	//Create IP addresses
	@PostMapping(path="/createIpAddresses")
	public String createIpAddreses(@RequestBody String CIDRBlockIn) {
		try {
			SubnetUtils subnetUtils = new SubnetUtils(CIDRBlockIn);
			subnetUtils.setInclusiveHostCount(true);
			String[] allAddresses = subnetUtils.getInfo().getAllAddresses();
			for(int i=0;i<allAddresses.length;i++) {
				if(ipAddressRepository.findByIpAddress(allAddresses[i]) == null){
					ipAddressRepository.save(new IpAddress(allAddresses[i], "available"));
				}
			}
			return "SUCCESSFUL";
		}catch(IllegalArgumentException e) {
			System.out.println(e);
			return "ILLEGAL ARGUMENT";
		}
	}
	
	//List IP addresses
	@GetMapping(path="/getAllIpAddresses")
	public Iterable<IpAddress> getAllIpAddresses() {
		return ipAddressRepository.findAll();
	}
	
	//Acquire an IP
	@PostMapping(path="/acquireIpAddress")
	public String acquireIpAddress(@RequestBody String strIn) {
		if(strIn == null || strIn == "") {
			return "NO IP ADDRESS GIVEN";
		}
				
		IpAddress ipAddressTemp = ipAddressRepository.findByIpAddress(strIn);
		
		if(ipAddressTemp == null) {
			return "IP ADDRESS NOT FOUND";
		}
		
		if(ipAddressTemp.getStatus().equals("acquired")) {
			return "ALREADY ACQURIED";
		}else {
			ipAddressTemp.setStatus("acquired");
			ipAddressRepository.save(ipAddressTemp);
			return "SUCCESSFUL";
		}
	}
	
	//Release an IP
	@PostMapping(path="/releaseIpAddress")
	public String releaseIpAddress(@RequestBody String strIn) {
		if(strIn == null || strIn == "") {
			return "NO IP ADDRESS GIVEN";
		}
		
		IpAddress ipAddressTemp = ipAddressRepository.findByIpAddress(strIn);
		
		if(ipAddressTemp == null) {
			return "IP ADDRESS NOT FOUND";
		}
		
		if(ipAddressTemp.getStatus().equals("available")) {
			return "ALREADY AVAILABLE";
		}else {
			ipAddressTemp.setStatus("available");
			ipAddressRepository.save(ipAddressTemp);
			return "SUCCESSFUL";
		}
	}
}