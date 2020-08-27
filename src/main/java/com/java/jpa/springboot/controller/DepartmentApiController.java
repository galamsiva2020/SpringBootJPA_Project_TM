package com.java.jpa.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.java.jpa.springboot.model.Department;
import com.java.jpa.springboot.model.User;
import com.java.jpa.springboot.service.DepartmentService;
import com.java.jpa.springboot.service.UserService;
import com.java.jpa.springboot.util.CustomErrorType;

import java.util.List;

@RestController
@RequestMapping("/depApi")
public class DepartmentApiController {

	public static final Logger logger = LoggerFactory.getLogger(DepartmentApiController.class);

	@Autowired
	DepartmentService departmentService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Users---------------------------------------------

	@RequestMapping(value = "/dep/", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> listAllUsers() {
		List<Department> departments = departmentService.findAllUsers();
		if (departments.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Department>>(departments, HttpStatus.OK);
	}

	// -------------------Retrieve Single User------------------------------------------

	@RequestMapping(value = "/dep/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		Department departmentServiceById = departmentService.findById(id);
		if (departmentServiceById == null) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id 
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Department>(departmentServiceById, HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/dep/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Department department, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", department);

		if (departmentService.isUserExist(department)) {
			logger.error("Unable to create. A User with name {} already exist", department.getName());
			return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " + 
			department.getName() + " already exist."),HttpStatus.CONFLICT);
		}
		departmentService.saveUser(department);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/depApi/dep/{id}").buildAndExpand(department.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User ------------------------------------------------

	@RequestMapping(value = "/dep/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Department department) {
		logger.info("Updating User with id {}", id);

		Department currentUser = departmentService.findById(id);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentUser.setName(department.getName());

		departmentService.updateUser(currentUser);
		return new ResponseEntity<Department>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/dep/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		Department user = departmentService.findById(id);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		departmentService.deleteUserById(id);
		return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/dep/", method = RequestMethod.DELETE)
	public ResponseEntity<Department> deleteAllUsers() {
		logger.info("Deleting All Users");

		departmentService.deleteAllUsers();
		return new ResponseEntity<Department>(HttpStatus.NO_CONTENT);
	}

}