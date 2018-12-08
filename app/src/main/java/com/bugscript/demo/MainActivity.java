package com.bugscript.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "RX";
    private String[] greetings = {"AAA","BBB","CCC","DDD"};
    private Integer[] nums = {1,2,3,4,5};
    private Observable<Integer> myObservable;
    private DisposableObserver<Integer> myObserver;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fromArray operator considers each item in the array as Observable.
        //unlike just fromArray creates an Iterable
        //try debugging
//        myObservable = Observable.fromArray(greetings);
        myObservable = Observable.fromArray(nums);

        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver()));

    }

    private DisposableObserver getObserver(){
        myObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer i) {
                Log.e(TAG,"OnNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"OnError");
            }

            @Override
            public void onComplete() {
                Log.e(TAG,"OnComplete");
            }
        };
        return myObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Composite Disposable

        compositeDisposable.clear();

        Log.e(TAG,"Disposed");
    }
}
