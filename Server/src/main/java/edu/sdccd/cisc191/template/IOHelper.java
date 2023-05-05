package edu.sdccd.cisc191.template;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class IOHelper {

    public static LinkedList<String> readItems(String fileName) throws IOException {
        File input = new File(fileName);
        Scanner in = new Scanner(input);
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String file;
        int lines = 0;

        while (br.readLine() != null) {

            lines++;
        }

        // Create the list
        LinkedList<String> list = new LinkedList<>();

        // Copy the file contents to the Linked List
        for (int i = 0; i < lines; i++) {

            System.out.println(input.length());
            file = in.next();
            list.add(file);
        }

        return list;
    }

    static void writeData(LinkedList<String> calcHistory) {
        String file_name = "output.txt";
        try {

            FileWriter fileStream = new FileWriter(file_name);
            BufferedWriter out = new BufferedWriter(fileStream);

            for (String element : calcHistory) {
                out.write(element + "\n");
            }

            out.close();
            System.out.println("File created successfully.");

        } catch (Exception ignored) {
        }
    }
}
