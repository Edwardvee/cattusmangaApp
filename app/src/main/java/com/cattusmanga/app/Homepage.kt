package com.cattusmanga.app
import android.util.Log
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException


class Homepage : AppCompatActivity(){

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Manga>

    private lateinit var imageid: MutableList<String>
    private lateinit var titlemanga: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homepage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageid = mutableListOf()
        titlemanga = mutableListOf()
        var queue = Volley.newRequestQueue(this)
        val url =
            "http://192.168.56.1/quiroga/cattusmanga_plus/controllers/androidRequests/getMangas.php"
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    titlemanga.add(jsonObject.getString("title"))
                    imageid.add(
                        "http://192.168.56.1/quiroga/cattusmanga_plus/mangas/" + jsonObject.getString(
                            "ID"
                        ) + "/caratula.png"
                    )
                }
                getUserData()
            } catch (
                e: JSONException
            ) {
                e.printStackTrace()
            }
        }, { error: VolleyError? -> })
        queue.add(jsonObjectRequest)
        newRecyclerView = findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setLayoutManager(GridLayoutManager(this, 3))
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<Manga>()

    }
    private fun getUserData(){
        for (i in imageid.indices){
            val manga = Manga(imageid[i], titlemanga[i])
            newArrayList.add(manga)
        }
        newRecyclerView.adapter = MangaAdapter(newArrayList)
    }
}

