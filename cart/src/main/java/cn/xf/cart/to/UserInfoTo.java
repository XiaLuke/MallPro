package cn.xf.cart.to;

import lombok.Data;
import lombok.ToString;

/**
 * 传输给Controller的UserInfo
 *
 * @author XF
 * @date 2022/10/11
 */
@Data
@ToString
public class UserInfoTo {
    /**
     * 用户登录了存在用户id
     */
    private Long userId;

    /**
     * 用户没登陆，存在userKey
     */
    private String userKey;

    /**
     * 临时用户
     */
    private boolean tempUser = false;
}
