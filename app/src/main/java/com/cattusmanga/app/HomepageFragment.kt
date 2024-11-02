package com.cattusmanga.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.util.Log

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


//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"


class HomepageFragment : Fragment() {
//
//    private var param1: String? = null
//    private var param2: String? = null
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Manga>
    private lateinit var imageid: MutableList<String>
    private lateinit var titlemanga: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_homepage, container, false)
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }


    }
    private fun getUserData(){
        for (i in imageid.indices){
            val manga = Manga(imageid[i], titlemanga[i])
            newArrayList.add(manga)
        }
        newRecyclerView.adapter = MangaAdapter(newArrayList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageid = mutableListOf()
        titlemanga = mutableListOf()
        var queue = Volley.newRequestQueue(this.context)
        val url =
            "http://10.120.0.118/quiroga/cattusmanga_plus/controllers/androidRequests/getMangas.php"
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    titlemanga.add(jsonObject.getString("title"))
                    imageid.add(
                        "http://10.120.0.118/quiroga/cattusmanga_plus/mangas/" + jsonObject.getString(
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
        newRecyclerView = requireView().findViewById(R.id.recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager(this.context)
        newRecyclerView.setLayoutManager(GridLayoutManager(this.context, 3))
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<Manga>()
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
           HomepageFragment()
    //            .apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }

    }
}