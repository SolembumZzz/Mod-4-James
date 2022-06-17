package com.cg.simpledict.controlelr;

import com.cg.simpledict.service.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DictionaryController {
    @Autowired
    IDictionaryService dictionaryService;

    @GetMapping("/")
    public ModelAndView showDict() {
        return new ModelAndView("index");
    }

    @GetMapping("/translate")
    public ModelAndView getResult(@RequestParam String keyword) {
        ModelAndView mav = new ModelAndView("index");

        if (!dictionaryService.ifExists(keyword)) {
            mav.addObject("result", "Result not found");
        } else {
            String result = dictionaryService.outputResult(keyword);
            mav.addObject("result", result);
        }

        mav.addObject("keyword", keyword);

        return mav;
    }
}
