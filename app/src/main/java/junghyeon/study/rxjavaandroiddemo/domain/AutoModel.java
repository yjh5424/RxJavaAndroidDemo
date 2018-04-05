package junghyeon.study.rxjavaandroiddemo.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 윤정현 on 2018-04-05.
 */

public class AutoModel {
    @SerializedName("access_token")
    String token;

    @SerializedName("refresh_token")
    String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
