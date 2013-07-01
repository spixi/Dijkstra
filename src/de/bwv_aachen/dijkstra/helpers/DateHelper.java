/**
 * DateHelper
 * <p>
 * This is a helper class for the conversion of Strings and Durations. It uses
 * the Joda-Time library.
 * 
 * @author Marius Spix
 */

package de.bwv_aachen.dijkstra.helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public final class DateHelper {
    public static final DateHelper INSTANCE = new DateHelper();
    private PeriodFormatterBuilder builder;
    private PeriodFormatter        formatter;

    /**
     * Constructor of DateHelper
     * <p>
     * Prepares the formatter of the Date Helper class.
     * <p>
     * Singleton, please use the static attribute DateHelper.INSTANCE
     */
    private DateHelper() {
        builder = new PeriodFormatterBuilder();
        builder.printZeroAlways().appendHours().appendSeparator(":")
                .minimumPrintedDigits(2).appendMinutes();

        formatter = builder.toFormatter();
    }

    /**
     * durationToString
     * <p>
     * Converts a Duration object to a String object.
     * 
     * @param p
     *            : the Duration instance
     * @return the String instance
     */
    public String durationToString(Duration p) {
        return formatter.print(p.toPeriod());
    }

    /**
     * stringToDuration
     * <p>
     * Converts a String object to a Duration object.
     * 
     * @param s
     *            : the String instance
     * @return the Duration instance
     * @throws IllegalArgumentException
     */
    public Duration stringToDuration(String s) throws IllegalArgumentException {
        Period p = Period.parse(s, formatter);

        if (p.toStandardSeconds().isGreaterThan(Seconds.ZERO)) {
            return p.toStandardDuration();
        }
        else {
            return p.negated().toStandardDuration();
        }

    }

}
