package jp.hitting.svn_diff_browser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main Controller
 * Created by hitting on 2016/02/06.
 */
@Controller
class MainController {

    /**
     * Index page
     * @return view name
     */
    @RequestMapping("/")
    fun index(): String {
        return "index"
    }

}
