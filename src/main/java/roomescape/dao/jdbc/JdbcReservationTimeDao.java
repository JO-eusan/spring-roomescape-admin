package roomescape.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ReservationTime> findAllTimes() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, createReservationMapper());
    }

    public ReservationTime findTimeById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, createReservationMapper(), id);
    }

    public ReservationTime addTime(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"id"});
            stmt.setTime(1, Time.valueOf(reservationTime.getStartAt()));
            return stmt;
        }, keyHolder);

        return new ReservationTime(keyHolder.getKey().longValue(), reservationTime.getStartAt());
    }

    public void removeTimeById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ReservationTime> createReservationMapper() {
        return (rs, rowNum) -> new ReservationTime(
            rs.getLong("id"),
            rs.getTime("start_at").toLocalTime());
    }
}
