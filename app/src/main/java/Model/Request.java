package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Request implements Serializable {



    @SerializedName("request")
    @Expose
    private Request request;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @SerializedName("count")
    @Expose
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    @Expose
    private String name;


    public String getUser_devision() {
        return user_devision;
    }

    public void setUser_devision(String user_devision) {
        this.user_devision = user_devision;
    }

    @SerializedName("user_devision")
    @Expose
    private String user_devision;


    public Integer getSetTime() {
        return Set_time;
    }

    public void setSetTime(Integer setTime) {
        this.Set_time = setTime;
    }

    @SerializedName("Set_time")
    @Expose
    private Integer Set_time;

    public String getStarted_time() {
        return Started_time;
    }

    public void setStarted_time(String started_time) {
        Started_time = started_time;
    }

    @SerializedName("Started_time")
    @Expose
    private String Started_time;

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

    @SerializedName("Finished_time")
    @Expose
    private String Finished_time;

    public Integer getWorking_time() {
        return Working_time;
    }

    public void setWorking_time(Integer working_time) {
        Working_time = working_time;
    }

    @SerializedName("Working_time")
    @Expose
    private Integer Working_time;


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @SerializedName("action")
    @Expose
    private String action;












    @SerializedName("Otp_code")
    @Expose
    private String Otp_code;


    @SerializedName("error")
    @Expose
    private String error;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @SerializedName("Note")
    @Expose
    private String note;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getFinished_time() {
        return Finished_time;
    }

    public void setFinished_time(String finished_time) {
        Finished_time = finished_time;
    }

    public String getOtp_code() {
        return Otp_code;
    }

    public void setOtp_code(String otp_code) {
        Otp_code = otp_code;
    }

}
