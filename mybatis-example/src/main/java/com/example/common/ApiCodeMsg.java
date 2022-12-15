package com.example.common;

/**
 * @author zhiqin.zhang
 */
public enum ApiCodeMsg {
    /**
     * code 接口状态码
     * desc 状态描述
     */
    ERROR(500, "未知异常，请联系管理员"), PARAMS_IS_INVALID(401, "参数不合法"), METHOD_NOT_ALLOWED(403, "不允许访问的方法"), NOT_FOUND(404, "接口不存在"), NOT_LOGIN(405, "请登录"), BAD_REQUEST(400, ""), UNAUTHORIZED(504, ""), BUSINESS(-1, "业务异常"), SUCCESS(0, "success");

    private Integer code;
    private String desc;

    ApiCodeMsg(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
