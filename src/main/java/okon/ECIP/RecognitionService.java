package okon.ECIP;

import okon.ECIP.exception.ConnectionException;
import okon.ECIP.exception.LoggingException;
import okon.ECIP.exception.RedirectionLimitException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

import static okon.ECIP.ECIPApp.authorization;

public class RecognitionService {
    private HttpGateway server;
    private ContentAnalyzer analyzer;

    public RecognitionService(HttpGateway server, ContentAnalyzer analyzer) {
        this.server = server;
        this.analyzer = analyzer;
    }

    public List<Report> recognizeQueueSizes() throws ConnectionException, LoggingException, RedirectionLimitException {
        HttpURLConnection connection = null;
        connection = goWebsite();
        return analyzer.reportQueueSizes(getWebsiteContent(connection));
    }

    private HttpURLConnection goWebsite() throws ConnectionException, LoggingException, RedirectionLimitException {
        int redirections = 0;
        HttpURLConnection connection = server.doRequest(authorization.getProperty("website"), RequestMethod.POST,
                authorization.getProperty("website"), new HashMap<String, String>()
                {{ put("email", authorization.getProperty("email"));
                    put("password", authorization.getProperty("password"));
                }});
        try {
            server.checkLoggingCorrectness();
            while (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                if (!isRedirectionLimitExceeded(redirections)) {
                    connection = doRequest(connection);
                    redirections++;
                } else {
                    throw new RedirectionLimitException("Redirection limit is exceeded.");
                }
            }
        } catch (LoggingException e) {
            throw new LoggingException(e);
        } catch (IOException e) {
            throw new ConnectionException(e);
        } catch (RedirectionLimitException e) {
            throw new RedirectionLimitException(e);
        }
        return connection;
    }

    private boolean isRedirectionLimitExceeded(int redirections) {
        if (redirections < 21) return false;
        return true;
    }

    private String getWebsiteContent(HttpURLConnection connection) throws ConnectionException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            throw new ConnectionException(e);
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
