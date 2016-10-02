package com.example;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException {
        Path currentRelativePath = Paths.get("");
        String workingDirectory = currentRelativePath.toAbsolutePath().toString();
        String fileName = workingDirectory + FileSystems.getDefault().getSeparator() + "payment_dates.csv";

        int numberOfMonths = 12;
        int bonusPayDate = 15;
        boolean useFriday = false;

        try {
            ArrayList salaryPayDates = new PayDates(numberOfMonths).getPaymentDates();
            ArrayList bonusPayDates = new PayDates(numberOfMonths, bonusPayDate, useFriday).getPaymentDates();

            List<String[]> entries = new ArrayList<String[]>();
            entries.add(new String[]{"month", "salary_date", "bonus_dates"});
            for (int i = 0; i < bonusPayDates.size(); i++) {
                LocalDate salary = (LocalDate) salaryPayDates.get(i);
                LocalDate bonus = (LocalDate) bonusPayDates.get(i);
                entries.add(new String[]{salary.getMonth().name(), salary.toString(), bonus.toString()});
            }

            CSVWriter writer = new CSVWriter(new FileWriter(fileName));
            writer.writeAll(entries);
            writer.close();

            System.out.println("[+] Successfully generated csv :" + fileName);
        } catch (Exception ex) {
            System.out.println("[!] Something went wrong unable to generate the csv");
            System.out.println("[!] Exception " + ex.toString());

        }
    }
}
