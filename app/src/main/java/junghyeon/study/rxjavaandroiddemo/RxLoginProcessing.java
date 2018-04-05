package junghyeon.study.rxjavaandroiddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import junghyeon.study.rxjavaandroiddemo.network.Connector;
import junghyeon.study.rxjavaandroiddemo.network.Service;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RxLoginProcessing extends AppCompatActivity {

    private EditText id;
    private EditText password;
    private Button loginButton;
    private TextView passwordCheck;
    private boolean loginState=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_login_processing);

        init();

        Observable<String> observable=Observable.create(subscriber -> {
            subscriber.onNext("ddd");
            subscriber.onCompleted();
        });

        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }


    private void init(){
        setView();

        Observable<String> observable=RxTextView.afterTextChangeEvents(password).map(text-> password.getText().toString());
        observable.subscribe(text -> {
            if (!checkPassword(text)) {
                passwordCheck.setVisibility(View.VISIBLE);
                //loginState=false;
            }else{
                passwordCheck.setVisibility(View.GONE);
                //loginState=true;
            }
        });

        Observable<Intent> loginNextObservable= RxView.clicks(loginButton).map(intent->new Intent(getApplicationContext(),MainActivity.class));
        loginNextObservable.subscribe(intent-> {
            login(id.getText().toString(),password.getText().toString());
            startActivity(intent);
            finish();
          /*  if(loginState){
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(this,"비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
            }*/
        });
    }

    private void setView(){
        id=findViewById(R.id.id);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.password_check);
        loginButton = findViewById(R.id.loginButton);

    }

    private void login(String id, String password) {
        Service service = Connector.HttpService();
        service.login(id, password).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(auth -> {
                    Log.d("token",auth.getToken());
                    Log.d("refreshToken",auth.getRefreshToken());
                },throwable -> {
                    //오류
                });

              /*  subscribe(new Action1<AutoModel>() {
                    @Override
                    public void call(AutoModel user) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });*/
    }

    private boolean checkPassword(String text){
        if(password.getText().length()==0){
            //맨처음
            return true;
        }
        for (int i = 0; i < text.length(); i++) {
            //문자거나 숫자이면 true
            if (!Character.isLetterOrDigit(text.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
