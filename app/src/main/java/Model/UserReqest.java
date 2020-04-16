package Model;

public class UserReqest {
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

    String Resone,Status;

    public UserReqest(String resone, String status) {
        Resone = resone;
        Status = status;
    }
}
