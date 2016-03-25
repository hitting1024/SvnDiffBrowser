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
     * @param dir target directory
     * @param session HttpSession
     * @param model Model
     * @return detail view name
     */
    @RequestMapping("/{id}")
    fun detail(@PathVariable("id") id: Int, path: String?, session: HttpSession, model: Model): String {
        val map = session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as? HashMap<Int, RepositoryModel>
        if (map == null || !map.containsKey(id)) {
            // TODO error message
            return "index"
        }

        val repository = map.get(id)!!
        val temp = repository.url.split("/")
        val repositoryName = temp.get(temp.size - 1)
        model.addAttribute("repositoryName", repositoryName)

        val targetPath = if (path == null) "/" else "/" + path
        model.addAttribute("path", targetPath)
        // FIXME init repository twice
        model.addAttribute("logList", this.repositoryServiceImpl!!.getLogList(repository, targetPath))
        model.addAttribute("pathList", this.repositoryServiceImpl!!.getPathList(repository, targetPath))

        return "detail"
    }

    /**
     * Repository commit log page at revision.
     *
     * @param id repository ID
     * @param rev revision
     * @param session HttpSession
     * @param model Model
     * @return diff view name
     */
    @RequestMapping("/{id}/rev/{rev}")
    fun diff(@PathVariable("id") id: Int, @PathVariable("rev") rev: Long, path: String?, session: HttpSession, model: Model): String {
        val map = session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as? HashMap<Int, RepositoryModel>
        if (map == null || !map.containsKey(id)) {
            // TODO error message
            return "index"
        }

        val targetPath = if (path == null) "/" else "/" + path

        // TODO
        this.repositoryServiceImpl!!.getDiffList(map.get(id)!!, targetPath, rev)
        return "diff"
    }

}
