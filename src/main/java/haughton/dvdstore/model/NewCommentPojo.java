package haughton.dvdstore.model;

/**
 * Created by danie on 19/05/2017.
 */
//pojo used for json with ajax
public class NewCommentPojo {
    //use "SUCCESS" AND "FAILURE" AND "TOOSHORT"
    private String success;
    private String text;
    private String userName;

    public NewCommentPojo(String success, String text, String userName) {
        this.success = success;
        this.text = text;
        this.userName = userName;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
