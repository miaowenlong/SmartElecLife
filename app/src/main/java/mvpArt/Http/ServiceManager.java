package mvpArt.Http;


import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sgcc.smarteleclife.BuildConfig;
import com.sgcc.smarteleclife.Constants;
import com.sgcc.smarteleclife.Http.models.RequestDto;
import com.sgcc.smarteleclife.Http.models.ReturnDto;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by miao_wenlong on 2017/8/15.
 */

public class ServiceManager {
    private static ServiceManager instance = null;

    public synchronized static ServiceManager getInstance() {
        return instance != null ? instance : new ServiceManager();
    }

    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(BuildConfig.DEBUG ?
                            HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
            .build();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constants.BASE__URL)
            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private Http mHttp = retrofit.create(Http.class);



    public Observable<ReturnDto> request(int requestCode, Map map){
        RequestDto requestDto = new RequestDto();
        String oJson = new Gson().toJson(map);
        requestDto.setRequestCode(requestCode);
        requestDto.setObj(oJson);
        String data = new Gson().toJson(requestDto);
        return mHttp.http(data).subscribeOn(Schedulers.io());
    }
}

