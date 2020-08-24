package okon.ECIP;

public class ReportFormatter {
    private final String format = "%-21s%-16s%-16s%-16s";

    public String format(String[] input) { return String.format(format, input); }
}