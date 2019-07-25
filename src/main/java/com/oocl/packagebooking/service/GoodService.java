package com.oocl.packagebooking.service;

import com.oocl.packagebooking.modle.Good;
import com.oocl.packagebooking.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;

    public List<Good> findAll() {
        return goodRepository.findAll();
    }

    public Good saveGood(Good good) {
        return goodRepository.save(good);
    }

    public List<Good> filterGoodsByGoodStatus(String goodStatus) {
        return goodRepository.findAllByGoodStatus(goodStatus);
    }

    public Good changeGoodStatus(Good good) {
        return goodRepository.save(good);
    }

    public Good reserveGood(Good good) {
        return goodRepository.save(good);
    }
}
