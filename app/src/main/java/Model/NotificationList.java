package Model;

public class NotificationList {

    String title,body,Status;



    public NotificationList(String title, String body, String Status) {
        this.title = title;
        this.body = body;
        this.Status = Status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
