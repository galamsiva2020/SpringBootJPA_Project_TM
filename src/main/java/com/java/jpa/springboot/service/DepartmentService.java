package com.java.jpa.springboot.service;


import java.util.List;

import com.java.jpa.springboot.model.Department;
import com.java.jpa.springboot.model.User;

public interface DepartmentService {
	
	Department findById(Long id);

	Department findByName(String name);

	void saveUser(Department department);

	void updateUser(Department department);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<Department> findAllUsers();

	boolean isUserExist(Department department);
}