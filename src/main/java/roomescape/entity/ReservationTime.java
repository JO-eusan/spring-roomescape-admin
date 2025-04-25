package roomescape.entity;

import java.time.LocalTime;

public record ReservationTime(
    Long id,
    LocalTime startAt
) {

    public static ReservationTime withId(Long id, ReservationTime reservationTime) {
        return new ReservationTime(id, reservationTime.startAt());
    }
}
