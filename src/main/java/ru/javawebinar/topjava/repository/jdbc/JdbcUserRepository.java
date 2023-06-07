package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.javawebinar.topjava.util.ValidationUtil.validate;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        validate(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            addRoles(user);
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password, 
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0) {
            return null;
        } else {
            deleteRoles(user);
            addRoles(user);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Collection<Role>> roles = new HashMap<>();
        jdbcTemplate.query("SELECT r.user_id as user_id, r.role as role FROM user_role r",
                new ResultSetExtractor<Map<Integer, Collection<Role>>>() {
                    @Override
                    public Map<Integer, Collection<Role>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            roles.computeIfAbsent(
                                    Integer.parseInt(rs.getString("user_id")),
                                    r -> EnumSet.noneOf(Role.class)
                            ).add(Role.valueOf(rs.getString("role")));
                        }
                        return roles;
                    }
                }
        );
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(roles.get(u.getId())));
        return users;
    }

    private void addRoles(User u) {
        if (u != null) {
            Set<Role> roles = u.getRoles();
            if (!CollectionUtils.isEmpty(roles)) {
                jdbcTemplate.batchUpdate("INSERT INTO user_role  (user_id, role) VALUES(?, ?)", roles, roles.size(),
                        (ps, argument) -> {
                            ps.setInt(1, u.id());
                            ps.setString(2, argument.name());
                        }
                );
            }
        }
    }

    public void deleteRoles(User u) {
        if (u != null) {
            jdbcTemplate.update("DELETE FROM user_role WHERE user_id=?", u.id());
        }
    }

    public User setRoles(User u) {
        if (u != null) {
            Collection<Role> roles = jdbcTemplate.queryForList(
                    "SELECT role FROM user_role WHERE user_id=?",
                    Role.class,
                    u.id());
            u.setRoles(roles);
        }
        return u;
    }
}