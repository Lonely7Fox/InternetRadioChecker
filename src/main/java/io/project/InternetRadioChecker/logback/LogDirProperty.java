package io.project.InternetRadioChecker.logback;

import ch.qos.logback.core.PropertyDefinerBase;

public class LogDirProperty extends PropertyDefinerBase {

    //public static final String LOG_DIR = (OSUtils.IS_OS_WINDOWS) ? "logs" : "/var/log/InternetRadioChecker";
    public static final String LOG_DIR = "logs";

    @Override
    public String getPropertyValue() {
        return LOG_DIR;
    }
}
