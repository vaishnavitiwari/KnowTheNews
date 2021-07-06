package com.example.android.knowthenews

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var madapter :NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)
         fetchdata()
         madapter= NewsListAdapter(this)

        recyclerView.adapter = madapter
    }
    private fun fetchdata()  {
        val url="https://saurav.tech/NewsAPI/top-headlines/category/general/in.json"
       val jsonObjectRequest = JsonObjectRequest( Request.Method.GET,
           url,
           null,
           {
               val newsjasonarray = it.getJSONArray("articles");
               val newsarray = arrayListOf<News>();
               for(i in 0 until newsjasonarray.length())
               {
                    val newJasonObject = newsjasonarray.getJSONObject(i)
                   val news = News(newJasonObject.getString("title"),
                   newJasonObject.getString("author"),
                   newJasonObject.getString("url"),
                   newJasonObject.getString("urlToImage"))
                   newsarray.add(news);
               }

             madapter.updateNews(newsarray)
           },
           {

           }



       )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)


    }

    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder();
        val customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));
        

    }
}