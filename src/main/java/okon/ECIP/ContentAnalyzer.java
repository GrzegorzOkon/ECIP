package okon.ECIP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentAnalyzer {
    public List<Report> reportQueueSizes(String content) {
        List<Report> result = new ArrayList<>();
        result.add(new Report(matchQueue(content)));
        return result;
    }

    private List<Queue> matchQueue(String content) {
        List<Queue> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("<span style=\"word-wrap:break-word;\">(.*)\\.RSND.*</span>" +
                "\\s+<span class=\"label label-info\">(.*)</span>" +
                "\\s+<span class=\"\\s*label label-success pull-right\">(.*)</span>");
        Matcher matcher = pattern.matcher(content);
        for (int i = 1; i < 4; i++) {
            matcher.find();
            String name = matcher.group(1);
            String type = matcher.group(2);
            String size = matcher.group(3);
            result.add(new Queue(name, type, size));
        }
        return result;
    }
}