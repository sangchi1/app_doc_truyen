package com.example.ass_app.FRAGMENT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ass_app.ADAPTER.TruyenAdapter;
import com.example.ass_app.ChiTietActivity;
import com.example.ass_app.DTO.TruyenDTO;
import com.example.ass_app.INTERFACE.TruyenInterface;
import com.example.ass_app.MainActivity;
import com.example.ass_app.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView rcvComic;
    private TruyenAdapter truyenAdapter ;
    List<TruyenDTO> list_truyen;
    String id_user ;
    static final  String BASE_URL="http://172.20.10.4:3000/truyen/";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View  view=inflater.inflate(R.layout.fragment_home, container, false);
      searchView = view.findViewById(R.id.seachView);
      searchView.clearFocus();
        rcvComic=view.findViewById(R.id.rcv_ComicList);
        //
        list_truyen= new ArrayList<TruyenDTO>();
        searchComic();
        getTruyen();
        if(getArguments()!=null){

            id_user = getArguments().getString("id");
            Log.d("id truyen tu main", "onCreateView: "+id_user);
        }
        return view;
    }
    private void searchComic(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterliss(newText);
                return true;
            }
            private void filterliss(String Text) {
                List<TruyenDTO> fiteliss = new ArrayList<>();
                for (TruyenDTO comic : list_truyen) {
                    if (comic.getName().toLowerCase().contains(Text.toLowerCase())) {
                        fiteliss.add(comic);
                    }
                }
                if (fiteliss.isEmpty()) {

                } else {
                    truyenAdapter.setfilterliss(fiteliss);
                }
            }
        });
    }
    private void getTruyen(){
        Gson gson= new GsonBuilder().setLenient().create();

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        TruyenInterface truyenInterface = retrofit.create(TruyenInterface.class);
        Call<List<TruyenDTO>> objCall = truyenInterface.list();
        objCall.enqueue(new Callback<List<TruyenDTO>>() {
            @Override
            public void onResponse(Call<List<TruyenDTO>> call, Response<List<TruyenDTO>> response) {
                if(response.isSuccessful()){
                    list_truyen.clear();
                    list_truyen.addAll(response.body());
                    Log.d("zzzzzzzz", "onResponse: "+list_truyen);
                    Log.d("zzzzzzzzzzzz", "onResponse: "+ response.body());
                    truyenAdapter = new TruyenAdapter(getActivity(), list_truyen, new TruyenAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(TruyenDTO comic) {
                            String selectedItemId = comic.get_id();
                            Intent intent = new Intent(getActivity(), ChiTietActivity.class);
                            intent.putExtra("TRUYEN",comic);
                            intent.putExtra("id_user",id_user);
                            Log.d("zzz"," " + comic);
                            startActivity(intent);
                        }
                    });
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                    rcvComic.setLayoutManager(gridLayoutManager);
                    rcvComic.setAdapter(truyenAdapter);
                } else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu" +response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TruyenDTO>> call, Throwable t) {
                Log.e("RetrofitError", "onFailure: ", t);
                Toast.makeText(getActivity(), "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
