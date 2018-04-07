package com.example.suhail.loginattempt1.ApiClient;

/**
 * Created by Suhail on 8/7/2017.
 */

        import com.example.suhail.loginattempt1.Interfaces.ApiInterface;

        import java.util.concurrent.TimeUnit;

        import okhttp3.OkHttpClient;
        import retrofit2.Retrofit;
        import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "http://wittymonk.com/"; //put base url here
    private static Retrofit retrofit = null;


    public static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100,TimeUnit.SECONDS).build();


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofit.create(ApiInterface.class);
        }
        return retrofit;
    }
}
