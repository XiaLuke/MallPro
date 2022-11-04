package cn.xf.order.web;

import cn.xf.common.exception.NoStockException;
import cn.xf.order.service.OrderService;
import cn.xf.order.vo.OrderConfirmVo;
import cn.xf.order.vo.OrderSubmitVo;
import cn.xf.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {
    @Autowired
    OrderService orderService;

    /**
     * 订单去结算页面跳转
     *
     * @return {@link String}
     */
    @GetMapping("/toTrade")
    public String toTrade(HttpServletRequest request, Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo orderConfirmVo = orderService.getOrderConfirmData();
        model.addAttribute("confirmOrderData", orderConfirmVo);
        return "confirm";
    }

    /**
     * 提交订单
     * 流程：
     * 1.前端提交订单
     * 2.验证令牌
     * 3.令牌通过
     * 3.1.删除令牌，创建订单号
     * 3.2.获取购物车中所有选中项
     * 3.3.创建订单项
     * 3.4.获取收货人信息，填充订单
     * 3.5.计算订单金额
     * 3.6.验价
     * 4.令牌不通过
     * 4.1.此订单号是否存在
     *
     * @param vo         签证官
     * @param model      模型
     * @param attributes 属性
     * @return {@link String}
     */
    @PostMapping(value = "/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes attributes) {
        try {
            SubmitOrderResponseVo response = orderService.submitOrder(vo);
            if (response.getCode() == 0) {
                //成功
                model.addAttribute("submitOrderResp", response);
                return "pay";
            } else {
                String msg = "下单失败";
                switch (response.getCode()) {
                    case 1:
                        msg += "令牌订单信息过期，请刷新再次提交";
                        break;
                    case 2:
                        msg += "订单商品价格发生变化，请确认后再次提交";
                        break;
                    case 3:
                        msg += "库存锁定失败，商品库存不足";
                        break;
                }
                attributes.addFlashAttribute("msg", msg);
                return "redirect:http://order.mall.xyz/toTrade";
            }
        } catch (Exception e) {
            if (e instanceof NoStockException) {
                String message = ((NoStockException) e).getMessage();
                attributes.addFlashAttribute("msg", message);
            }
            return "redirect:http://order.mall.xyz/toTrade";
        }
    }
}
