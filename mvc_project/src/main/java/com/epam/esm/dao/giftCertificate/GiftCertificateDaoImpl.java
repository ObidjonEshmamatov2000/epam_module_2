package com.epam.esm.dao.giftCertificate;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.giftCertificate.GiftCertificateMapper;
import com.epam.esm.model.tag.Tag;
import com.epam.esm.model.tag.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        jdbcTemplate.update("delete from gift_certificate_tag where gift_certificate_id = ?", id);
        return jdbcTemplate.update("delete from gift_certificate where id = ?", id);
    }

    @Override
    public int update(GiftCertificate gc) {
        return jdbcTemplate.update("update gift_certificate " +
                "set name = ?, " +
                "description = ?, " +
                "price = ?, " +
                "duration = ?, " +
                "last_updated_date = ? " +
                "where id = ?; ",
                gc.getName(),
                gc.getDescription(),
                gc.getPrice(),
                gc.getDuration(),
                gc.getLastUpdateDate(),
                gc.getId());
    }

    @Override
    public List<GiftCertificate> getByTagName(String tagName) {
        List<GiftCertificate> giftCertificates = new ArrayList<>();

        Tag tag = jdbcTemplate.queryForObject("select id, name from tag where name = ?", new TagMapper(), tagName);
        assert tag != null;
        List<UUID> giftCertificateIds = jdbcTemplate.query("select gift_certificate_id from gift_certificate_tag where tag_id = ?", new RowMapper<UUID>() {
            @Override
            public UUID mapRow(ResultSet rs, int rowNum) throws SQLException {
                return UUID.fromString(rs.getString("gift_certificate_id"));
            }
        }, tag.getId());


        giftCertificateIds.forEach(id -> giftCertificates.add(get(id)));
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> searchByNameOrDescription(String name) {
        return jdbcTemplate.query("select * from search_by_name_or_desc(?)", new GiftCertificateMapper(), name);
    }

    @Override
    public List<GiftCertificate> searchAndGetByTagName(String searchWord, String tagName) {


        return jdbcTemplate.query("select gc.* from gift_certificate gc " +
                "                    inner join search_by_name(?) res on res.id = gc.id " +
                "                               where gc.id in " +
                "                                      (select gt.gift_certificate_id from gift_certificate_tag gt " +
                "                                              where gt.tag_id = " +
                "                                                    (select t.id from tag t where t.name = ?) " +
                "                                                        );",
                new GiftCertificateMapper(), searchWord, tagName);
    }
}
