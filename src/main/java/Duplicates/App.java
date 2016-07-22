package Duplicates;

import com.google.common.io.Files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws IOException {
        String dir, duplicates = "";
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        DateFormat dateFormat = new SimpleDateFormat(" dd - MMMM - yyyy HH:mm");
        Date date = new Date();
        Scanner scanner = new Scanner(System.in);

        //TODO creating text file
        File logFile = new File("duplicateLog.txt");
        if (logFile.exists())
            logFile.createNewFile();

        System.out.println("Enter directory you want to scan or 0 to exit");
        dir = scanner.nextLine();

        //TODO.... validation
        while (!dir.equalsIgnoreCase("0")) {
            while (!new File((dir)).isDirectory()) {
                System.out.println("Invalid directory try again or 0 to exit");
                dir = scanner.nextLine();
                System.out.flush();
                if (dir.equalsIgnoreCase("1")) {
                    System.exit(0);
                }
            }

            final File folder = new File(dir);
            File[] files = folder.listFiles();
            fileWriter = new FileWriter(logFile.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);

            //TODO checking for duplications
            for (int i = 0; i < files.length; i++) {
                for (int j = i + 1; j < files.length; j++) {
                    if (files[i].isFile() && files[j].isFile()) {
                        if (Files.asByteSource(files[i]).contentEquals(Files.asByteSource(files[j]))) {
                            duplicates += files[i].getName().toLowerCase()
                                    + " \tis a duplicate of \t " +
                                    files[j].getName().toUpperCase() + "\n";

                        }
                    }
                }
            }

            //TODO write to th file
            if (!duplicates.equalsIgnoreCase("")) {
                bufferedWriter.write("Path: " +
                        new File(dir).getAbsoluteFile() +
                        "\n\t\t\t" + dateFormat.format(date) +
                        "\n" + duplicates + "\n\n");
                duplicates = "";
            }

            System.out.println("Scanning directory "+dir.toUpperCase()+" is completed");
            System.out.println("Enter directory you want to scan or 0 to exit");
            dir = scanner.nextLine();

            if (dir.equals("0"))
                bufferedWriter.close();
        }
        System.out.println("\n\n\nthanks for using duplicate checker,\nResults are on the duplicateLog.txt File");
    }


}
