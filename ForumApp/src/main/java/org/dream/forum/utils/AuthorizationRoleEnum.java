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

    public AuthorizationRoleEnum getAuthorizationRole(String value) {
        switch (value){
            case "visitor":
                return AuthorizationRoleEnum.VISITOR;
            case "user":
                return AuthorizationRoleEnum.USER;
            case "policy_maker":
                return AuthorizationRoleEnum.POLICY_MAKER;
                default:
                    return AuthorizationRoleEnum.ADMIN;
        }
    }

}
