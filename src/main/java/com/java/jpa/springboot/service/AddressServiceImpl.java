package com.java.jpa.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.jpa.springboot.model.Address;
import com.java.jpa.springboot.model.User;
import com.java.jpa.springboot.repositories.AddressRepository;
import com.java.jpa.springboot.repositories.UserRepository;

import java.util.List;


@Service("addressService")
@Transactional
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository userRepository;

	public Address findById(Long id) {
		return userRepository.findOne(id);
	}

	public Address findByName(String name) {
		return userRepository.findByName(name);
	}

	public void saveUser(Address user) {
		userRepository.save(user);
	}

	public void updateUser(Address user){
		saveUser(user);
	}

	public void deleteUserById(Long id){
		userRepository.delete(id);
	}

	public void deleteAllUsers(){
		userRepository.deleteAll();
	}

	public List<Address> findAllUsers(){
		return userRepository.findAll();
	}

	public boolean isUserExist(Address user) {
		return findByName(user.getName()) != null;
	}

}
