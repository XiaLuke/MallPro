package cn.xf.common.exception;

/**
 * 通用异常代码
 *
 * @author XF
 * @date 2022/02/16
 */
public enum CommonExceptionCode {
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    UNKNOWN_EXCEPTION(10002,"未知异常"),
    PRODUCT_UP_EXCEPTION(11000,"商品上架异常");



    private int code;
    private String msg;

    CommonExceptionCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
