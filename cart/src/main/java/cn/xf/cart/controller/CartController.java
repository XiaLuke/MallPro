package cn.xf.cart.controller;

import cn.xf.cart.interceptor.CartInterceptor;
import cn.xf.cart.service.CartService;
import cn.xf.cart.to.UserInfoTo;
import cn.xf.cart.vo.CartItemVo;
import cn.xf.cart.vo.CartVo;
import cn.xf.common.constant.AuthVerifyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 获取当前用户购物车中选中的物品信息
     *
     * @return {@link List}<{@link CartItemVo}>
     */
    @GetMapping("/getCartWhichAreCheckedList")
    @ResponseBody
    public List<CartItemVo> getCurrentUserCartListWhichAreChecked(){
        return cartService.getCurrentUserCartListWhichAreChecked();
    }


    /**
     * 通过点击购物车到购物车详情页中
     * <p>
     * 浏览器中有一个cookie:user-key，京东一个月后失效
     * 如果第一次使用购物车，会给用户一个临时身份
     * 浏览器保存，以后每次访问都会带上cookie
     * <p>
     * 如果登录，session中有
     * 没有登录，使用cookie中的user-key
     * 第一次使用购物车，创建一个临时用户，并以后也使用这个用户
     *
     * @return {@link String}
     */
    @GetMapping("/cart.html")
    public String toCartListPage(Model model) throws ExecutionException, InterruptedException {

        // 获取前置拦截器中存入的UserInfo
        // UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();

        // 整合离线购物车和在线购物车数据
        CartVo cartVo = cartService.integrationCart();
        model.addAttribute("cart", cartVo);
        return "index";
    }

    /**
     * 添加商品到购物车
     */
    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes attributes) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId, num);
        attributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.mall.xyz/addToCartSuccessPage.html";
    }

    @GetMapping(value = "/addToCartSuccessPage.html")
    public String addSuccess(@RequestParam("skuId") Long skuId, Model model) {
        //重定向到成功页面。再次查询购物车数据即可
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }

    /**
     * 商品是否选中
     * @param skuId
     * @param checked
     * @return
     */
    @GetMapping(value = "/checkItem")
    public String checkItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "checked") Integer checked) {
        cartService.checkItem(skuId,checked);
        return "redirect:http://cart.mall.xyz/cart.html";
    }

    /**
     * 改变商品数量
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping(value = "/countItem")
    public String countItem(@RequestParam(value = "skuId") Long skuId,
                            @RequestParam(value = "num") Integer num) {
        cartService.changeItemCount(skuId,num);
        return "redirect:http://cart.mall.xyz/cart.html";
    }


    /**
     * 删除商品信息
     * @param skuId
     * @return
     */
    @GetMapping(value = "/deleteItem")
    public String deleteItem(@RequestParam("skuId") Integer skuId) {
        cartService.deleteIdCartInfo(skuId);
        return "redirect:http://cart.mall.xyz/cart.html";

    }
}
