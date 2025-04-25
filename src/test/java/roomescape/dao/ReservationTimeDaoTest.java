package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.ReservationTime;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(ReservationTimeDao.class)
public class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Nested
    class ConnectionTest {

        @Test
        @DisplayName("DataSource 접근 테스트")
        void connectJdbc() {
            try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                assertThat(connection).isNotNull();
                assertThat(connection.getCatalog()).isEqualTo("TEST");
                assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null)
                    .next()).isTrue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Nested
    class CRUDTest {

        @Autowired
        private ReservationTimeDao reservationTimeDao;

        @Test
        @DisplayName("전체 시간을 조회할 수 있다.")
        void findAllReservationTime() {
            jdbcTemplate.update("INSERT INTO reservation_time (startAt) VALUES (?)", "15:40");
            jdbcTemplate.update("INSERT INTO reservation_time (startAt) VALUES (?)", "12:00");

            // List<Reservation> reservations = reservationTimeDao.findAllReservations();

            // assertThat(reservations).hasSize(2);
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

            // reservationTimeDao.removeReservationById(newReservationTime.id());

            // assertThat(reservationTimeDao.findAllReservations()).isEmpty();
        }
    }
}
