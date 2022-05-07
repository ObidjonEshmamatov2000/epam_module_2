package com.epam.esm.dao.giftCertificate;

import com.epam.esm.model.giftCertificate.GiftCertificate;
import com.epam.esm.model.giftCertificate.GiftCertificateMapper;
import com.epam.esm.model.tag.Tag;
import com.epam.esm.model.tag.TagMapper;
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
        String query =
                "insert into gift_certificate(id, name, description, price, duration, " +
                "created_date, last_updated_date) values(?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(
                query,
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
        String query = "select * from gift_certificate where id = ?";
        return jdbcTemplate.queryForObject(query, new GiftCertificateMapper(), id);
    }

    @Override
    public List<GiftCertificate> getAll() {
        String query = "select * from gift_certificate";
        return jdbcTemplate.query(query, new GiftCertificateMapper());
    }

    @Override
    public int delete(UUID id) {
        jdbcTemplate.update("delete from gift_certificate_tag where gift_certificate_id = ?", id);
        return jdbcTemplate.update("delete from gift_certificate where id = ?", id);
    }

    @Override
    public int update(GiftCertificate gc) {
        String query =
                "update gift_certificate set name = ?, description = ?, price = ?, " +
                "duration = ?, last_updated_date = ? where id = ?; ";
        return jdbcTemplate.update(
                query,
                gc.getName(),
                gc.getDescription(),
                gc.getPrice(),
                gc.getDuration(),
                gc.getLastUpdateDate(),
                gc.getId());
    }

    @Override
    public void createTagsWithGiftCertificate(UUID certificateId, List<Tag> tags) {
        tags.forEach(tag -> {
            tag.setId(UUID.randomUUID());
            jdbcTemplate.update("insert into tag(id, name) values(?, ?)", tag.getId(), tag.getName());
            jdbcTemplate.update("insert into gift_certificate_tag(tag_id, gift_certificate_id) values(?, ?)", tag.getId(), certificateId);
        });
    }

    @Override
    public List<GiftCertificate> getFilteredGifts(
            String searchWord,
            String tagName,
            boolean doNameSort,
            boolean doDateSort,
            boolean isDescending
    ) {

        return jdbcTemplate.query("select * from get_filtered_gifts(?, ?, ?, ?, ?)",
                new GiftCertificateMapper(),
                searchWord, tagName, doNameSort, doDateSort, isDescending);
    }
}
