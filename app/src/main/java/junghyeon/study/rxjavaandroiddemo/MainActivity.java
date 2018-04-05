package junghyeon.study.rxjavaandroiddemo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    Button recyclerview;
    Button button;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("junghyeon.study.rxjavaandroiddemo", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        recyclerview=findViewById(R.id.recyclerview_rx);
        button=findViewById(R.id.button_event_rx);
        login=findViewById(R.id.rx_login);

        Observable<Intent> rxcyclerviewNext= RxView.clicks(recyclerview).map(intent -> new Intent(getApplicationContext(),RxRecyclerView.class));
        Observable<Intent> rxButtonNext= RxView.clicks(button).map(intent -> new Intent(getApplicationContext(),RxButtonEvent.class));
        Observable<Intent> rxLogin= RxView.clicks(login).map(intent -> new Intent(getApplicationContext(),RxLoginProcessing.class));

        Observable<Intent> clickEvent = Observable.merge(rxcyclerviewNext, rxButtonNext,rxLogin);
        clickEvent.subscribe(intent -> startActivity(intent));


        //stringObservable.map(text -> text="change Android").subscribe(android -> textView.setText(android));
    }

/*    private void mergeClick() {
        //클릭이 있으면 left를 반환
        Observable<String> left = RxView.clicks(leftButton).map(event -> "left");
*//*       left.map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s;
            }
        });
        *//*
        Observable<String> right = RxView.clicks(rightButton).map(event -> "right");
        Observable<String> leftWithRight = Observable.merge(left, right);

        leftWithRight.map(text -> text.toUpperCase()).subscribe((text) -> {
            Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
            textView.setText(text);}
        );
    }

    private void leftClick(){
        Observable<String> left= RxView.clicks(leftButton).map(event -> "left");
        left.map(event-> "left click").subscribe(text->textView.setText(text));

    }

    private void rightClick(){
        Observable<String> right= RxView.clicks(rightButton).map(event -> "right");
        right.map(event-> "right click").subscribe(text->textView.setText(text));
    }


    private void original(){
        Observable<String> original=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onCompleted();
            }
        });

        original.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                //string 을 받아서 처리해줌

            }
        });


        //편의를 위한 방법
        original.subscribe(new Action1<String>() {

            //call만 구현해서 데이터흐름이 제대로 처리됐을때만 처리

            @Override
            public void call(String s) {

            }
        });
    }*/
}
