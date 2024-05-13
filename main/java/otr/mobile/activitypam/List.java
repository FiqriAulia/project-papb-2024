package otr.mobile.activitypam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import otr.mobile.activitypam.client.ApiClient;
import otr.mobile.activitypam.client.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class List<T> extends AppCompatActivity implements RVInterface {

    ArrayList<input> input = new ArrayList<>();
    int[] image = {R.drawable.frame_8, R.drawable.frame_25, R.drawable.frame_5, R.drawable.frame_11,
            R.drawable.frame_24, R.drawable.frame_15, R.drawable.frame_16, R.drawable.frame_19, R.drawable.frame_20,
            R.drawable.frame_4, R.drawable.frame_18, R.drawable.frame_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        populate();

    }


    private void setupModels(){
        String[] nama = getResources().getStringArray(R.array.list_teman);
        String[] status = getResources().getStringArray(R.array.list_subs);

        for (int i=0;i<nama.length; i++){
            int id = i + 1;
            input.add(new input(id,nama[i],status[i],image[i]));
        }
    }

    private void populate(){
        ApiClient.getClient().create(ApiService.class).getList().enqueue(new Callback<inputApiResponse>() {
            @Override
            public void onResponse(Call<inputApiResponse> call, Response<inputApiResponse> response) {
                if (response.code()==200){
                    input.addAll(response.body().getData());
                    Log.d("respon",input.get(0).nama);
                }
                List_RVAdapter adapter = new List_RVAdapter(List.this,input,List.this);
                RecyclerView recyclerView =findViewById(R.id.mRecyclerView);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(List.this));
                Log.d("respon",input.get(0).nama);
            }

            @Override
            public void onFailure(Call<inputApiResponse> call, Throwable t) {
                Log.e("respon",t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        String text = input.get(position).getNama();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}