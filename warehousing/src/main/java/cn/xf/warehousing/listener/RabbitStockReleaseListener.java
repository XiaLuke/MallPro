package cn.xf.warehousing.listener;

import cn.xf.common.enume.OrderStatusEnum;
import cn.xf.common.to.mq.OrderTo;
import cn.xf.common.to.mq.StockDetailTo;
import cn.xf.common.to.mq.StockLockedTo;
import cn.xf.common.utils.R;
import cn.xf.warehousing.entity.WareOrderTaskDetailEntity;
import cn.xf.warehousing.entity.WareOrderTaskEntity;
import cn.xf.warehousing.feign.OrderFeignService;
import cn.xf.warehousing.service.WareOrderTaskDetailService;
import cn.xf.warehousing.service.WareOrderTaskService;
import cn.xf.warehousing.service.WareSkuService;
import cn.xf.warehousing.vo.OrderVo;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * RabbitMQ中仓库锁定释放监听器
 *
 * @author XF
 * @date 2022/10/22
 */
@Service
// 监听库存解锁队列
@RabbitListener(queues = "stock.release.queue")
@Slf4j
public class RabbitStockReleaseListener {

    @Autowired
    private WareSkuService wareSkuService;


    /**
     * 【释放锁定】的库存（需要监听队列是否存在消息）
     * --
     * 锁定库存在下订单时对仓库中库存锁定
     * --
     * 监听消息队列中数据进行释放
     * --
     * 情况1：下订单成功，订单过期没有支付或被系统自动取消、被用户取消。需要解锁库存
     * 情况2：下订单成功，库存锁定成功，后续业务调用失败导致订单回滚，锁定的库存需要解锁
     *
     * @param message 消息
     */
    @RabbitHandler
    public void releaseStock(Message message, StockLockedTo to, Channel channel) throws IOException {
        log.info("开始解锁库存");
        // 查看解锁和释放方法中有没有出现异常，直接捕获，成功的手动消费，失败的回队列
        try {
            wareSkuService.releaseAndUnlock(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /**
     * 处理订单关闭时发送过来的消息
     */
    @RabbitHandler
    public void handleOrderReleaseInfo(OrderTo order, Message message, Channel channel) throws IOException {
        log.info("收到订单释放消息，开始解锁库存");
        try {
            wareSkuService.releaseAndUnlock(order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
