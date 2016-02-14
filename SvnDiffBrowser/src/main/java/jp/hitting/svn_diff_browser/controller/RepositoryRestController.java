package jp.hitting.svn_diff_browser.controller;

import jp.hitting.svn_diff_browser.model.RepositoryModel;
import jp.hitting.svn_diff_browser.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Repository RestController.
 * Created by hitting on 2016/02/06.
 */
@RestController
@RequestMapping("/repository")
public class RepositoryRestController {

    private static String SESSION_REPOSITORY_KEY = "SESSION_REPOSITORY_KEY";

    @Autowired
    private RepositoryService repositoryServiceImpl;

    /**
     * get repository list.
     *
     * @return repository list
     */
    @RequestMapping("/list")
    public Map<String, RepositoryModel> getRepositoryList(HttpSession session) {
        return (Map<String, RepositoryModel>) session.getAttribute(SESSION_REPOSITORY_KEY);
    }

    /**
     * add repository.
     *
     * @param repositoryModel repository information (url, userid, password)
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addRepository(RepositoryModel repositoryModel, HttpSession session) {
        if (!this.repositoryServiceImpl.existsRepository(repositoryModel)) {
            return false;
        }

        // save
        Map<String, RepositoryModel> map = (Map<String, RepositoryModel>) session.getAttribute(SESSION_REPOSITORY_KEY);
        if (map == null) {
            map = new HashMap<>();
        }
        if (!map.containsKey(repositoryModel.getUrl())) {
            map.put(repositoryModel.getUrl(), repositoryModel);
        }
        session.setAttribute(SESSION_REPOSITORY_KEY, map);
        return true;
    }

    /**
     * delete repository.
     *
     * @param url repository url
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public boolean deleteRepository(String url, HttpSession session) {
        Map<String, RepositoryModel> map = (Map<String, RepositoryModel>) session.getAttribute(SESSION_REPOSITORY_KEY);
        if (map == null) {
            return true;
        }
        map.remove(url);
        return true;
    }

}
