package choongyul.android.com.rxandroid_study;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 실제 Task 처리하는 객체 (발행자)
        Observable<String> simpleObservable =
                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        // 네트웤에서 가져온 데이터
                        // 반복문을 돌면서
                        // for(네트웤에서 가져온 데이터) { ----------
                        subscriber.onNext("Hello RxAndroid!!");
                        subscriber.onNext("Hello RxAndroid!! 1");
                        subscriber.onNext("Hello RxAndroid!! 2");
                        subscriber.onNext("Hello RxAndroid!! 3");
                        // } ----------------------------------------
                        subscriber.onCompleted();
                    }
                });

        simpleObservable
                .subscribe(new Subscriber<String>() { // observer(구독자) 에 해당함
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "[observer1] complete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "[observer1] error : " + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(MainActivity.this, "[observer1]" + s, Toast.LENGTH_SHORT).show();
                    }
                });


    }

}





    /*  아래와 같이 구현할 수도 있다.
        // 옵저버 (구독자) 를 등록해주는 함수 - 기본형


        // 옵저버를 등록하는 함수 - 진화형 (각 함수를 하나의 콜백객체에 나눠서 담아준다)
        simpleObservable.subscribe(
                new Action1<String>() { // onNext함수와 동일한 역할을 하는 콜백 객체
                    @Override
                    public void call(String s) {
                        Toast.makeText(MainActivity.this, "[observer2]" + s, Toast.LENGTH_SHORT).show();

                    }
                }, new Action1<Throwable>() { // onError 함수와 동일한 역할을 하는 콜백 객체
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "[observer2] error : " + throwable.getMessage());

                    }
                }, new Action0() { // onComplete 함수와 동일한 역할을 하는 콜백 객체
                    @Override
                    public void call() {
                        Log.d(TAG, "[observer2] complete!");


                    }
                }
        );

        // 옵저버를 등록하는 함수 - 최종진화형 람다형
        simpleObservable.subscribe( // 순서가 정해져 있기 때문에 처음 s는 onNext의 string으로,
                                    // 두번째 인자는 onError
                                    // 세번째 인자는 onComplete로 인식한다. 까보면됌
                s -> {

                }, throwable -> {

                }, () -> {

                }
        );

    */

