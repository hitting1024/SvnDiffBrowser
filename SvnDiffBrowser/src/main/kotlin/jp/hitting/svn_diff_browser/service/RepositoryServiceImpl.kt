package jp.hitting.svn_diff_browser.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.hitting.svn_diff_browser.model.RepositoryModel;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil

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
        val url = repositoryModel.url
        if (StringUtils.isEmpty(url)) {
            return false
        }

        // init
        if (url.startsWith("http", false)) {
            DAVRepositoryFactory.setup()
        } else if (url.startsWith("svn")) {
            SVNRepositoryFactoryImpl.setup()
        } else if (url.startsWith("file")) {
            FSRepositoryFactory.setup()
        }

        try {
            val svnUrl = SVNURL.parseURIDecoded(url) // FIXME
            val auth = SVNWCUtil.createDefaultAuthenticationManager(repositoryModel.userId, repositoryModel.password.toCharArray())
            val repository = SVNRepositoryFactory.create(svnUrl)
            repository.authenticationManager = auth
            val nodeKind = repository.checkPath("", -1);
            if (nodeKind == SVNNodeKind.NONE) {
                return false;
            }
        } catch (e: SVNException) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
