package org.mushare.wooder.service.common;

public enum ResultCode {

    Success(901),
    AccessTokenError(902),
    NoPrivilege(903),
    SaveInternalError(904),

    GroupIdError(1001),
    GroupEmailRegistered(1002),
    GrouprEmailNotRegistered(1003),
    GroupPasswordWrong(1004),

    ProjectIdError(2001),

    MemberIdError(3001),
    MemberEmailRegistered(3002),
    MemberrEmailNotRegistered(3003),
    MemberPasswordWrong(3004);

    public int code;

    private ResultCode(int code) {
        this.code = code;
    }

    public boolean equals(ResultCode code) {
        return this.code == code.code;
    }

}

