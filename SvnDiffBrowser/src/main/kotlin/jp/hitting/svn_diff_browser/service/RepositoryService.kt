package jp.hitting.svn_diff_browser.service;

import jp.hitting.svn_diff_browser.model.DiffInfo
import jp.hitting.svn_diff_browser.model.LogInfo
import jp.hitting.svn_diff_browser.model.PathInfo
import jp.hitting.svn_diff_browser.model.RepositoryModel

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

    /**
     * get log list.
     *
     * @param repositoryModel repository info
     * @param path target path
     * @param lastRev the end of revision to get commit log
     * @return LogInfo list
     */
    fun getLogList(repositoryModel: RepositoryModel, path: String, lastRev: Long?): List<LogInfo>

    /**
     * get path list.
     *
     * @param repositoryModel repository info
     * @param path target path
     * @return PathInfo list
     */
    fun getPathList(repositoryModel: RepositoryModel, path: String): List<PathInfo>

    /**
     * get diff list.
     *
     * @param repositoryModel repository info
     * @param rev target revision
     * @return DiffInfo list
     */
    fun getDiffList(repositoryModel: RepositoryModel, rev: Long): List<DiffInfo>

}
