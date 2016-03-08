package jp.hitting.svn_diff_browser.controller

import jp.hitting.svn_diff_browser.Constants
import jp.hitting.svn_diff_browser.model.RepositoryModel
import jp.hitting.svn_diff_browser.service.RepositoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*
import javax.servlet.http.HttpSession

/**
 * Repository Controller.
 * Created by hitting on 2016/03/04.
 */
@Controller
@RequestMapping("/repository")
class RepositoryController {

    @Autowired
    private val repositoryServiceImpl: RepositoryService? = null

    /**
     * Repository detail page
     *
     * @param id repository ID
     * @param model Model
     * @return detail view name
     */
    @RequestMapping("/{id}")
    fun detail(@PathVariable("id") id: Int, session: HttpSession, model: Model): String {
        val map = session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as? HashMap<Int, RepositoryModel>
        if (map == null || !map.containsKey(id)) {
            // TODO error message
            return "index"
        }

        model.addAttribute("logList", this.repositoryServiceImpl!!.getLogList(map.get(id)!!))

        return "detail"
    }

}
