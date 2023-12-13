package com.esay.utility;

import java.io.File;  // Import the File class
import java.io.FileInputStream;
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class EsayFile implements Serializable {

    private ArrayList<Integer> data;

    public EsayFile () {
        data = new ArrayList<Integer>();
    }

    public void readFile(String path){
        File file = new File(path);

        try (FileInputStream fis = new FileInputStream(file)) {
            int content;
            while ((content = fis.read()) != -1) {
                data.add(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String path){
        File file = new File(path);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            for(Integer i : data){
                fos.write((byte)i.intValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
