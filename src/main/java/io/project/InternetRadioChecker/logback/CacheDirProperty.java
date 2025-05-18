package io.project.InternetRadioChecker.logback;

import ch.qos.logback.core.PropertyDefinerBase;

public class CacheDirProperty extends PropertyDefinerBase {

    //public static final String CACHE_DIR = (OSUtils.IS_OS_WINDOWS) ? "cache" : "/var/log/KabachokTgBotCache";
    public static final String CACHE_DIR = "cache";

    @Override
    public String getPropertyValue() {
        return CACHE_DIR;
    }
}
