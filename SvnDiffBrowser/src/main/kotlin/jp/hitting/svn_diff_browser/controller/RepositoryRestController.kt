package jp.hitting.svn_diff_browser.controller;

import jp.hitting.svn_diff_browser.model.RepositoryModel;
import jp.hitting.svn_diff_browser.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * Repository RestController.
 * Created by hitting on 2016/02/06.
 */
@RestController
@RequestMapping("/repository")
class RepositoryRestController {

    private val SESSION_REPOSITORY_KEY = "SESSION_REPOSITORY_KEY"

    @Autowired
    private val repositoryServiceImpl: RepositoryService? = null

    /**
     * get repository list.
     *
     * @return repository list
     */
    @RequestMapping("/list")
    fun getRepositoryList(session: HttpSession): Map<Int, RepositoryModel>? {
        return session.getAttribute(SESSION_REPOSITORY_KEY) as? Map<Int, RepositoryModel>
    }

    /**
     * add repository.
     *
     * @param repositoryModel repository information (url, userid, password)
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/add", method = arrayOf(RequestMethod.POST))
    fun addRepository(repositoryModel: RepositoryModel, session: HttpSession): Boolean {
        if (!this.repositoryServiceImpl!!.existsRepository(repositoryModel)) {
            return false
        }

        // save
        var map: HashMap<Int, RepositoryModel>? = session.getAttribute(SESSION_REPOSITORY_KEY) as? HashMap<Int, RepositoryModel>
        if (map == null) {
            map = HashMap<Int, RepositoryModel>()
        }
        if (!map.containsKey(repositoryModel.hashCode())) {
            map.put(repositoryModel.hashCode(), repositoryModel)
        }
        session.setAttribute(SESSION_REPOSITORY_KEY, map)
        return true
    }

    /**
     * delete repository.
     *
     * @param url repository url
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/delete", method = arrayOf(RequestMethod.DELETE))
    fun deleteRepository(url: String, session: HttpSession): Boolean {
        val map = session.getAttribute(SESSION_REPOSITORY_KEY) as Map<Int, RepositoryModel>;
        if (map == null) {
            return true;
        }
        map.remove(url);
        return true;
    }

}
