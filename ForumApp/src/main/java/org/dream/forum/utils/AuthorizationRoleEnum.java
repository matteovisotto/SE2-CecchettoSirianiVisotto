package org.dream.forum.utils;

public enum AuthorizationRoleEnum {
    VISITOR("visitor"),
    USER("user"),
    POLICY_MAKER("policy_maker"),
    ADMIN("admin");

    private final String value;

    AuthorizationRoleEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
