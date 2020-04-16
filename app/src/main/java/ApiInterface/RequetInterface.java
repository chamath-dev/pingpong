package ApiInterface;

import Model.Request;
import Model.RequestList;
import Model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RequetInterface {
  /*  @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/request-check")
    Call<Request> CheckRequest(@Header("Authorization") String auth, @Query("emp_no")String emp_no);*/


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/request-check")
    Call<RequestList> CheckRequest(@Header("Authorization") String auth, @Query("emp_no")String emp_no);

    //@Headers({"Content-Type: application/json"})
    @POST("api/request")
    Call<Request> CreateReq(@Header("Authorization") String auth,@Body Request request);


    @PUT("api/send-otp")
    Call<Request> SendOTP (@Header("Authorization") String auth,@Body Request request);


    @PUT("api/start-work")
    Call<Request> StartWork (@Header("Authorization") String auth,@Body Request request);

    @PUT("api/end-work")
    Call<Request> EndWork (@Header("Authorization") String auth,@Body Request request);



}
