package jp.hitting.svn_diff_browser.service;

import jp.hitting.svn_diff_browser.model.LogInfo
import jp.hitting.svn_diff_browser.model.PathInfo
import jp.hitting.svn_diff_browser.model.RepositoryModel
import jp.hitting.svn_diff_browser.util.DiffUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.tmatesoft.svn.core.*
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl
import org.tmatesoft.svn.core.io.SVNRepository
import org.tmatesoft.svn.core.io.SVNRepositoryFactory
import org.tmatesoft.svn.core.wc.SVNDiffClient
import org.tmatesoft.svn.core.wc.SVNLogClient
import org.tmatesoft.svn.core.wc.SVNRevision
import org.tmatesoft.svn.core.wc.SVNWCUtil
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * Repository Service Implementation.
 * Created by hitting on 2016/02/14.
 */
@Service
class RepositoryServiceImpl : RepositoryService {


    @Value("\${commit-log-chunk}")
    private val commitLogChunk: Long = 10

    /**
     * constructor.
     * Repository setup.
     */
    init {
        DAVRepositoryFactory.setup()
        SVNRepositoryFactoryImpl.setup()
        FSRepositoryFactory.setup()
    }

    /**
     * {@inheritDoc}
     */
    override
    fun existsRepository(repositoryModel: RepositoryModel): Boolean {
        try {
            val repository = this.initRepository(repositoryModel) ?: return false
            val nodeKind = repository.checkPath("", -1)
            if (SVNNodeKind.NONE == nodeKind) {
                return false
            }
        } catch (e: SVNException) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * {@inheritDoc}
     */
    override fun getLogList(repositoryModel: RepositoryModel, path: String, lastRev: Long?): List<LogInfo> {
        val list = ArrayList<LogInfo>()
        try {
            val url = repositoryModel.url
            if (StringUtils.isEmpty(url)) {
                return Collections.emptyList()
            }

            //
            val svnUrl = SVNURL.parseURIDecoded(url) // FIXME
            val auth = SVNWCUtil.createDefaultAuthenticationManager(repositoryModel.userId, repositoryModel.password.toCharArray())
            val logClient = SVNLogClient(auth, null)
            val endRev = if (lastRev == null) SVNRevision.HEAD else SVNRevision.create(lastRev)
            logClient.doLog(svnUrl, arrayOf(path), endRev, endRev, SVNRevision.create(1), false, false, this.commitLogChunk, { logEntry ->
                println(logEntry.revision)
                val l = LogInfo()
                l.rev = logEntry.revision
                l.comment = logEntry.message ?: ""
                list.add(l)
            })
        } catch (e: SVNException) {
            e.printStackTrace()
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
            e.printStackTrace()
            return Collections.emptyList()
        }

        return list
    }

    /**
     * {@inheritDoc}
     */
    override fun getDiffList(repositoryModel: RepositoryModel, rev: Long): String {
        try {
            val url = repositoryModel.url
            if (StringUtils.isEmpty(url)) {
                return ""
            }

            //
            val svnUrl = SVNURL.parseURIDecoded(url) // FIXME
            val auth = SVNWCUtil.createDefaultAuthenticationManager(repositoryModel.userId, repositoryModel.password.toCharArray())
            val diffClient = SVNDiffClient(auth, null)

            val outputStream = ByteArrayOutputStream()
            diffClient.doDiff(svnUrl, SVNRevision.create(rev - 1), svnUrl, SVNRevision.create(rev), SVNDepth.INFINITY, false, outputStream)
            return DiffUtil.formatDiff(outputStream.toByteArray())
        } catch (e: SVNException) {
            e.printStackTrace()
            return ""
        }
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

        val svnUrl = SVNURL.parseURIDecoded(url) // FIXME
        val auth = SVNWCUtil.createDefaultAuthenticationManager(repositoryModel.userId, repositoryModel.password.toCharArray())
        val repository = SVNRepositoryFactory.create(svnUrl)
        repository.authenticationManager = auth

        return repository
    }

}
