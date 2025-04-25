package roomescape.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationTimeDao;
import roomescape.entity.ReservationTime;

@RestController
@RequestMapping("/times")
public class TimeController {

    private final ReservationTimeDao reservationTimeDao;

    private TimeController(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ReservationTime createReservationTime(@RequestBody ReservationTime reservationTime) {
        return reservationTimeDao.addTime(reservationTime);
    }
}
