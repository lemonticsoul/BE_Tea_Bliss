package store.teabliss.member.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberRole {

    USER("user"),
    ADMIN("admin");

    final String key;

    public String getKey() {
        return key;
    }
}
