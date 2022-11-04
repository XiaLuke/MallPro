package cn.xf.common.to.mq;

import lombok.Data;

/**
 * 发送到mq消息队列的to
 *
 * @author XF
 * @date 2022/10/17
 */
@Data
public class StockLockedTo {

    /** 库存工作单的id **/
    private Long id;

    /** 工作单详情的所有信息 **/
    private StockDetailTo detailTo;
}
