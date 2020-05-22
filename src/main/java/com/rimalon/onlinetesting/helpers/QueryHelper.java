package com.rimalon.onlinetesting.helpers;

import com.rimalon.onlinetesting.datamodel.entities.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class QueryHelper {
    private JdbcTemplate jtm;

    @Value("${helpers.query.schema.name}")
    public String schemaName;

    @Autowired
    public QueryHelper(JdbcTemplate jtm) {
        this.jtm = jtm;
    }


    public <T extends BaseEntity> T getById(Class<T> clazz, Integer id) {
        try {
            String sql = String.format("SELECT * FROM %s WHERE id = ?", getFullTableName(clazz));
            return jtm.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception e) {
            log.warn("{} is not found id={}", clazz.getSimpleName(), id);
            return null;
        }
    }

    public <T extends BaseEntity> List<T> getListObjectsByWhereClause(Class<T> clazz, String whereClause, Object[] args) {
        try {
            String sql = String.format("SELECT %s.* FROM %s WHERE %s", clazz.getSimpleName(), getFullTableName(clazz), whereClause);
            return jtm.query(sql, args, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception e) {
            return null;
        }
    }

    public <T extends BaseEntity, TT extends BaseEntity> List<T> getListObjectsByJoinClause(Class<T> clazz, Class<TT> joinClass, String joinClause) {
        try {
            String sql = String.format("SELECT %s.* FROM %s INNER JOIN %s ON %s", clazz.getSimpleName(), getFullTableName(clazz), getFullTableName(joinClass), joinClause);
            return jtm.query(sql, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception e) {
            return null;
        }
    }

    public <T extends BaseEntity> T getObjectByWhereClause(Class<T> clazz, String whereClause, Object[] args) {
        if (whereClause.chars().filter(ch -> ch == '?').count() != args.length) {
            log.error("Incorrect parameters amount expected={} but actual={}", whereClause.chars().filter(ch -> ch == '?').count(), args.length);
            return null;
        }
        try {
            String sql = String.format("SELECT * FROM %s WHERE %s", getFullTableName(clazz), whereClause);
            return jtm.queryForObject(sql, args, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception e) {
            return null;
        }
    }

    public <T extends BaseEntity> T getObjectWithMaxId(Class<T> clazz) {
        try {
            String sql = String.format("SELECT * FROM %s ORDER BY id DESC LIMIT 1", getFullTableName(clazz));
            return jtm.queryForObject(sql, new BeanPropertyRowMapper<>(clazz));
        } catch (Exception e) {
            return null;
        }
    }

    public <T extends BaseEntity> boolean save(Class<T> clazz, String fields, Object[] args) {
        String values = String.join(",", Collections.nCopies(args.length, "?"));
        String sql = String.format("INSERT INTO %s %s VALUES (%s)", getFullTableName(clazz), fields, values);
        try {
            jtm.update(sql, args);
        } catch (Exception e) {
            log.error("Failed to insert into table {} for fields={} and args={}", clazz.getSimpleName(), fields, Arrays.toString(args));
            return false;
        }
        return true;
    }


    private <T extends BaseEntity> String getFullTableName(Class<T> clazz) {
        return schemaName + "." + clazz.getSimpleName();
    }


}
