package dei.unipd.it.resources;

import java.time.LocalDateTime;

public class Shift {

    private final long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Shift(long id, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
