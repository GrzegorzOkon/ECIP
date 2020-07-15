package okon.ECIP;

import okon.ECIP.params.AuthorizationParamsReader;

import java.io.File;
import java.util.Properties;

public class ECIPApp {
    static final Properties authorization;

    static {
        authorization = AuthorizationParamsReader.readProperties(new File("./params/authorization.properties"));
    }

    public static void main (String args[]) {

    }
}
