package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(ReservationDao.class)
public class ReservationDaoTest {

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
        private ReservationDao reservationDao;

        @BeforeEach
        void createTestData() {
            jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "사나", "2025-04-22", "15:40");
            jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "앤지", "2025-04-21", "12:00");
        }

        @AfterEach
        void deleteTestData() {
            jdbcTemplate.update("DELETE FROM reservation");
        }

        @Test
        @DisplayName("전체 예약 기록을 조회할 수 있다.")
        void findAllReservation() {
            List<Reservation> reservations = reservationDao.findAllReservations();

            assertThat(reservations).hasSize(2);
        }
    }
}
