package cn.xf.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 三方服务远程请求
 *
 * @author XF
 * @date 2022/10/16
 */
@FeignClient("third-party")
public interface ThridFeignClient {

//    @GetMapping(value = "/pay",consumes = "application/json")
//    String pay(@RequestBody PayVo vo) throws AlipayApiException;

}
