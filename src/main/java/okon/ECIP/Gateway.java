package okon.ECIP;

import okon.ECIP.exception.AppException;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

public class Gateway {
    public int doRequest(String url, RequestMethod method) {
        try {
            URL objective = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) objective.openConnection();
            connection.setRequestMethod(method.name());
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            connection.setRequestProperty("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Host", objective.getHost());
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/84.0.4147.105 Safari/537.36");
            return connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AppException(e);
        }
    }
}
