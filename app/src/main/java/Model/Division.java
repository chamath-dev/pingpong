package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Division implements Serializable {

    @SerializedName("division")
    @Expose
    private List<Division> division = null;

    public List<Division> getDivision() {
        return division;
    }

    public void setDivision(List<Division> division) {
        this.division = division;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("devision_name")
    @Expose
    private String devisionName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevisionName() {
        return devisionName;
    }

    public void setDevisionName(String devisionName) {
        this.devisionName = devisionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
