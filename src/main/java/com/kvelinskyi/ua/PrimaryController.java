package com.kvelinskyi.ua;

import com.kvelinskyi.ua.fileOperations.FileData;
import com.kvelinskyi.ua.fileOperations.FileOpenAndSaveData;
import com.kvelinskyi.ua.fileOperations.FileSave;
import com.kvelinskyi.ua.sortingText.SortingText;
import com.kvelinskyi.ua.sortingText.WordsAndAbbreviations;
import com.kvelinskyi.ua.textOutput.CreateStringFromMap;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField textFieldFilePath;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField textFieldMinValue;
    @FXML
    private TextField textFieldMinNamberLetters;
    private Stage stage;
    private FileData fileData;
    private final SimpleDateFormat formating = new SimpleDateFormat("mm:ss:SSS");
    private Map<String, Long> mapSortDuplicateWords;
    private Map<String, Long> mapSortDuplicateAbbreviations;
    private Map<String, String> mapTextResultSorting;

    @FXML
    private void textOutput() throws IOException {
        if (mapSortDuplicateWords != null) {
            CreateStringFromMap createStringFromMap = new CreateStringFromMap();
            mapTextResultSorting = new HashMap<String, String>();
            //TODO КЛЮЧИ СДЕЛАТЬ ENUM  со значением для mapTextResultSorting  -------
            mapTextResultSorting.put("words", createStringFromMap.outputString(mapSortDuplicateWords));
            mapTextResultSorting.put("abbreviations", createStringFromMap.outputString(mapSortDuplicateAbbreviations));
            textArea.appendText("\n" + "------------------WORDS--------------------------");
            textArea.appendText(mapTextResultSorting.get("words"));
            textArea.appendText("\n" + "------------------ABBREVIATIONS-------------------");
            textArea.appendText(mapTextResultSorting.get("abbreviations"));
        } else {
            try {
                if (fileData == null || fileData.getFileText() == null) {
                    textArea.clear();
                    textArea.appendText("\n" + "--------+------OPEN FILE!!!------+-------");
                } else {
                    textArea.appendText("\n" + "--------++-----Sorting Text!!!-----++-------");
                }
            } catch (NullPointerException e) {
                textArea.clear();
                textArea.appendText("\n" + "--------+++----OPEN FILE!!!------+++-----");
            }
        }
    }

    @FXML
    private void buttonOpenFile() throws IOException {
        try {
            FileOpenAndSaveData fileReaderAndSaveData = new FileOpenAndSaveData();
            fileData = fileReaderAndSaveData.saveDataFromFile(fileReaderAndSaveData.fileOpen(stage));
            textFieldFilePath.setText(fileData.getFileAbsolutePath());
            textArea.clear();
            textArea.appendText(fileData.getFileText());
        } catch (NullPointerException e) {
            textArea.clear();
            textArea.appendText("\n" + "+-------------+OPEN FILE!!!+------------+");
        }
    }

    @FXML
    private void buttonSaveFile() throws IOException {
        FileSave fileSave = new FileSave();
        try {
            if (fileData.getFileAbsolutePath() != null) {
                if (mapTextResultSorting != null) {
                    fileSave.saveNewFile(stage, fileData, mapTextResultSorting);
                } else {
                    textArea.appendText("\n" + "----ENTER BUTTON (Text output)----------");
                } 
            } else {
                textArea.clear();
                textArea.appendText("\n" + "+-----------+++OPEN FILE!!!+++----------+");
            }
        } catch (NullPointerException e) {
            textArea.clear();
            textArea.appendText("\n" + "+-----------+++OPEN FILE!!!+++----------+");
        }
    }

    @FXML
    private void buttonSortingText() throws IOException {
        textArea.clear();
        SortingText sortingText = new SortingText();
        try {
            if (fileData != null || fileData.getFileText() != null) {
                textArea.appendText("\n" + "Word count in the text = " + Integer.toString(fileData.getWords().length));
                List<String> listWords = sortingText.removingTheSigns(fileData.getWords());
                textArea.appendText("\n" + formating.format(Calendar.getInstance().getTime())
                        + "\n" + "Operation delete signs " + " Word count = " + Integer.toString(listWords.size()));
                listWords = sortingText
                        .removeWordsLessThanXValueLetters(listWords, Integer.parseInt(textFieldMinNamberLetters.getText()));
                textArea.appendText("\n" + formating.format(Calendar.getInstance().getTime())
                        + "\n" + "Operation removing words less " + textFieldMinNamberLetters.getText() + " letters "
                        + "Word count = " + Integer.toString(listWords.size()));
                //-------------This text splitting into abbreviations and words ---class WordsAndAbbreviations-------------------
                WordsAndAbbreviations wordsAndAbbreviations;
                wordsAndAbbreviations = sortingText.separatingWordsAndAbbreviations(listWords);
                textArea.appendText("\n" + formating.format(Calendar.getInstance().getTime())
                        + "\n" + "Operation of division into words and abbreviations"
                        + "\n" + "Abbreviations сount = " + Integer.toString(wordsAndAbbreviations.getAbbreviations().size())
                        + "\n" + "Word count  = " + Integer.toString(wordsAndAbbreviations.getWords().size()));
                Map<String, Long> mapCountDuplicateWords = new HashMap<>();
                Map<String, Long> mapCountDuplicateAbbreviations = new HashMap<>();
                mapCountDuplicateWords = sortingText.countDuplicates(wordsAndAbbreviations.getWords());
                mapCountDuplicateAbbreviations = sortingText.countDuplicates(wordsAndAbbreviations.getAbbreviations());
                textArea.appendText("\n" + formating.format(Calendar.getInstance().getTime())
                        + "\n" + "Counting of duplicate words = " + Integer.toString(mapCountDuplicateWords.size())
                        + "\n" + "Counting of duplicate abbreviations = " + Integer.toString(mapCountDuplicateAbbreviations.size()));
                //---------Duplicates are removed whose number is less than the specified value=textFieldMinValue.getText()---------------------
                Map<String, Long> mapDeletElemetByValueLessCountDuplicateWords = new HashMap<>();
                mapDeletElemetByValueLessCountDuplicateWords = sortingText
                        .deletElemetByValueLessForMap(mapCountDuplicateWords, Integer.parseInt(textFieldMinValue.getText()));
                // FIXME Start output to terminal
//                mapDeletElemetByValueLessCountDuplicateWords.entrySet().stream()
//                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                        .forEach(x -> System.out.println(x));               
//                mapCountDuplicateAbbreviations.entrySet().stream()
//                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                        .forEach(System.out::println);
                // TODO End output to terminal
                //------------------Duplicates are sorted in descending order----------------------
                mapSortDuplicateWords = new HashMap<>();
                mapSortDuplicateAbbreviations = new HashMap<>();
                mapSortDuplicateAbbreviations = sortingText.mapSortReversedByValue(mapCountDuplicateAbbreviations);
                mapSortDuplicateWords = sortingText.mapSortReversedByValue(mapDeletElemetByValueLessCountDuplicateWords);
                textArea.appendText("\n" + formating.format(Calendar.getInstance().getTime())
                        + "\n" + "Counting of duplicate words = " + Integer.toString(mapSortDuplicateWords.size())
                        + "\n" + "The count of which is more " + textFieldMinValue.getText());
            } else {
                textArea.clear();
                textArea.appendText("\n" + "---------------OPEN FILE!!!--------------");
            }
        } catch (NullPointerException e) {
            textArea.clear();
            textArea.appendText("\n" + "+--------------OPEN FILE!!!-------------+");
        }

    }

    @FXML
    private void buttonClearTextArea() throws IOException {
        textArea.clear();
    }
}
