package jp.hitting.svn_diff_browser.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jp.hitting.svn_diff_browser.model.RepositoryModel;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;

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
//        val url = repositoryModel.url;
//        if (StringUtils.isEmpty(url)) {
//            return false;
//        }
//
//        // init
//        if (url.startsWith("http")) {
//            DAVRepositoryFactory.setup();
//        } else if (url.startsWith("svn")) {
//            SVNRepositoryFactoryImpl.setup();
//        } else if (url.startsWith("file")) {
//            FSRepositoryFactory.setup();
//        }
//
//        try {
//            SVNURL svnUrl = SVNURL.parseURIDecoded(url);
//        } catch (SVNException e) {
//            // TODO
//            e.printStackTrace();
//            return false;
//        }

        return false;
    }
}
