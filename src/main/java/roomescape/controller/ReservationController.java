package roomescape.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;

    private ReservationController(ReservationDao reservationDao,
        ReservationTimeDao reservationTimeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
    }

    @PostMapping
    public ReservationResponse createReservation(@RequestBody ReservationRequest request) {
        ReservationTime time = reservationTimeDao.findTimeById(request.timeId());
        Reservation createReservation = reservationDao.addReservation(
            ReservationRequest.toEntity(request, time));
        return ReservationResponse.from(createReservation);
    }

    @GetMapping
    public List<ReservationResponse> readReservations() {
        return reservationDao.findAllReservations().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationDao.removeReservationById(id);
    }
}
