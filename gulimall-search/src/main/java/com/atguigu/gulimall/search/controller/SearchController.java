package com.atguigu.gulimall.search.controller;

import com.atguigu.gulimall.search.service.MallSearchService;
import com.atguigu.gulimall.search.vo.SearchParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lxh
 * @createTime 2022-03-23 22:07:13
 */
@Controller
@RequiredArgsConstructor
public class SearchController {
    private final MallSearchService mallSearchService;

    @GetMapping({"/", "list.html"})
    public String listPage(SearchParam param, Model model) {
//        到es中检索
        Object result = mallSearchService.search(param);
        model.addAttribute("result", result);
        return "list";
    }
}
