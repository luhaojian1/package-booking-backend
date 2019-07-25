package com.oocl.packagebooking.repository;

import com.oocl.packagebooking.modle.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GoodRepository extends JpaRepository<Good, String> {
    public List<Good> findAllByGoodStatus(String goodStatus);
}
