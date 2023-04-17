package edu.sdccd.cisc191.template;

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
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
        LinkedList<String> list = new LinkedList<String>();
        ListIterator<String> iter = list.listIterator();

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

            FileWriter fstream = new FileWriter(file_name);
            BufferedWriter out = new BufferedWriter(fstream);

            ListIterator itr = calcHistory.listIterator();
            while (itr.hasNext()) {
                String element = (String) itr.next();
                out.write(element + "\n");
            }

            out.close();
            System.out.println("File created successfully.");

        } catch (Exception e) {
        }
    }
}
