package cn.xf.search.service;

import cn.xf.search.vo.SearchParam;
import cn.xf.search.vo.SearchResult;

public interface SearchService {
    /**
     * 商品页面的加锁方法
     *
     * @param searchParam 检索的所有参数
     * @return {@link SearchResult} 页面中所有需要的信息
     */
    SearchResult search(SearchParam searchParam);
}
