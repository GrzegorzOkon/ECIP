package okon.ECIP;

import okon.ECIP.exception.AppException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ReportPrinter {
    public void print(List<Report> reports) {
        printToConsole(reports);
        printToFile(reports);
    }

    private void printToConsole(List<Report> reports) {
        ReportFormatter formatter = new ReportFormatter();
        System.out.println(formatter.format(new String[]{"Kolejka", "In", "Out", "Error"}));
        System.out.println(formatter.format(new String[]{"-------", "--", "---", "-----"}));
        for (Report report : reports) {
            String[] input = new String[4];
            for (int i = 0; i < report.getQueues().size(); i++) {
                if (i == 0) input[i] = report.getQueues().get(i).getName();
                input[i + 1] = report.getQueues().get(i).getSize();
            }
            System.out.println(formatter.format(input));
        }
    }

    private void printToFile(List<Report> reports) {
        try (Writer out = new FileWriter(new java.io.File(ECIPApp.getJarFileName() + ".txt"))) {
            ReportFormatter formatter = new ReportFormatter();
            out.write(formatter.format(new String[]{"Kolejka", "In", "Out", "Error"}));
            out.write(System.getProperty("line.separator"));
            out.write(formatter.format(new String[]{"-------", "--", "---", "-----"}));
            out.write(System.getProperty("line.separator"));
            for (Report report : reports) {
                String[] input = new String[4];
                for (int i = 0; i < report.getQueues().size(); i++) {
                    if (i == 0) input[i] = report.getQueues().get(i).getName();
                    input[i + 1] = report.getQueues().get(i).getSize();
                }
                out.write(formatter.format(input));
                out.write(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
