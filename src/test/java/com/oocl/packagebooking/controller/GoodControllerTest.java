package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.modle.Good;
import com.oocl.packagebooking.service.GoodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoodController.class)
@ExtendWith(SpringExtension.class)
public class GoodControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GoodService goodService;

    @Test
    public void should_show_all_goods() throws Exception {
        List<Good> goods = new ArrayList<>();
        goods.add(createGood("1", "盆子", "1359546","未取件"));
        goods.add(createGood("2", "盆子2", "1359546","未取件"));

        when(goodService.findAll()).thenReturn(goods);

        ResultActions resultActions = mvc.perform(get("/goods"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    private Good createGood(String id, String name, String phoneNumber, String status ) {
        Good good = new Good();
        good.setGoodId(id);
        good.setCustomerName(name);
        good.setPhoneNumber(phoneNumber);
        good.setAppointmentTime(status);
        return good;
    }
}
