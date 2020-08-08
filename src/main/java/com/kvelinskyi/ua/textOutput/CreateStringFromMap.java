/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvelinskyi.ua.textOutput;

import java.util.Map;
import lombok.Data;

/**
 *
 * @author IgorKv
 */
@Data
public class CreateStringFromMap {
    
    /**
     *
     * @param map
     * @return
     */
    public String outputString(Map<String, Long> map){
        String outputString = new String();        
        return map.entrySet().stream()
                    .map(entry -> "\n" + entry.getKey() + "--" + entry.getValue())
                    .reduce(outputString, String::concat);
    }
}
