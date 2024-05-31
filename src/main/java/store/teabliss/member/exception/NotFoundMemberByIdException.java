package store.teabliss.member.exception;

import lombok.Getter;

@Getter
public class NotFoundMemberByIdException extends MemberException {
    private final Long memberId;

    public NotFoundMemberByIdException(Long memberId) {
        super(MemberStatus.NOT_FOUND_MEMBER_BY_ID);
        this.memberId = memberId;
    }
}
