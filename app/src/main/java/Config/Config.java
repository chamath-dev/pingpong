package Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    public static final String Api="http://192.168.1.6:8000/";


    public static Retrofit getRetrofitInstance() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl(Api)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



}
