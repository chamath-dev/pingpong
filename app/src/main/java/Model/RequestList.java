package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RequestList implements Serializable {

    @SerializedName("request")
    @Expose
    private List<RequestList> request = null;

    public List<RequestList> getRequest() {
        return request;
    }

    public void setRequest(List<RequestList> request) {
        this.request = request;
    }



    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("UserID")
    @Expose
    private String userID;
    @SerializedName("Request_resone")
    @Expose
    private String requestResone;
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Note")
    @Expose
    private String note;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("Set_time")
    @Expose
    private Integer Set_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRequestResone() {
        return requestResone;
    }

    public void setRequestResone(String requestResone) {
        this.requestResone = requestResone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSetTime() {
        return Set_time;
    }

    public void setSetTime(Integer setTime) {
        this.Set_time = setTime;
    }









}
