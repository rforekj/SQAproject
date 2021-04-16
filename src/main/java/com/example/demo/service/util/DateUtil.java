package com.example.demo.service.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateUtil {
    public static Date getTheFirstDayOfNextMonth() {
        LocalDate d = LocalDate.now();
        return Date.from(d.withDayOfMonth(d.getMonth().length(d.isLeapYear())).plusDays(1).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date getTheFirstDayOfThisMonth() {
        LocalDate d = LocalDate.now();
        return Date.from(d.withDayOfMonth(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getTheLastDayOfPreviousMonth() {
        return Date.from(LocalDate.now().withDayOfMonth(1).minusDays(1).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date getTheLastDayOfThisMonth() {
        LocalDate d = LocalDate.now();
        return Date.from(d.withDayOfMonth(d.getMonth().length(d.isLeapYear())).atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
