package cn.xf.member.exception;

/**
 * 手机存在重复异常
 *
 * @author XF
 * @date 2022/09/15
 */
public class PhoneExistException extends RuntimeException{

    public PhoneExistException(){
        super("手机号重复");
    }
}
