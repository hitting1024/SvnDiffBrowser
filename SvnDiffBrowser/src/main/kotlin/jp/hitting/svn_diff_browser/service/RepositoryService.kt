package jp.hitting.svn_diff_browser.service;

import jp.hitting.svn_diff_browser.model.RepositoryModel;

/**
 * Repository Service.
 * Created by hitting on 2016/02/14.
 */
interface RepositoryService {

    /**
     * confirm to exist repository.
     *
     * @param repositoryModel repository info
     * @return exist: true
     */
    fun existsRepository(repositoryModel: RepositoryModel): Boolean

}
