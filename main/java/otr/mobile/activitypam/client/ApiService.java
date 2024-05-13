package otr.mobile.activitypam.client;

import otr.mobile.activitypam.List;
import otr.mobile.activitypam.input;
import otr.mobile.activitypam.inputApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("hermanosapi/api_her.php")
    Call<inputApiResponse> getList();
}
