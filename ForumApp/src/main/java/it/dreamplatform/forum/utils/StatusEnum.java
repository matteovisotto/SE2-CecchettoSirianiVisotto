package it.dreamplatform.forum.utils;

public enum StatusEnum {
    PENDING,ACCEPTED,ERROR;

    StatusEnum getStatusById(int statusId) {
        switch (statusId) {
            case 0:
                return StatusEnum.PENDING;
            case 1:
                return StatusEnum.ACCEPTED;
            default:
                return StatusEnum.ERROR;
        }
    }
}