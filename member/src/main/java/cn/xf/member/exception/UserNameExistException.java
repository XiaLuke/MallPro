package cn.xf.member.exception;

/**
 * 用户名存在重复异常
 *
 * @author XF
 * @date 2022/09/15
 */
public class UserNameExistException extends RuntimeException{
    public UserNameExistException(){
        super("用户名重复");
    }
}
