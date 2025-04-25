package roomescape.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record Reservation(
    Long id,
    String name,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @JsonFormat(pattern = "HH:mm") LocalTime time
) {

    public static Reservation withId(Long id, Reservation reservation) {
        return new Reservation(id, reservation.name(), reservation.date(),
            reservation.time());
    }
}
