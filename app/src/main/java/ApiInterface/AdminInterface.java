package ApiInterface;

import Model.AdminRequest;
import Model.Division;
import Model.Notification;
import Model.NotificationData;
import Model.Request;
import Model.RequestList;
import Model.User;
import Model.UserList;
import Model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AdminInterface {


  /*  @POST("api/auth-register")
    Call<User> UserReg(@Body User user);

   */

    @GET("api/working-details")
    Call<AdminRequest> getWorkingUsers(@Header("Authorization") String token);


    @GET("api/pending-request")
    Call<AdminRequest> getPendingRequest(@Header("Authorization") String token);

    @GET("api/pending-users")
    Call<UserList> getPendingUsers(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/admin-request-action-approve")
    Call<Request> AproveAction(@Header("Authorization") String token,@Body Request request);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/admin-request-action-reject")
    Call<Request> RejectAction(@Header("Authorization") String token,@Body Request request);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/Allow-User")
    Call<Users> AllowUser (@Header("Authorization") String auth, @Body Users notification);

    @GET("api/Today-report")
    Call<AdminRequest> TodayReport(@Header("Authorization") String auth);


    @GET("api/ActievUser")
    Call<Users> ActiveUserList(@Header("Authorization") String auth);

    @GET("api/UnActievUser")
    Call<Users> UnActiveUserList(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/ActievUsers-User")
    Call<Users> ActievUsers (@Header("Authorization") String auth, @Body Users users);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/UnActievUsers-User")
    Call<Users> UnActiveUsers (@Header("Authorization") String auth, @Body Users users);


    @GET("api/LoadDevision")
    Call<Division> DevisionList(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/BandDevision")
    Call<Division> BanDevision (@Header("Authorization") String auth, @Body Division division);



    @GET("api/ActiveDivisionData")
    Call<Division> ActiveDevisionData(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/ActiveDevision")
    Call<Division> ActiveDevision (@Header("Authorization") String auth, @Body Division division);

    @GET("api/UserReport")
    Call<Users> UserReport(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/OneUserReport")
    Call<RequestList> OneUserReport(@Header("Authorization") String auth, @Query("emp_no")String emp_no);



   /* @GET("api/notification")
    Call<NotificationData> GetUserNotifications(@Header("Authorization") String token , @Query("emp_no")String emp_no);

    @GET("api/count-notification")
    Call<Notification> CountNotifications(@Header("Authorization") String token , @Query("emp_no")String emp_no);

   */

}
