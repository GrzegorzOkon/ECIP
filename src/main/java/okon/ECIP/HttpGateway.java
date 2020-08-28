package okon.ECIP;

import okon.ECIP.exception.ConnectionException;
import okon.ECIP.exception.LoggingException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpGateway {
    private final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private final String ACCEPT_ENCODING = "gzip, deflate, br";
    private final String ACCEPT_LANGUAGE = "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7";
    private final String CONNECTION = "keep-alive";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/84.0.4147.105 Safari/537.36";
    private final List<String> COOKIES = new ArrayList<>();

    public HttpURLConnection doRequest(String url, RequestMethod method) throws ConnectionException {
        try {
            URL objective = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) objective.openConnection();
            connection.setRequestMethod(method.name());
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept", ACCEPT);
            connection.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
            connection.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
            connection.setRequestProperty("Connection", CONNECTION);
            connection.setRequestProperty("Host", objective.getHost());
            connection.setRequestProperty("User-Agent", USER_AGENT);
            addCookies(connection);
            return connection;
        } catch (MalformedURLException e) {
            throw new ConnectionException(e);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    public HttpURLConnection doRequest(String url, RequestMethod method, String referer, Map<String, String> parameters) throws ConnectionException {
        try {
            URL objective = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) objective.openConnection();
            byte[] contentData = getContentString(parameters).getBytes(StandardCharsets.UTF_8);
            connection.setRequestMethod(method.name());
            connection.setUseCaches(false);
            connection.setRequestProperty("Accept", ACCEPT);
            connection.setRequestProperty("Accept-Encoding", ACCEPT_ENCODING);
            connection.setRequestProperty("Accept-Language", ACCEPT_LANGUAGE);
            connection.setRequestProperty("Connection", CONNECTION);
            connection.setRequestProperty("Content-Length", Integer.toString(contentData.length));
            connection.setRequestProperty("Content-Type", CONTENT_TYPE);
            connection.setRequestProperty("Host", objective.getHost());
            connection.setRequestProperty("Referer", referer);
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(contentData);
            }
            getCookies(connection);
            return connection;
        } catch (MalformedURLException e) {
            throw new ConnectionException(e);
        } catch (IOException e) {
            throw new ConnectionException(e);
        }
    }

    public void checkLoggingCorrectness() throws LoggingException {
        if (areCookiesPresent()) {
            if (!COOKIES.toString().contains("JSESSIONID")) {
                throw new LoggingException("Niepoprawny email lub hasło.");
            }
        } else {
            throw new LoggingException("Niepoprawny email lub hasło.");
        }
    }

    private String getContentString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    private void getCookies(HttpURLConnection connection) {
        if (isSetCookiePresent(connection)) {
           String[] cookies = connection.getHeaderField("Set-Cookie").split(";");
           for (String cookie : cookies) {
               COOKIES.add(cookie);
           }
        }
    }

    private boolean isSetCookiePresent(HttpURLConnection connection) {
        if (connection.getHeaderField("Set-Cookie") != null)
            return true;
        return false;
    }

    private void addCookies(HttpURLConnection connection) {
        if (areCookiesPresent()) {
            for (String cookie : COOKIES) {
                connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
    }

    private boolean areCookiesPresent() {
        if (COOKIES.size() > 0)
            return true;
        return false;
    }
}
