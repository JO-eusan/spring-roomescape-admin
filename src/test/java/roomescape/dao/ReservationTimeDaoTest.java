package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
@Import(ReservationTimeDao.class)
public class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Test
    @DisplayName("전체 시간을 조회할 수 있다.")
    void findAllReservationTime() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:40");
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "12:00");

        List<ReservationTime> times = reservationTimeDao.findAllTimes();

        assertThat(times).hasSize(2);
    }

    @Test
    @DisplayName("시간을 추가할 수 있다.")
    void addReservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(12, 0));
        ReservationTime newReservationTime = reservationTimeDao.addTime(reservationTime);

        assertThat(newReservationTime).isNotNull();
    }

    @Test
    @DisplayName("ID로 시간을 삭제할 수 있다.")
    void removeReservation() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(12, 0));
        ReservationTime newReservationTime = reservationTimeDao.addTime(reservationTime);

        reservationTimeDao.removeTimeById(newReservationTime.id());

        assertThat(reservationTimeDao.findAllTimes()).isEmpty();
    }
}
