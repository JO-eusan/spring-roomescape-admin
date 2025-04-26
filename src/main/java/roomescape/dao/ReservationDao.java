package roomescape.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Reservation> findAllReservations() {
        String sql = """
            SELECT
                r.id as reservation_id,
                r.name,
                r.date,
                t.id as time_id,
                t.start_at as time_value
            FROM reservation as r
            INNER JOIN reservation_time as t
            ON r.time_id = t.id
            """;

        return jdbcTemplate.query(sql, createReservationMapper());
    }

    public Reservation addReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"id"});
            stmt.setString(1, reservation.getName());
            stmt.setDate(2, Date.valueOf(reservation.getDate()));
            stmt.setLong(3, reservation.getTime().getId());
            return stmt;
        }, keyHolder);

        return new Reservation(keyHolder.getKey().longValue(), reservation.getName(),
            reservation.getDate(), reservation.getTime());
    }

    public void removeReservationById(Long id) {
        String sql = "DELETE FROM reservation WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Reservation> createReservationMapper() {
        return (rs, rowNum) -> new Reservation(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDate("date").toLocalDate(),
            new ReservationTime(
                rs.getLong("time_id"),
                rs.getTime("time_value").toLocalTime())
        );
    }
}
