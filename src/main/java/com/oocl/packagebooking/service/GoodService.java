package com.oocl.packagebooking.service;

import com.oocl.packagebooking.exception.GoodTakeAwayException;
import com.oocl.packagebooking.exception.NotWorkingTimeException;
import com.oocl.packagebooking.modle.Good;
import com.oocl.packagebooking.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class GoodService {
    @Autowired
    private GoodRepository goodRepository;

    public List<Good> findAll() {
        return goodRepository.findAll();
    }

    public Good saveGood(Good good) {
        good.setGoodStatus("未取件");
        return goodRepository.save(good);
    }

    public List<Good> filterGoodsByGoodStatus(String goodStatus) {
        return goodRepository.findAllByGoodStatus(goodStatus);
    }

    public Good changeGoodStatus(Good good) {
        return goodRepository.save(good);
    }

    public Good reserveGood(Good good) {
        Calendar cale = Calendar.getInstance();
        cale.setTimeInMillis(good.getAppointmentTime());
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH);
        int day = cale.get(Calendar.DATE);
        Calendar startTime = Calendar.getInstance();
        startTime.set(year, month, day, 9, 0, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, 20, 0, 0);

        Good targetGood = goodRepository.findById(good.getGoodId()).get();
        boolean isValidDate = cale.after(startTime) && cale.before(endTime);
        boolean isFetchGood = targetGood.getGoodStatus().equals("已取件");
        if (isValidDate) {
            if (!isFetchGood) {
                targetGood.setGoodStatus("已预约");
                targetGood.setAppointmentTime(good.getAppointmentTime());
                return goodRepository.save(targetGood);
            } else {
                throw new GoodTakeAwayException();
            }

        }
        throw new NotWorkingTimeException();
    }
}
