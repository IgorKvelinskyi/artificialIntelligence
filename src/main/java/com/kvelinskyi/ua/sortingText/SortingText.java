/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvelinskyi.ua.sortingText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author IgorKv
 */
public class SortingText {

    SimpleDateFormat formating = new SimpleDateFormat("mm:ss:SSS");

    /**
     *
     * @param words
     * @return
     */
    public List<String> removingTheSigns(String[] words) {
        List<String> list = new ArrayList<>();
        for (String word : words) {
            word = word.replaceAll("[^\\p{L}\\p{Z}]","");
//            word = word.replace(",", "");
//            word = word.replace("(", "");
//            word = word.replace(")", "");
//            word = word.replace(".", "");
//            word = word.replace("\"", "");
//            word = word.replace("!", "");
//            word = word.replace("?", "");
//            word = word.replace(":", "");
//            word = word.replace(";", "");
//            word = word.replace(" ", "");
//            word = word.replace(" ", "");
//            word = word.replace("’", "");
//            word = word.replace("”", "");
//            word = word.replace("—", " ");
//            word = word.replace("-", " ");
//            word = word.replace("“", "");
//            word = word.replace("•", "");
//            word = word.replace("'", "");
//            word = word.replace("_", "");
//            word = word.replace("  ", " ");
//            word = word.replace("\n", "");
            list.add(word);
        }
        return list;
    }

    /**
     *
     * @param list
     * @param xValue
     * @return
     */
    public List<String> removeWordsLessThanXValueLetters(List<String> list, int xValue) {
        List<String> listNew;
        listNew = new ArrayList<>();
        list.stream().filter(wordNew -> (wordNew.length() >= xValue)).forEach(x -> listNew.add(x));
        return listNew;
    }

    /**
     *
     * @param list
     * @return
     */
    public WordsAndAbbreviations separatingWordsAndAbbreviations(List<String> list) {
        WordsAndAbbreviations wordsAndAbbreviations = new WordsAndAbbreviations();
        List<String> listAbbreviations = new ArrayList<>();
        List<String> listWords = new ArrayList<>();
        list.stream().filter(word -> isCheckingForAbbreviation(word)).forEach(x -> listAbbreviations.add(x));
        wordsAndAbbreviations.setAbbreviations(listAbbreviations);
        list.stream().filter(word -> {
            return !isCheckingForAbbreviation(word);
        }).forEach(x -> listWords.add(x));
        wordsAndAbbreviations.setWords(listWords);
        return wordsAndAbbreviations;
    }

    /**
     *
     * @param mapWords
     * @param valueWordCount
     * @return
     */
    public Map<String, Long> deletElemetByValueLessForMap(Map<String, Long> mapWords, int valueWordCount) {
        Map<String, Long> map = new HashMap<>();
        mapWords.entrySet().stream().filter(entry -> (entry.getValue() > valueWordCount)).forEachOrdered(entry -> {
            map.put(entry.getKey(), entry.getValue());
        });
        return map;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> mapSortAscendingByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();        
        map.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public <K, V extends Comparable<? super V>> Map<K, V> mapSortReversedByValue(Map<K, V> map) {
        return map.entrySet().stream().sorted((Map.Entry.<K, V>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     *
     * @param inputList
     * @return
     */
    public Map<String, Long> countDuplicates(List<String> inputList) {
        return inputList.stream().collect(Collectors.toMap(Function.identity(), v -> 1L, Long::sum));
    }

    /**
     * метод проверки слова на аббревиатуру
     *
     * @param word
     * @return
     */
    private boolean isCheckingForAbbreviation(String word) {
        Pattern p = Pattern.compile("[A-Z][A-Z]+");
        Matcher m = p.matcher(word);
        return m.matches();
    }
}
