package com.cg.simpledict.service;


import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DictionaryService implements IDictionaryService {
    static HashMap<String, String> simpleDict = new HashMap<>();
    
    static {
        simpleDict.put("teenager", "thieu nien");
        simpleDict.put("mutant", "dot bien");
        simpleDict.put("ninja", "nin-ja");
        simpleDict.put("turtle", "con rua");
    }

    @Override
    public String outputResult(String keyword) {
        return simpleDict.get(beautifyKeyword(keyword));
    }

    @Override
    public boolean ifExists(String keyword) {
        return outputResult(beautifyKeyword(keyword)) != null;
    }

    @Override
    public String beautifyKeyword(String keyword) {
        return keyword.trim().toLowerCase();
    }
}