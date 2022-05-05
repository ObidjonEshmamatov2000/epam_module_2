package com.epam.esm.dao.giftCertificate;

import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.giftCertificate.GiftCertificateMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int create(GiftCertificate gc) {
        return jdbcTemplate.update("insert into gift_certificate(id, name, description, price, duration, " +
                "created_date, last_updated_date) values(?, ?, ?, ?, ?, ?, ?)",
                gc.getId(),
                gc.getName(),
                gc.getDescription(),
                gc.getPrice(),
                gc.getDuration(),
                gc.getCreateDate(),
                gc.getLastUpdateDate());
    }

    @Override
    public GiftCertificate get(UUID id) {
        return jdbcTemplate.queryForObject("select * from gift_certificate where id = ?", new GiftCertificateMapper(), id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        return jdbcTemplate.query("select * from gift_certificate", new GiftCertificateMapper());
    }

    @Override
    public int delete(UUID id) {
        return jdbcTemplate.update("delete from gift_certificate where id = ?", id);
    }

    @Override
    public int update(GiftCertificate gc) {
        return jdbcTemplate.update("update gift_certificate" +
                "set name = ?," +
                "description = ?," +
                "price = ?," +
                "duration = ?," +
                "last_update_date = ?" +
                "where id = ?;",
                gc.getName(),
                gc.getDescription(),
                gc.getPrice(),
                gc.getDuration(),
                gc.getLastUpdateDate());
    }

    @Override
    public List<GiftCertificate> getByTagName(String tagName) {
        return null;
    }
}
