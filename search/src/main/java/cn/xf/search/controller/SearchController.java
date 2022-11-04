package cn.xf.search.controller;

import cn.xf.search.service.SearchService;
import cn.xf.search.vo.SearchParam;
import cn.xf.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 搜索控制器
 *
 * @author XF
 * @date 2022/09/03
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/list.html")
    public String toPage(SearchParam searchParam, Model model, HttpServletRequest request){
        searchParam.set_queryString(request.getQueryString());
        // 封装检索请求返回结果
        SearchResult resultObject = searchService.search(searchParam);
        model.addAttribute("result",resultObject);
        return "list";
    }
}
