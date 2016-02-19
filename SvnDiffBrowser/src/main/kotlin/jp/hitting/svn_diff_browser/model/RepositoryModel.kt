package jp.hitting.svn_diff_browser.model;

/**
 * Repository Information.
 * Created by hitting on 2016/02/06.
 */
class RepositoryModel {

    var url = ""
    var userId = ""
    var password = ""

    override
    fun hashCode(): Int {
        return (this.url + "^" + this.userId).hashCode()
    }

}
