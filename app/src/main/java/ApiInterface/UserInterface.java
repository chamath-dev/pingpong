package ApiInterface;

import Model.Notification;
import Model.NotificationData;
import Model.Request;
import Model.RequestList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import Model.User;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserInterface {

    @POST("api/auth-register")
    Call<User> UserReg(@Body User user);

    @POST("api/auth-login")
    Call<User> GetjwtToken(@Body User user);

    @GET("api/user")
    Call<User> GetUserDetails(@Header("Authorization") String token);


    @GET("api/notification")
    Call<NotificationData> GetUserNotifications(@Header("Authorization") String token ,@Query("emp_no")String emp_no);

    @GET("api/count-notification")
    Call<Notification> CountNotifications(@Header("Authorization") String token ,@Query("emp_no")String emp_no);

    @PUT("api/update-notification")
    Call<Notification> UpdateNotification (@Header("Authorization") String auth, @Body Notification notification);

    @GET("api/LoadUserHistory")
    Call<RequestList> LoadUserRequestData(@Header("Authorization") String token , @Query("emp_no")String emp_no);


}
