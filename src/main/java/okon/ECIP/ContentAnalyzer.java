package okon.ECIP;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentAnalyzer {
    public List<Report> reportQueueSizes(String content) {
        return matchQueues(content);
    }

    private List<Report> matchQueues(String content) {
        List<Report> result = new ArrayList<>();
        List<Queue> queues = new ArrayList<>(3);
        Pattern pattern = Pattern.compile("<span style=\"word-wrap:break-word;\">(.*)\\.RSND.*</span>" +
                "\\s+<span class=\"label label-info\">(.*)</span>" +
                "\\s+<span class=\"\\s*label label-success pull-right\">(.*)</span>");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find() == true) {
            String name = matcher.group(1);
            String type = matcher.group(2);
            String size = matcher.group(3);
            queues.add(new Queue(name, type, size));
            if (areRetrievedQueuesToResult(queues)) {
                result.add(new Report(queues));
                queues = new ArrayList<>(3);
            }
        }
        return result;
    }

    private boolean areRetrievedQueuesToResult(List<Queue> queues) {
        if (queues.size() == 3)
            return true;
        return false;
    }
}