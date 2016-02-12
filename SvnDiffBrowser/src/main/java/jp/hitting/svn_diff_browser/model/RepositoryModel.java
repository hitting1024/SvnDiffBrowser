package jp.hitting.svn_diff_browser.model;

/**
 * Repository Information.
 * Created by hitting on 2016/02/06.
 */
public class RepositoryModel {

    private String url;
    private String userId;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
