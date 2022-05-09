package com.helpduck.helpduckusers.repository;

import com.helpduck.helpduckusers.entity.Equipment;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipmentRepository extends MongoRepository<Equipment, String> {
}