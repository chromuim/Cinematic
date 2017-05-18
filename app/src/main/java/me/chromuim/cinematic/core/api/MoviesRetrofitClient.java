package me.chromuim.cinematic.core.api;

import android.support.annotation.NonNull;
import me.chromuim.cinematic.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chromuim on 17.05.17.
 */

public class MoviesRetrofitClient {

  public static Retrofit.Builder sBuilder = new Builder()
      .baseUrl(Constants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create());

  private static OkHttpClient.Builder sHttpBuilder = new OkHttpClient.Builder();

  @NonNull
  public static <T> T createService(Class<T> serviceClass) {
    sHttpBuilder.addInterceptor(chain -> {
      Request request = chain.request();
      HttpUrl url = request.url()
          .newBuilder()
          .addQueryParameter(Constants.API_KEY_PARAM, BuildConfig.API_KEY)
          .build();
      Request newRequest = request.newBuilder().url(url).build();
      return chain.proceed(newRequest);
    });

    Retrofit retrofit = sBuilder.client(sHttpBuilder.build()).build();
    return retrofit.create(serviceClass);
  }
}
