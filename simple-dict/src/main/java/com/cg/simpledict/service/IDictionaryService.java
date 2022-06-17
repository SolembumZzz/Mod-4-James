package com.cg.simpledict.service;

public interface IDictionaryService {
    String outputResult(String keyword);

    boolean ifExists(String keyword);

    String beautifyKeyword(String keyword);
}
