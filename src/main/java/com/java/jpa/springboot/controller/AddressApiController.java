package com.java.jpa.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.java.jpa.springboot.model.Address;
import com.java.jpa.springboot.model.Department;
import com.java.jpa.springboot.service.AddressService;
import com.java.jpa.springboot.service.DepartmentService;
import com.java.jpa.springboot.util.CustomErrorType;

import java.util.List;

@RestController
@RequestMapping("/addApi")
public class AddressApiController {

	public static final Logger logger = LoggerFactory.getLogger(AddressApiController.class);

	@Autowired
	AddressService addressService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users method here look into below functionality---------------------------------------------

	@RequestMapping(value = "/add/", method = RequestMethod.GET)
	public ResponseEntity<List<Address>> listAllUsers() {
		List<Address> departments = addressService.findAllUsers();
		if (departments.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Address>>(departments, HttpStatus.OK);
	}

	// -------------------Retrieve Single getUser method here  look into below once------------------------------------------

	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		Address departmentServiceById = addressService.findById(id);
		if (departmentServiceById == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Address>(departmentServiceById, HttpStatus.OK);
	}

	// -------------------I Created a User method here please find below things once-------------------------------------------

	@RequestMapping(value = "/add/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Address department, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", department);

		if (addressService.isUserExist(department)) {
			logger.error("Unable to create. A User with name {} already exist", department.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			department.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		addressService.saveUser(department);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/addApi/add/{id}").buildAndExpand(department.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User Functionality here please chek ------------------------------------------------

	@RequestMapping(value = "/dep/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Department department) {
		logger.info("Updating User with id {}", id);

		Address currentUser = addressService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setName(department.getName());

		addressService.updateUser(currentUser);
		return new ResponseEntity<Address>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User functionality here please check once below-----------------------------------------

	@RequestMapping(value = "/add/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		Address user = addressService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		addressService.deleteUserById(id);
		return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users functionality here please look into below -----------------------------

	@RequestMapping(value = "/add/", method = RequestMethod.DELETE)
	public ResponseEntity<Address> deleteAllUsers() {
		logger.info("Deleting All Users");

		addressService.deleteAllUsers();
		return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
	}

}