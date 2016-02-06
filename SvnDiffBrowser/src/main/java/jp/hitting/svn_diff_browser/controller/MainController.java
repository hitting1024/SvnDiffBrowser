package jp.hitting.svn_diff_browser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Main Controller
 * Created by hitting on 2016/02/06.
 */
@Controller
public class MainController {

    /**
     * Index page
     * @return view name
     */
    @RequestMapping({"/", "/index"})
    public String index() {
        return "index";
    }

}
