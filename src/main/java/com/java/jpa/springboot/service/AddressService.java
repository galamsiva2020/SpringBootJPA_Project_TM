package com.java.jpa.springboot.service;


import java.util.List;

import com.java.jpa.springboot.model.Address;
import com.java.jpa.springboot.model.User;

public interface AddressService {
	
	Address findById(Long id);

	Address findByName(String name);

	void saveUser(Address user);

	void updateUser(Address user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<Address> findAllUsers();

	boolean isUserExist(Address user);
}