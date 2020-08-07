/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvelinskyi.ua.fileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author IgorKv
 */
public class FileReader {
    private FileData fileData;   
    private Stage stage;
    
    public FileData fileOpen() throws FileNotFoundException, IOException{
        fileData = new FileData();
        // Диалоговое окно чтения файла        
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) {
            //Save
           fileData.setFileAbsolutePath(file.getAbsolutePath());
            byte[] data;
            try (FileInputStream fis = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                fis.read(data);
            }
            //создаем стринговую переменную со всем текстом
            fileData.setFileText(new String(data, "UTF-8"));
            //создаем масив слов текста            
            fileData.setWords(fileData.getFileText().split(" "));
        }
        return fileData;
    }
}
