package com.oocl.packagebooking.controller;

import com.oocl.packagebooking.exception.NotWorkingTimeException;
import com.oocl.packagebooking.modle.Good;
import com.oocl.packagebooking.service.GoodService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    public void should_add_good_to_depository() throws Exception {
        Good good =createGood("1", "盆子", "1359546","未取件");

        when(goodService.saveGood(ArgumentMatchers.any())).thenReturn(good);

        ResultActions resultActions = mvc.perform(post("/goods").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "       \"goodId\":\"123456\"\n" +
                "    }"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.goodId", is("1")))
        .andExpect(jsonPath("$.customerName",is("盆子")));
    }

    @Test
    public void should_filter_goods_by_good_status() throws Exception {
        List<Good> goods = new ArrayList<>();
        goods.add(createGood("1", "盆子", "1359546","未取件"));


        when(goodService.filterGoodsByGoodStatus(anyString())).thenReturn(goods);

        ResultActions resultActions = mvc.perform(get("/goods").param("goodStatus","未取件"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].goodId", is("1")))
                .andExpect(jsonPath("$[0].customerName",is("盆子")));
    }

    @Test
    public void should_change_good_status() throws Exception {
        Good good =createGood("1", "盆子", "1359546","已取件");

        when(goodService.changeGoodStatus(ArgumentMatchers.any())).thenReturn(good);

        ResultActions resultActions = mvc.perform(put("/goods/{goodId}",good.getGoodId()).contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "       \"goodStatus\":\"已取件\"\n" +
                "    }"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.goodId", is("1")))
                .andExpect(jsonPath("$.customerName",is("盆子")))
                .andExpect(jsonPath("$.goodStatus",is("已取件")));
    }

    @Test
    public void should_reserve_good() throws Exception {
        Good good =createGood("1", "盆子", "1359546","未取件");
        long appointmentTime = System.currentTimeMillis();
        good.setAppointmentTime(appointmentTime);

        when(goodService.reserveGood(ArgumentMatchers.any())).thenReturn(good);

        ResultActions resultActions = mvc.perform(put("/goods").param("goodId","123").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "       \"appointmentTime\":\"2019\"\n" +
                "    }"));

        resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.goodId", is("1")))
                .andExpect(jsonPath("$.appointmentTime",is(appointmentTime)));
    }

    @Test
    public void should_throw_not_working_time_exception_when_reserve_good_in_break_time() throws Exception {

        when(goodService.reserveGood(ArgumentMatchers.any())).thenThrow(NotWorkingTimeException.class);

        ResultActions resultActions = mvc.perform(put("/goods").param("goodId","123").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "       \"appointmentTime\":\"2019\"\n" +
                "    }"));

        resultActions.andExpect(status().isBadRequest());
    }




    private Good createGood(String id, String name, String phoneNumber, String status ) {
        Good good = new Good();
        good.setGoodId(id);
        good.setCustomerName(name);
        good.setPhoneNumber(phoneNumber);
        good.setGoodStatus(status);
        return good;
    }


}
