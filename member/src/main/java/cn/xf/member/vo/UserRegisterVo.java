package cn.xf.member.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author XF
 */
@Data
public class UserRegisterVo {

    private String userName;

    private String password;

    private String phone;
}