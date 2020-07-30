package com.davidlutta.ytsapp.api;

import com.davidlutta.ytsapp.GlobalApplication;
import com.davidlutta.ytsapp.util.Constants;
import com.davidlutta.ytsapp.util.LiveDataCallAdapterFactory;
import com.davidlutta.ytsapp.util.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";
    private static long cacheSize = (10 * 1024 * 1024); // 10 MB

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Constants.MOVIESAPI)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create());
//            .client(okHttpClient());

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit.Builder retrofitBuilder2 = new Retrofit.Builder()
            .baseUrl(Constants.EXPRESSAPI)
            .addCallAdapterFactory(new LiveDataCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson));
//            .client(okHttpClient());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static Retrofit retrofit2 = retrofitBuilder2.build();

    private static MoviesApi moviesApi = retrofit.create(MoviesApi.class);

    private static ExpressApi expressApi = retrofit2.create(ExpressApi.class);

    public static MoviesApi getMoviesApi() {
        return moviesApi;
    }

    public static ExpressApi getExpressApi() {
        return expressApi;
    }

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cache(cache())
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor())
                .readTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private static Cache cache() {
        return new Cache(new File(GlobalApplication.getAppContext().getCacheDir(), "http"), cacheSize);
    }

    /**
     * This interceptor will be called both if network is both available and unavailable
     *
     * @return Interceptor
     */
    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                ServiceManager serviceManager = new ServiceManager(GlobalApplication.getAppContext());
                if (!serviceManager.isNetworkAvailable()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * This interceptor will be called only if the network is available
     *
     * @return Interceptor
     */
    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }
}
