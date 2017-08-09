package mvpArt.Http;

import com.sgcc.smarteleclife.Http.models.ReturnDto;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by miao_wenlong on 2017/8/15.
 */
interface Http {
    @POST
    Observable<ReturnDto> http(@Query("data") String data);
}
