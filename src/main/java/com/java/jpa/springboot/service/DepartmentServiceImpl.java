package com.java.jpa.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.jpa.springboot.model.Department;
import com.java.jpa.springboot.model.User;
import com.java.jpa.springboot.repositories.DepartmentRepository;
import com.java.jpa.springboot.repositories.UserRepository;

import java.util.List;


@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;

	public Department findById(Long id) {
		return departmentRepository.findOne(id);
	}

	public Department findByName(String name) {
		return departmentRepository.findByName(name);
	}

	public void saveUser(Department department) {
		departmentRepository.save(department);
	}

	public void updateUser(Department department){
		saveUser(department);
	}

	public void deleteUserById(Long id){
		departmentRepository.delete(id);
	}

	public void deleteAllUsers(){
		departmentRepository.deleteAll();
	}

	public List<Department> findAllUsers(){
		return departmentRepository.findAll();
	}

	public boolean isUserExist(Department department) {
		return findByName(department.getName()) != null;
	}

}
