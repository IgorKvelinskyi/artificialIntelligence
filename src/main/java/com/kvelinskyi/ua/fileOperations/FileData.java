/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvelinskyi.ua.fileOperations;

import lombok.Data;

/**
 *
 * @author IgorKv
 */
@Data
public class FileData {    
    private String fileAbsolutePath;    
    private String fileText;
    private String[] words;    
}
