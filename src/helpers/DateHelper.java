package helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public final class DateHelper {
	public static final DateHelper INSTANCE = new DateHelper();
	private PeriodFormatterBuilder builder;
	private PeriodFormatter formatter;
	
	private DateHelper() {
	    builder = new PeriodFormatterBuilder();
	    builder.printZeroAlways()
	           .appendHours()
	           .appendSeparator(":")
	           .appendMinutes();
	    
	    formatter = builder.toFormatter();
	}
    
    public String durationToString(Duration p) {
    	return formatter.print(p.toPeriod());
    }
    
    public Duration stringToDuration(String s) throws IllegalArgumentException {
    	Period p = Period.parse(s,formatter);
    	
    	if (p.toStandardSeconds().isGreaterThan(Seconds.ZERO)) {
        	return p.toStandardDuration();
    	}
    	else {
    		return p.negated().toStandardDuration();
    	}
    	
    }

}
