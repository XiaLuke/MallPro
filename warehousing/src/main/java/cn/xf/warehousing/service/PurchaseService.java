package cn.xf.warehousing.service;

import cn.xf.warehousing.vo.MergeVo;
import cn.xf.warehousing.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.xf.common.utils.PageUtils;
import cn.xf.warehousing.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author XF
 * @email xialuke98@gmail.com
 * @date 2022-02-05 23:13:33
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询新建且没有被领取的采购单
     *
     * @param params 参数个数
     * @return {@link PageUtils}
     */
    PageUtils queryPageNotReceived(Map<String, Object> params);

    /**
     * 合并采购单
     *
     * @param mergeVo 合并签证官
     */
    void mergePurchase(MergeVo mergeVo);

    /**
     * 领取采购单
     *
     * @param ids id
     */
    void received(List<Long> ids);

    /**
     * 完成采购单
     *
     * @param doneVo 做签证官
     */
    void done(PurchaseDoneVo doneVo);
}

