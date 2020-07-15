package okon.ECIP.params;

import okon.ECIP.exception.AppException;
import okon.ECIP.security.HexDecryptor;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class AuthorizationParamsReader {
    public static Properties readProperties(File file) {
        Properties result = new Properties();
        try {
            result.load(new FileInputStream(file));
            result.setProperty("password", HexDecryptor.convert(result.getProperty("password")));
        } catch (Exception e) {
            throw new AppException(e);
        }
        return result;
    }
}
