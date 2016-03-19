package jp.hitting.svn_diff_browser.service;

import jp.hitting.svn_diff_browser.model.LogInfo
import jp.hitting.svn_diff_browser.model.PathInfo
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.hitting.svn_diff_browser.model.RepositoryModel;
import org.tmatesoft.svn.core.*
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil
import java.util.*

/**
 * Repository Service Implementation.
 * Created by hitting on 2016/02/14.
 */
@Service
class RepositoryServiceImpl: RepositoryService {

    /**
     * {@inheritDoc}
     */
    override
    fun existsRepository(repositoryModel: RepositoryModel): Boolean {
        try {
            val repository = this.initRepository(repositoryModel) ?: return false
            val nodeKind = repository.checkPath("", -1);
            if (SVNNodeKind.NONE == nodeKind) {
                return false
            }
        } catch (e: SVNException) {
            e.printStackTrace();
            return false
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    override fun getLogList(repositoryModel: RepositoryModel, path: String): List<LogInfo> {
        val list = ArrayList<LogInfo>()
        try {
            val repository = this.initRepository(repositoryModel) ?: return Collections.emptyList()
            val rev = repository.latestRevision
            val logs = repository.log(arrayOf(path), null, 0, rev, false, false) as List<SVNLogEntry>
            logs.forEach {
                val l = LogInfo()
                l.rev = it.revision
                val message = it.message
                l.comment = if (message == null) "" else message

                list.add(l)
            }
        } catch (e: SVNException) {
            e.printStackTrace();
            return Collections.emptyList()
        }

        return list
    }

    /**
     * {@inheritDoc}
     */
    override fun getPathList(repositoryModel: RepositoryModel, path: String): List<PathInfo> {
        val list = ArrayList<PathInfo>()
        try {
            val repository = this.initRepository(repositoryModel) ?: return Collections.emptyList()
            val rev = repository.latestRevision
            val dirs = repository.getDir(path, rev, null, null as? Collection<*>) as Collection<SVNDirEntry>
            dirs.forEach {
                val p = PathInfo()
                p.path = it.relativePath
                p.comment = it.commitMessage
                p.isDir = (SVNNodeKind.DIR == it.kind)
                list.add(p)
            }
        } catch (e: SVNException) {
            e.printStackTrace();
            return Collections.emptyList()
        }

        return list
    }

    /**
     * init repository.
     *
     * @param repositoryModel repository information
     * @return svn repository with auth info
     * @throws SVNException throw it when svnkit can't connect repository
     */
    @Throws(SVNException::class)
    private fun initRepository(repositoryModel: RepositoryModel): SVNRepository? {
        val url = repositoryModel.url
        if (StringUtils.isEmpty(url)) {
            return null
        }

        // init
        // TODO handle concurrent access
        if (url.startsWith("http", false)) {
            DAVRepositoryFactory.setup()
        } else if (url.startsWith("svn")) {
            SVNRepositoryFactoryImpl.setup()
        } else if (url.startsWith("file")) {
            FSRepositoryFactory.setup()
        }

        val svnUrl = SVNURL.parseURIDecoded(url) // FIXME
        val auth = SVNWCUtil.createDefaultAuthenticationManager(repositoryModel.userId, repositoryModel.password.toCharArray())
        val repository = SVNRepositoryFactory.create(svnUrl)
        repository.authenticationManager = auth

        return repository
    }

}
