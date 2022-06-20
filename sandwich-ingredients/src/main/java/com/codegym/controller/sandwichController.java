package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class sandwichController {

    @RequestMapping
    public String showHome() {
        return "index";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("ingredients") String[] ingredients) {
        ModelAndView mav = new ModelAndView("ingredients-list");

        mav.addObject("ingredients", ingredients);

        return mav;
    }
}
