package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GreetingController {
    @RequestMapping(value = "greeting", method = RequestMethod.POST)
    public String greeting(@RequestParam String name, Model model) {
        model.addAttribute("name", name);
        return "greeting/index";
    }
}