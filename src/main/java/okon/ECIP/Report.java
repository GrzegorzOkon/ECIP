package okon.ECIP;

import java.util.List;

public class Report {
    private final List<Queue> queues;

    public Report(List<Queue> queues) {
        this.queues = queues;
    }

    public List<Queue> getQueues() {
        return queues;
    }
}
