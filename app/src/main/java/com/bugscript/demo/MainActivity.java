package com.bugscript.demo;

import android.service.autofill.TextValueSanitizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "RX";
    private String greetings = "Hello RxJava.!";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private TextView see;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        see = findViewById(R.id.sample);

        myObservable = Observable.just(greetings);

        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG,"OnSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG,"OnNext "+s);
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
}
