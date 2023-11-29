package com.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class AppInitiator extends Application {
    static String FXML_PATH = "src\\main\\resources\\com\\calculator\\Calculator.fxml";

    @Override
    public void start(Stage stage) throws IOException {
//        getSizeParameters();
        FXMLLoader fxmlLoader = new FXMLLoader(AppInitiator.class.getResource("Calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 264, 360);
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

//    private void getSizeParameters() {
//        FileInputStream fis;
//        try {
//            fis = new FileInputStream(new File(FXML_PATH));
//        } catch (IOException e) {
//            System.out.println("File not found in directory " + new File(FXML_PATH).getAbsolutePath());
//            return;
//        }
//
//        Scanner scanner = new Scanner(fis);
//        while (scanner.hasNext()) {
//            String tmp = scanner.nextLine();
//            if (tmp.contains("prefHeight=\"")) {
//                int indexH = getIndex(tmp, "prefHeight=\"");    //12
//                int indexW = getIndex(tmp, "prefWidth=\"");     //11
//                System.out.println(indexH + " " + indexW);
//                break;
//            }
//        }
//    }

//    int getIndex(String str, String substring)
//    {
//        return Arrays.asList(str.split("\\s+")).indexOf(substring)+1;
//    }
}