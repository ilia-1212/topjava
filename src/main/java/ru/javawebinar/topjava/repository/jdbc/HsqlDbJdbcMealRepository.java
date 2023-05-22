package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.Profiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Profile(Profiles.HSQL_DB)
public class HsqlDbJdbcMealRepository extends AbstractJdbcMealRepository {

    public HsqlDbJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    protected Timestamp convertDate(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }
}
