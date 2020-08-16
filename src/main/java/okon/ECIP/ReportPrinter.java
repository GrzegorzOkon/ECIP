package okon.ECIP;

import java.util.List;

public class ReportPrinter {
    public void print(List<Report> reports) {
        printToConsole(reports);
        printToFile();
    }

    private void printToConsole(List<Report> reports) {
        ReportFormatter formatter = new ReportFormatter();
        System.out.println(formatter.format(new String[]{"Queue", "In", "Out", "Error"}));
        System.out.println(formatter.format(new String[]{"-----", "--", "---", "-----"}));
        for (Report report : reports) {
            String[] input = new String[4];
            for (int i = 0; i < report.getQueues().size(); i++) {
                if (i == 0) input[i] = report.getQueues().get(i).getName();
                input[i + 1] = report.getQueues().get(i).getSize();
            }
            System.out.println(formatter.format(input));
        }
    }

    private void printToFile() {

    }
}
