package store.teabliss.member.entity.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import store.teabliss.member.entity.MemberRole;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(MemberRole.class)
public class MemberRoleTypeHandler implements TypeHandler<MemberRole> {

    // MyBatis Enum Role 객체 변환 핸들러

    @Override
    public void setParameter(PreparedStatement ps, int i, MemberRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getKey());
    }

    @Override
    public MemberRole getResult(ResultSet rs, String columnName) throws SQLException {
        String roleKey = rs.getString(columnName);
        return getRole(roleKey);
    }

    @Override
    public MemberRole getResult(ResultSet rs, int columnIndex) throws SQLException {
        String roleKey = rs.getString(columnIndex);
        return getRole(roleKey);
    }

    @Override
    public MemberRole getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String roleKey = cs.getString(columnIndex);
        return getRole(roleKey);
    }

    private MemberRole getRole(String roleKey) {
        MemberRole memberRole = null;
        switch (roleKey) {
            case "ROLE_USER":
                memberRole = MemberRole.USER;
                break;
            case "ROLE_SELLER":
                memberRole = MemberRole.SELLER;
                break;
        }

        return memberRole;
    }
}
