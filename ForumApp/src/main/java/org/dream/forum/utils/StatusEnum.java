package org.dream.forum.utils;

public enum StatusEnum {
    PENDING(0),
    ACCEPTED(1);

    private final int value;

    StatusEnum(int value) {this.value = value;}

    public static StatusEnum getStatus(int value) {
        switch (value){
            case 0:
                return StatusEnum.PENDING;
            default:
                return StatusEnum.ACCEPTED;
        }
    }
}
