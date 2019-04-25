package org.mushare.wooder.controller.common;

public enum ErrorCode {

    ErrorUnknown(800, "Unknown error."),
    ErrorNoSession(801, "Session is none or timeout."),
    ErrorNoPrivilge(802, "No privilege to invoke this method."),
    ErrorObjecId(803, "Object cannot be found by the object id."),
    ErrorSavingObject(804, "Internal error saving object."),

    ErrorAccessToken(901, "Access token is wrong."),

    // GroupController
    GroupIdNotExist(1001, "Group id is not existing."),
    GroupExist(1011, "This group email has been registered."),
    GroupNotExist(1022, "This email is not exsit."),
    GroupPasswordWrong(1023, "Password is wrong."),
    GroupNotLogin(1031, "Group manager does not login.");

    public int code;
    public String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
