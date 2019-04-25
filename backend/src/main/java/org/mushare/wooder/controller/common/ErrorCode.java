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
    GroupExist(1002, "This group email has been registered."),
    GroupNotExist(1003, "This email is not exsit."),
    GroupPasswordWrong(1004, "Password is wrong."),

    // MemberController
    MemberIdNotExist(2001, "Member id is not existing."),
    MemberExist(2002, "This member email has been registered."),
    MemberNotExist(2003, "This member email has not been registered."),
    MemberPasswordWrong(2004, "Password is wrong.");

    public int code;
    public String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
