package com.iprest.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IpAddress {
	
	public IpAddress() {}
	
	public IpAddress(String ipAddress, String status) {
		this.ipAddress = ipAddress;
		this.status = status;
	}
	
	@Id
	private String ipAddress;
	
	private String status;

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}