package cn.xf.warehousing.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 运费信息
 *
 * @author XF
 * @date 2022/10/16
 */
@Data
public class FareVo {

    private MemberAddressVo address;

    private BigDecimal fare;

}
