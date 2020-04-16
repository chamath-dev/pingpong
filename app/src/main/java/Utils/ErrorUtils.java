package Utils;

import android.util.Log;

import com.example.tfxi.pingpong.UserDashBoard;

import java.io.IOException;
import java.lang.annotation.Annotation;

import Config.Config;
import Model.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {

        Log.e("fdkihfjdkhgfhklsf",response.message());
        Converter<ResponseBody, ApiError> converter = Config.getRetrofitInstance().responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            assert response.errorBody() != null;
            error = converter.convert(response.errorBody());

        } catch (IOException e) {
            Log.e("fdkihfjdkhgfhklsf",e.toString());
            return new ApiError();
        }

        return error;
    }
}
