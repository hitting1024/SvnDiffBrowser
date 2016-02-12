package jp.hitting.svn_diff_browser.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import jp.hitting.svn_diff_browser.model.RepositoryModel;

/**
 * Repository RestController.
 * Created by hitting on 2016/02/06.
 */
@RestController
@RequestMapping("/repository")
public class RepositoryRestController {

    /**
     * get repository list.
     *
     * @return repository list
     */
    @RequestMapping("/list")
    public List<RepositoryModel> getRepositoryList() {
        return null;
    }

    /**
     * add repository.
     *
     * @param repositoryModel repository information (url, userid, password)
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addRepository(RepositoryModel repositoryModel) {
        return false;
    }

    /**
     * delete repository.
     *
     * @param url repository url
     * @return success: true, failure: false
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public boolean deleteRepository(String url) {
        return false;
    }

}
