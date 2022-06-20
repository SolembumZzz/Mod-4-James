package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.TimeZone;

@Controller
public class TimeController {

    @GetMapping("/world-clock")
    public String getTimeByZone(ModelMap model, @RequestParam(name = "city", required = false,
            defaultValue = "Asia/Ho_Chi_Minh") String city) {
        Date date = new Date();
        TimeZone localTimeZone = TimeZone.getDefault();
        TimeZone locale = TimeZone.getTimeZone(city);

        long locale_time = date.getTime() + (locale.getRawOffset() - localTimeZone.getRawOffset());
        date.setTime(locale_time);

        model.addAttribute("city", city);
        model.addAttribute("date", date);

        return "index";
    }

}
