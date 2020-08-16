package okon.ECIP;

public class Queue {
    private final String name;
    private final String type;
    private final String size;

    public Queue(String name, String type, String size) {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }
}
