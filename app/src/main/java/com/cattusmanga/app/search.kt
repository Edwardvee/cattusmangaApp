package com.cattusmanga.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException




class search : Fragment() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Category>
    private lateinit var imageid: MutableList<String>
    private lateinit var titleCategory: MutableList<String>
    private lateinit var g_ID: MutableList<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_search, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private fun getUserData(){
        for (i in imageid.indices){
            val categories = Category(imageid[i], titleCategory[i], g_ID[i])
            newArrayList.add(categories)
        }
        newRecyclerView.adapter = CategoriesAdapter(newArrayList)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageid = mutableListOf()
        titleCategory = mutableListOf()
        g_ID = mutableListOf()
        var queue = Volley.newRequestQueue(this.context)
        val url =
            "http://10.120.2.206/somnifero/cattusmanga_plus/controllers/androidRequests/getGenres.php"
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    titleCategory.add(jsonObject.getString("Name"))
                    Log.d("pepe", jsonObject.getString("Name"))
                    imageid.add(
                        "http://10.120.2.206/somnifero/cattusmanga_plus/mangas/" + jsonObject.getString(
                            "image"
                        ) + "/caratula.png"
                    )
                    g_ID.add(jsonObject.getInt("g_ID"))
                }
                getUserData()
            } catch (
                e: JSONException
            ) {
                e.printStackTrace()
            }
        }, { error: VolleyError? -> })
        queue.add(jsonObjectRequest)
        newRecyclerView = requireView().findViewById(R.id.recyclerviewCategory)
        newRecyclerView.layoutManager = LinearLayoutManager(this.context)
        newRecyclerView.setLayoutManager(GridLayoutManager(this.context, 3))
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<Category>()

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            search()
    }
}