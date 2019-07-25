package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.modle.Good;
import com.oocl.packagebooking.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/goods")
    public List<Good> findAllGoods(){
        return goodService.findAll();
    }

    @PostMapping("/goods")
    public Good saveGood(@RequestBody Good good){
        return goodService.saveGood(good);
    }

    @GetMapping(value = "/goods", params = "goodStatus")
    public List<Good> filterGoodsByGoodStatus(@RequestParam String goodStatus){
        return goodService.filterGoodsByGoodStatus(goodStatus);
    }
}
