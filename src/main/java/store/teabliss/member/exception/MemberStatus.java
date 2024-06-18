package store.teabliss.member.exception;

import lombok.Getter;

@Getter
public enum MemberStatus {
    MEMBER_JOIN_FAIL(400, "회원가입에 실패했습니다."),
    DUPLICATE_MEMBER(400, "이메일 중복된 회원이 있습니다."),
    DUPLICATE_NICKNAME(400, "닉네임 중복된 회원이 있습니다."),
    NOT_EQUAL_OLD_PASSWORD(400, "기존 비밀번호와 같지 않습니다."),
    NOT_EQUAL_NEW_PASSWORD(400, "새 비밀번호 비교가 다릅니다."),
    NOT_FOUND_MEMBER_BY_ID(400, "회원 아이디와 일치하는 정보가 없습니다."),
    NOT_FOUND_MEMBER_BY_EMAIL(400, "회원 이메일과 일치하는 정보가 없습니다.");

    private final int status;
    private final String message;

    MemberStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
