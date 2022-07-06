package org.assimbly.util.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateFormats {
    public static final DateFormat ISO8601;
    public static final DateFormat ISO8601_MILLIS;

    static {
        ISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        ISO8601.setTimeZone(TimeZone.getTimeZone("UTC"));

        ISO8601_MILLIS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        ISO8601_MILLIS.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
}
