package io.project.InternetRadioChecker.support.wait;

import java.time.Duration;

public interface Sleeper {
    Sleeper SYSTEM_SLEEPER = (duration) -> {
        Thread.sleep(duration.toMillis());
    };

    void sleep(Duration duration) throws InterruptedException;
}
