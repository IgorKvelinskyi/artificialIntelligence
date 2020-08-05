package com.kvelinskyi.ua;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class PrimaryController {

    @FXML
    private TextField output;
    @FXML
    private Stage stage;
    @FXML
    private TextArea textArea;
    private String fileText;
    private String[] words;

    @FXML
    private void saveText() throws IOException {
        try {
            textArea.appendText("----------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFile() throws IOException {
        // Диалоговое окно чтения файла        
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Open Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);//Указываем текущую сцену CodeNote.mainStage
        if (file != null) {
            //Save
            output.setText(file.getAbsolutePath());
            byte[] data;
            try (FileInputStream fis = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                fis.read(data);
            }
            //создаем стринговую переменную со всем текстом
            fileText = new String(data, "UTF-8");
            textArea.appendText(fileText);
        }
        // output.setText("Hellow");
    }

    @FXML
    private void sortingText() throws IOException {
        words = fileText.split(" ");
        textArea.clear();
        textArea.appendText(String.valueOf(words.length));

    }
}
