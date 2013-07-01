/**
 * Connection
 * <p>
 * This structure describes a single connection to another airport
 * 
 * @author Marius Spix
 */

package de.bwv_aachen.dijkstra.model;

import org.joda.time.Duration;

public class Connection {
    private Duration duration;

    public Connection(Duration duration) {
        setDuration(duration);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
