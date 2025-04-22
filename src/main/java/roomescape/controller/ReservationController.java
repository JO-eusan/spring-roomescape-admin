package roomescape.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dao.ReservationDao;
import roomescape.entity.Reservation;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationDao reservationDao;

    public ReservationController(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @GetMapping
    public List<Reservation> readReservations() {
        return reservationDao.findAllReservations();
    }

//    @PostMapping
//    public Reservation createReservation(@RequestBody Reservation reservation) {
//        Reservation newReservation = Reservation.withId(reservation, index.getAndIncrement());
//        reservations.add(newReservation);
//        return newReservation;
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteReservation(@PathVariable Long id) {
//        Reservation reservation = reservations.stream()
//            .filter(r -> Objects.equals(r.id(), id))
//            .findFirst()
//            .orElseThrow(IllegalArgumentException::new);
//        reservations.remove(reservation);
//    }
}
