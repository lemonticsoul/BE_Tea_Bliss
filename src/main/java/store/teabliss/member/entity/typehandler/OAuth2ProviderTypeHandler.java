package store.teabliss.member.entity.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import store.teabliss.common.security.oauth2.user.OAuth2Provider;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(OAuth2Provider.class)
public class OAuth2ProviderTypeHandler implements TypeHandler<OAuth2Provider> {


    @Override
    public void setParameter(PreparedStatement ps, int i, OAuth2Provider parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getRegistrationId());
    }

    @Override
    public OAuth2Provider getResult(ResultSet rs, String columnName) throws SQLException {
        String registration = rs.getString(columnName);
        return getProvider(registration);
    }

    @Override
    public OAuth2Provider getResult(ResultSet rs, int columnIndex) throws SQLException {
        String registration = rs.getString(columnIndex);
        return getProvider(registration);
    }

    @Override
    public OAuth2Provider getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String registration = cs.getString(columnIndex);
        return getProvider(registration);
    }

    private OAuth2Provider getProvider(String registrationId) {

        return switch (registrationId) {
            case "google" -> OAuth2Provider.GOOGLE;
            case "kakao" -> OAuth2Provider.KAKAO;
            case "naver" -> OAuth2Provider.NAVER;
            default -> null;
        };
    }
}
