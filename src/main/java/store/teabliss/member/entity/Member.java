package store.teabliss.member.entity;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Member {


    private int memId;

    private String email;

    private String password;

    private String name;

    private String nickname;

    private String profile;

}
