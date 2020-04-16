package Model;

public class PendingUserList {
    String Username;
    String UserID;
    String devision;

    public String getBan_Type() {
        return Ban_Type;
    }

    public void setBan_Type(String ban_Type) {
        Ban_Type = ban_Type;
    }

    String Ban_Type;





    public String getDevision() {
        return devision;
    }

    public void setDevision(String devision) {
        this.devision = devision;
    }

    public PendingUserList(String username, String userID , String devision_,String Ban_Type_) {
        Username = username;
        UserID = userID;
        devision =devision_;
        Ban_Type = Ban_Type_;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }




}
