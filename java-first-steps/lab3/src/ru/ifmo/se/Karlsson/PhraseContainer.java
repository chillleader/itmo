package ru.ifmo.se.Karlsson;

import java.util.HashMap;

public class PhraseContainer {

    private HashMap<String, String> c = new HashMap<>();
    String defMale;
    String defFem;

    public void add(String key, String phrase) {
        c.put(key, phrase);
    }

    public StringBuffer getPhrase(String key, boolean isMale) {
        return new StringBuffer(c.getOrDefault(key, isMale ? defMale : defFem));
    }
}
