package com.example.graduationproject.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.graduationproject.R;
import com.example.graduationproject.api.INewsApi;
import com.example.graduationproject.api.News;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewClass extends AppCompatActivity {
    private static final String TAG = "RecyclerView";
    RecyclerView recyclerViewNews;
//    List<Article> list;
//    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerViewNews=findViewById(R.id.rv_news);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INewsApi iNewsApi=retrofit.create(INewsApi.class);
        iNewsApi.getNews(
                "eg",
                "technology")
                .enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()&& response.body()!=null){
                    News news= response.body();
                    Log.i(TAG, "onResponse: "+ news.toString());

                  NewsAdapter newsAdapter=new NewsAdapter(response.body().getArticles(),getApplicationContext());
                  recyclerViewNews.setAdapter(newsAdapter);

//                  recyclerViewNews.setOnClickListener(new View.OnClickListener() {
//                      @Override
//                      public void onClick(View view) {
//                          Article article = list.get(0);
//                          Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            context.startActivity(intent);
//                      }
//                  });
                }

            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                String errorMessage = t.getLocalizedMessage();
                Toast.makeText(RecyclerViewClass.this, errorMessage , Toast.LENGTH_SHORT).show();
            }
        });

    }
}