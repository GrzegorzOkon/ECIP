package okon.ECIP;

import static okon.ECIP.ECIPApp.authorization;

public class RecognitionService {
    private Gateway server;

    public RecognitionService(Gateway server) {
        this.server = server;
    }

    public void recognizeQueueSizes() {
        System.out.println("Response code: " + server.doRequest(authorization.getProperty("website"), RequestMethod.GET));
    }
}
