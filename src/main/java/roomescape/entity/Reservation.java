package roomescape.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(Long id, String name, LocalDate date, LocalTime time) {

    public static Reservation withId(Reservation reservation, long id) {
        return new Reservation(id, reservation.name(), reservation.date(),
            reservation.time());
    }
}
