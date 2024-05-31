package store.teabliss.member.exception;

import lombok.Getter;

@Getter
public class DuplicationNicknameException extends MemberException {
    private final String nickname;

    public DuplicationNicknameException(String nickname) {
        super(MemberStatus.DUPLICATE_NICKNAME);
        this.nickname = nickname;
    }
}
