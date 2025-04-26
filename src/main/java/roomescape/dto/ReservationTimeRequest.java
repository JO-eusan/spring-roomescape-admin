package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record ReservationTimeRequest(
    @JsonFormat(pattern = "HH:mm") LocalTime startAt) {

    public static ReservationTime toEntity(ReservationTimeRequest request) {
        return new ReservationTime(null, request.startAt());
    }
}
