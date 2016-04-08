package jp.hitting.svn_diff_browser.controller;

import jp.hitting.svn_diff_browser.Constants
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

    @Autowired
    private val repositoryServiceImpl: RepositoryService? = null

    /**
     * get repository list.
     *
     * @return repository list
     */
    @RequestMapping("/list")
    fun getRepositoryList(session: HttpSession): Map<Int, RepositoryModel>? {
        return session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as? Map<Int, RepositoryModel>
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
        var map: HashMap<Int, RepositoryModel>? = session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as? HashMap<Int, RepositoryModel>
        if (map == null) {
            map = HashMap<Int, RepositoryModel>()
        } else if (map.containsKey(repositoryModel.hashCode())) {
            // already exists
            return false
        }
        map.put(repositoryModel.hashCode(), repositoryModel)
        session.setAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY, map)
        return true
    }

    /**
     * delete repository.
     *
     * @param url repository url
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/delete", method = arrayOf(RequestMethod.POST))
    fun deleteRepository(repositoryId: Int, session: HttpSession): Boolean {
        val map = session.getAttribute(Constants.SessionKey.SESSION_REPOSITORY_KEY) as Map<Int, RepositoryModel>;
        if (map == null) {
            return true;
        }
        map.remove(repositoryId);
        return true;
    }

}
