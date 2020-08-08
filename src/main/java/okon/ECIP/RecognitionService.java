package okon.ECIP;

import java.util.HashMap;

import static okon.ECIP.ECIPApp.authorization;

public class RecognitionService {
    private Gateway server;

    public RecognitionService(Gateway server) {
        this.server = server;
    }

    public void recognizeQueueSizes() {
        System.out.println("Response code: " + server.doRequest(authorization.getProperty("website"), RequestMethod.GET));
        System.out.println("Cookie: " + server.doRequest(authorization.getProperty("website"), RequestMethod.POST,
                authorization.getProperty("website"), new HashMap<String, String>()
                {{ put("email", authorization.getProperty("email"));
                   put("password", authorization.getProperty("password"));
                }}));
    }
}
