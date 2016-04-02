package jp.hitting.svn_diff_browser.model;

import org.springframework.util.StringUtils

/**
 * Repository Information.
 * Created by hitting on 2016/02/06.
 */
class RepositoryModel {

    var url = ""
    var userId = ""
    var password = ""

    fun getRepositoryName(): String {
        if (StringUtils.isEmpty(this.url)) {
            return ""
        }
        val temp = this.url.split("/")
        return temp.get(temp.size - 1)
    }

    override
    fun hashCode(): Int {
        return (this.url + "^" + this.userId).hashCode()
    }

}
