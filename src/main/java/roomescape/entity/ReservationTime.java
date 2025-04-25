package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record ReservationTime(
    Long id,
    @JsonFormat(pattern = "HH:mm") LocalTime startAt
) {

    public static ReservationTime withId(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.startAt());
    }
}
