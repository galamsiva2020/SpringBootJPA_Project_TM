package com.java.jpa.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java.jpa.springboot.model.Address;
import com.java.jpa.springboot.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByName(String name);

}
