package okon.ECIP;

import okon.ECIP.exception.AppException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

import static okon.ECIP.ECIPApp.authorization;

public class RecognitionService {
    private Gateway server;

    public RecognitionService(Gateway server) {
        this.server = server;
    }

    public void recognizeQueueSizes() {
        HttpURLConnection connection = null;
        connection = goWebsite();
        System.out.println(getWebsiteContent(connection));
    }

    private HttpURLConnection goWebsite() {
        HttpURLConnection connection = server.doRequest(authorization.getProperty("website"), RequestMethod.POST,
                authorization.getProperty("website"), new HashMap<String, String>()
                {{ put("email", authorization.getProperty("email"));
                    put("password", authorization.getProperty("password"));
                }});
        try {
            while (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                connection = doRequest(connection);
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
        return connection;
    }

    private String getWebsiteContent(HttpURLConnection connection) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    private HttpURLConnection doRequest(HttpURLConnection connection) throws IOException {
        HttpURLConnection result = null;
        if (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP
                || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM
                || connection.getResponseCode() == HttpURLConnection.HTTP_SEE_OTHER) {
            result = server.doRequest(connection.getHeaderField("Location"), RequestMethod.GET);
        }
        return result;
    }
}
