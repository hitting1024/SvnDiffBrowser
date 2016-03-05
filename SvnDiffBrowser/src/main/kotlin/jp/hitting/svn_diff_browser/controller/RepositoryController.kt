package jp.hitting.svn_diff_browser.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Repository Controller.
 * Created by hitting on 2016/03/04.
 */
@Controller
@RequestMapping("/repository")
class RepositoryController {

    /**
     * Repository detail page
     */
    @RequestMapping("/{id}")
    fun detail(@PathVariable("id") id: Int): String {
        return "detail"
    }

}