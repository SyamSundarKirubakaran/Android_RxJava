package com.bugscript.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "RX";
    private String greetings = "Hello RxJava.!";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private TextView see;
    //private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        see = findViewById(R.id.sample);

        myObservable = Observable.just(greetings);

        myObservable.subscribeOn(Schedulers.io());

        myObservable.observeOn(AndroidSchedulers.mainThread());

//        myObserver = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.e(TAG,"OnSubscribe");
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e(TAG,"OnNext "+s);
//                see.setText(s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG,"OnError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.e(TAG,"OnComplete");
//            }
//        };

        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.e(TAG,"OnNext");
                see.setText(s);
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

        myObservable.subscribe(myObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposable.dispose();
        myObserver.dispose();
    }
}
