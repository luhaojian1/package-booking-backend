package com.oocl.packagebooking.repository;

import com.oocl.packagebooking.modle.Good;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GoodRepository extends JpaRepository<Good, String> {
}
