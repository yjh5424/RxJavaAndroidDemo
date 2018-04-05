package junghyeon.study.rxjavaandroiddemo.network;

import junghyeon.study.rxjavaandroiddemo.domain.AutoModel;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Single;

/**
 * Created by 윤정현 on 2018-04-03.
 */

public interface Service {


    //결과만 알고 싶을때 Completable 또한 Single도 동일하지만 single 핵심은 한번만 흐름이 있을때
    @POST("auth")
    @FormUrlEncoded
    Single<AutoModel> login(@Field("id") String id, @Field("pw") String password);
}
