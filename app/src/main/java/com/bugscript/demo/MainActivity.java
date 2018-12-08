package com.bugscript.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "RX";

    private Observable<Student> myObservable;
    private DisposableObserver<Student> myObserver;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create operator is used to create a custom observable.
        //Here the datastream is Students but it is usually a network request or DB access returns

        myObservable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) throws Exception {
                ArrayList<Student> studentArrayList = getStudents();
                for(Student student:studentArrayList){
                    emitter.onNext(student);
                }
                emitter.onComplete();
            }
        });

        compositeDisposable.add(
                myObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver()));

    }

    private DisposableObserver getObserver(){
        myObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(Student s) {
                Log.e(TAG,"OnNext"+s.getName());
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

    private ArrayList<Student> getStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(int i=1;i<=5;i++){
            Student s = new Student();
            s.setAge(i);
            s.setEmail("email"+i);
            s.setName("name"+i);
            s.setRegDate("regDate"+i);
            students.add(s);
        }
        return students;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Composite Disposable

        compositeDisposable.clear();

        Log.e(TAG,"Disposed");
    }
}
