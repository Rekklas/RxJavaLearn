package com.example.rxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    private TextView text;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Task>() {
                    @Override public boolean test(Task task) throws Exception {
                        Log.d(TAG, "test: " + Thread.currentThread().getName());

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return task.isComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: called.");
                disposables.add(d);
            }

            @Override public void onNext(Task task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + task.getDescription());

            }

            @Override public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);

            }

            @Override public void onComplete() {
                Log.d(TAG, "onComplete: called.");

            }
        });
    }

    @Override protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
