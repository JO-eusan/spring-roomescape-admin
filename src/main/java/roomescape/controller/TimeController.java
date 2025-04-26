package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    private TimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ReservationTimeResponse createReservationTime(
        @RequestBody ReservationTimeRequest request) {
        ReservationTime createdTime = reservationTimeDao.addTime(
            ReservationTimeRequest.toEntity(request));
        return ReservationTimeResponse.from(createdTime);
    }

    @GetMapping
    public List<ReservationTimeResponse> readReservationTimes() {
        return reservationTimeDao.findAllTime().stream()
            .map(ReservationTimeResponse::from)
            .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteReservationTime(@PathVariable Long id) {
        reservationTimeDao.removeTimeById(id);
    }
}
