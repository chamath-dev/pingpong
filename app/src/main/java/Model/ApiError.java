package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError {

    @SerializedName("message")
    @Expose
    private String message;

    public ApiError() {
    }

    public String message() {
        return message;
    }



}
