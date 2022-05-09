package com.epam.esm.domain.giftCertificate;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(UUID.fromString(rs.getString("id")));
        giftCertificate.setName(rs.getString("name"));
        giftCertificate.setDescription(rs.getString("description"));
        giftCertificate.setPrice(rs.getBigDecimal("price"));
        giftCertificate.setDuration(rs.getInt("duration"));
        giftCertificate.setCreateDate(rs.getTimestamp("created_date").toLocalDateTime());
        giftCertificate.setLastUpdateDate(rs.getTimestamp("last_updated_date").toLocalDateTime());

        return giftCertificate;
    }
}
