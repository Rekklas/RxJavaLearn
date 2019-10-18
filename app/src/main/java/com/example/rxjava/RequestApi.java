package com.example.rxjava;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

/**
 * Created on 10/18/2019.
 */
public interface RequestApi {

    @GET("todos/1")
    Observable<ResponseBody> makeObservableQuery();
}
