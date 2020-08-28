package okon.ECIP;

import okon.ECIP.exception.ConnectionException;
import okon.ECIP.exception.LoggingException;
import okon.ECIP.params.AuthorizationParamsReader;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class ECIPApp {
    static final Properties authorization;

    static {
        authorization = AuthorizationParamsReader.readProperties(new File("./params/authorization.properties"));
    }

    public static void main(String args[]) {
        try {
            List<Report> reports = new ReportManager().getReports();
            new ReportPrinter().print(reports);
        } catch (ConnectionException | LoggingException e) {
            new ReportPrinter().printExceptionCommunication();
        }
    }

    static String getJarFileName() {
        String path = ECIPApp.class.getResource(ECIPApp.class.getSimpleName() + ".class").getFile();
        path = path.substring(0, path.lastIndexOf('!'));
        path = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf('.'));
        return path;
    }
}