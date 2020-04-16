package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Users implements Serializable {

    @SerializedName("user")
    @Expose
    private List<Users> user = null;

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("empoyee_no")
    @Expose
    private String empoyeeNo;
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("Ban_Type")
    @Expose
    private String Ban_Type;

    public String getBan_Type() {
        return Ban_Type;
    }

    public void setBan_Type(String ban_Type) {
        Ban_Type = ban_Type;
    }




    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("email_verified_at")
    @Expose
    private Object emailVerifiedAt;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("uuID")
    @Expose
    private String uuID;
    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;


    public String getUser_devision() {
        return user_devision;
    }

    public void setUser_devision(String user_devision) {
        this.user_devision = user_devision;
    }

    @SerializedName("user_devision")
    @Expose
    private String user_devision;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmpoyeeNo() {
        return empoyeeNo;
    }

    public void setEmpoyeeNo(String empoyeeNo) {
        this.empoyeeNo = empoyeeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Object getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Object emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
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

}
