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
public class FileReaderAndSaveData {        
       
    /**
     *
     * @param stage
     * @return
     */
    public File fileOpen(Stage stage) {
        // Диалоговое окно чтения файла        
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(stage);//Указываем текущую сцену 

    }

    /**
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public FileData saveDataFromFile(File file) throws FileNotFoundException, IOException {
        FileData fileData = new FileData();
        if (file != null) {
            //Save file absolute path
            fileData.setFileAbsolutePath(file.getAbsolutePath());
            byte[] data;
            try (FileInputStream fis = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                fis.read(data);
            }
            //Create a string variable with all the text
            fileData.setFileText(new String(data, "UTF-8"));
            //Create an array of text words         
            fileData.setWords(fileData.getFileText().split(" "));
        }
        return fileData;
    }
}
