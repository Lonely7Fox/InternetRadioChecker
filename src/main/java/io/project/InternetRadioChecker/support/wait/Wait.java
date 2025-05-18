package io.project.InternetRadioChecker.support.wait;

import io.project.InternetRadioChecker.exceptions.TimeoutException;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class Wait<T> {
    private final Duration DEFAULT_INTERVAL = Duration.ofMillis(500);
    private final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(5);
    private final Sleeper sleeper;
    private final Clock clock;
    private final T input;
    private Duration timeout;
    private Duration interval;
    private final List<Class<? extends Throwable>> ignoredExceptions;

    public Wait() {
        this(null);
    }

    public Wait(T input) {
        this.sleeper = Sleeper.SYSTEM_SLEEPER;
        this.clock = Clock.systemDefaultZone();
        this.interval = DEFAULT_INTERVAL;
        this.timeout = DEFAULT_TIMEOUT;
        this.input = input;
        this.ignoredExceptions = new ArrayList<>();
    }

    public Wait<T> withTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    public Wait<T> pollingEvery(Duration interval) {
        this.interval = interval;
        return this;
    }

    public Wait<T> ignoring(Class<? extends Throwable> exceptionType) {
        return this.ignoreAll(List.of(exceptionType));
    }

    public <K extends Throwable> Wait<T> ignoreAll(Collection<Class<? extends K>> types) {
        this.ignoredExceptions.addAll(types);
        return this;
    }

    /**
     * Check function until timeout.
     * Default timeout 5 sec, polling every 500 millis.
     * @throws TimeoutException
     */
    public <V> V until(Function<? super T, V> isTrue) {
        Instant end = this.clock.instant().plus(this.timeout);
        while(true) {
            Throwable lastException;
            try {
                V value = isTrue.apply(this.input);
                if (value != null && (Boolean.class != value.getClass() || Boolean.TRUE.equals(value))) {
                    return value;
                }
                lastException = null;
            } catch (Throwable e) {
                Throwable err = e;
                lastException = this.propagateIfNotIgnored(err);
            }

            if (end.isBefore(this.clock.instant())) {
                String timeoutMessage = String.format("Expected condition failed: %s (tried for %d second(s) with %d milliseconds interval)", "waiting for " + String.valueOf(isTrue), this.timeout.getSeconds(), this.interval.toMillis());
                throw new TimeoutException(timeoutMessage, lastException);
            }

            try {
                this.sleeper.sleep(this.interval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }

    private Throwable propagateIfNotIgnored(Throwable e) {
        Iterator var2 = this.ignoredExceptions.iterator();
        Class ignoredException;
        do {
            if (!var2.hasNext()) {
                if (e instanceof Error) {
                    throw (Error)e;
                }
                if (e instanceof RuntimeException) {
                    throw (RuntimeException)e;
                }
                throw new RuntimeException(e);
            }
            ignoredException = (Class)var2.next();
        } while(!ignoredException.isInstance(e));
        return e;
    }
}
