package com.example.rxjava;

import java.util.concurrent.Future;

import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created on 10/18/2019.
 */
public class MainViewModel extends ViewModel {

    private Repository repository;

    public MainViewModel() {
        repository = Repository.getInstance();
    }

    public Future<Observable<ResponseBody>> makeFutureQuery() {
        return repository.makeFutureQuery();
    }
}
