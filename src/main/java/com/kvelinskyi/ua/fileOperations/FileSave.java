/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kvelinskyi.ua.fileOperations;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author IgorKv
 */
public class FileSave {

    /**
     *
     * @param stage
     * @param fileName
     */
    public void saveNewFile(Stage stage, FileData fileData, Map<String, String> mapTextResultSorting) {              
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(fileData.getFile().getParentFile());
        File file = fileChooser.showSaveDialog(stage);        
        if (file != null) {
            saveTextToFile(mapTextResultSorting, file);            
        }
    }

    private void saveTextToFile(Map<String, String> mapTextResultSorting, File file) {
         try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(mapTextResultSorting.get("words") + mapTextResultSorting.get("abbreviations"));
            writer.close();
        } catch (IOException ex) {
            
        }
    }
}
