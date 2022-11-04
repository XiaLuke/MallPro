package cn.xf.order.service;

import cn.xf.common.vo.PayVo;
import cn.xf.order.vo.OrderConfirmVo;
import cn.xf.order.vo.OrderSubmitVo;
import cn.xf.order.vo.PayAsyncVo;
import cn.xf.order.vo.SubmitOrderResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.order.entity.OrderEntity;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 22:30:13
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 得到订单确认数据
     *
     * @return {@link OrderConfirmVo}
     */
    OrderConfirmVo getOrderConfirmData() throws ExecutionException, InterruptedException;

    /**
     * 订单提交返回数据
     *
     * @param vo 签证官
     * @return {@link SubmitOrderResponseVo}
     */
    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    /**
     * 根据订单号获取订单信息
     * --
     * 是否取消
     *
     * @param orderSn 订单sn
     * @return {@link OrderEntity}
     */
    OrderEntity getOrderInfoBySn(String orderSn);

    /**
     * RabbitMq订单延时队列消息过期时关闭订单方法
     *
     * @param orderEntity 订单实体
     */
    void closeOrder(OrderEntity orderEntity);

    /**
     * 获取订单支付信息
     *
     * @param orderSn 订单号
     * @return {@link PayVo}
     */
    PayVo getOrderPay(String orderSn);

    /**
     * 分页查询当前登录用户的所有订单信息
     * @param params
     * @return
     */
    PageUtils queryPageWithItem(Map<String, Object> params);

    /**
     * 修改订单状态
     *
     * @param asyncVo 异步签证官
     * @return {@link String}
     */
    String handlePayResult(PayAsyncVo asyncVo);

    /**
     * 微信异步通知处理
     * @param notifyData
     */
    String asyncNotify(String notifyData);
}

