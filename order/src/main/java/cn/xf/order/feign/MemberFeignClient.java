package cn.xf.order.feign;

import cn.xf.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("member")
public interface MemberFeignClient {

    @GetMapping("/member/memberreceiveaddress/{member}/address")
    public List<MemberAddressVo> getMemberReceiveAddress(@PathVariable("member") Long memberId);

}
