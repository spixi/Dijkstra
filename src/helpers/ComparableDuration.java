package helpers;

import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.ReadableDuration;
import org.joda.time.ReadableInstant;
import org.joda.time.base.BaseDuration;

/**
 * 
 * @author Marius
 *
 * This class is a decorator for org.joda.time.Duration with the Comparable interface
 */

public class ComparableDuration implements ReadableDuration,
                                           Comparable<ReadableDuration>{
	
	ReadableDuration decorated = null;
	
	public ComparableDuration(long arg0) {
		decorated = new Duration(arg0);
	}
	
	public ComparableDuration(Object arg0) {
		decorated = new Duration(arg0);
	}
	
	public ComparableDuration(long arg0, long arg1) {
		decorated = new Duration(arg0, arg1);
	}
	
	public ComparableDuration(ReadableInstant arg0, ReadableInstant arg1) {
		decorated = new Duration(arg0, arg1);
	}
	
	public ComparableDuration(BaseDuration decorated) {
		this.decorated = decorated;
	}

	@Override
	public int compareTo(ReadableDuration o) {
		if (decorated.isLongerThan(o)) {
			return 1;
		}
		if (decorated.isShorterThan(o)) {
			return -1;
		}
		else {
			return 0;
		}
	}

	@Override
	public long getMillis() {
        return decorated.getMillis();
	}

	@Override
	public boolean isEqual(ReadableDuration arg0) {
		return decorated.isEqual(arg0);
	}

	@Override
	public boolean isLongerThan(ReadableDuration arg0) {
		return decorated.isLongerThan(arg0);
	}

	@Override
	public boolean isShorterThan(ReadableDuration arg0) {
		return decorated.isEqual(arg0);
	}

	@Override
	public Duration toDuration() {
		return decorated.toDuration();
	}

	@Override
	public Period toPeriod() {
		return decorated.toPeriod();
	}
	

}
