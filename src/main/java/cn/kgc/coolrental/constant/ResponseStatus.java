package cn.kgc.coolrental.constant;

public enum ResponseStatus {
    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    NOT_LOGIN(433, "很抱歉，你还没有登录"),
    NOT_AUTHORITY(434, "没有Token认证信息"),
    AUTHORITY_TIME_OUT(435, "Token认证信息过期"),
    NO_PERM(436, "没有权限进行当前操作"),
    NO_ROLE(437, "不具备指定角色信息"),
    TOKEN_VERIFY_FAIL(438, "Token信息解析失败");

    private Integer code;
    private String msg;

    ResponseStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
