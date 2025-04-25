package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

@JdbcTest
@Import(ReservationDao.class)
public class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;

    @Test
    @DisplayName("DataSource 접근 테스트")
    void connectJdbc() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null)
                .next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("전체 예약 기록을 조회할 수 있다.")
    void findAllReservation() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
            "사나", "2025-04-22", "15:40");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
            "앤지", "2025-04-21", "12:00");

        List<Reservation> reservations = reservationDao.findAllReservations();

        assertThat(reservations).hasSize(2);
    }

    @Test
    @DisplayName("예약을 추가할 수 있다.")
    void addReservation() {
        Reservation reservation = new Reservation(null, "사나", LocalDate.of(2025, 4, 22),
            LocalTime.of(12, 0));
        Reservation newReservation = reservationDao.addReservation(reservation);

        assertAll(() -> {
            assertThat(newReservation).isNotNull();
            assertThat(reservationDao.findAllReservations()).hasSize(1);
        });
    }

    @Test
    @DisplayName("ID로 예약을 삭제할 수 있다.")
    void removeReservation() {
        Reservation reservation = new Reservation(null, "사나", LocalDate.of(2025, 4, 22),
            LocalTime.of(12, 0));
        Reservation newReservation = reservationDao.addReservation(reservation);

        reservationDao.removeReservationById(newReservation.id());

        assertThat(reservationDao.findAllReservations()).isEmpty();
    }
}
