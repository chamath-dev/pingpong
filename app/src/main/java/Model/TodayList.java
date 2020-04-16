package Model;

public class TodayList {

    public TodayList(String userNane, String userID, String resone, String status) {
        UserNane = userNane;
        UserID = userID;
        Resone = resone;
        Status = status;
    }

    public String getUserNane() {
        return UserNane;
    }

    public void setUserNane(String userNane) {
        UserNane = userNane;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getResone() {
        return Resone;
    }

    public void setResone(String resone) {
        Resone = resone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    String UserNane,UserID,Resone,Status;
}
