package com.cg.currencyconverter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
public class ConverterController {

    @GetMapping("/")
    public ModelAndView showConverter() {
        return new ModelAndView("index");
    }

    @GetMapping("/doTheThing")
    public ModelAndView convert(@RequestParam BigDecimal original) {
        ModelAndView mav = new ModelAndView("index");

        BigDecimal converted = original.multiply(new BigDecimal("0.000043"));

        mav.addObject("original", original);
        mav.addObject("converted", converted);
        return mav;
    }
}
