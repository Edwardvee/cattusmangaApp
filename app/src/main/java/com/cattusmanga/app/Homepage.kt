package com.cattusmanga.app
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Homepage : Fragment(){
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Manga>

    private lateinit var imageid: MutableList<String>
    private lateinit var titlemanga: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageid = mutableListOf()
        titlemanga = mutableListOf()
        var queue = Volley.newRequestQueue(this.context)
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
        newRecyclerView = view?.findViewById(R.id.recyclerview)!!
        newRecyclerView.layoutManager = LinearLayoutManager(this.context)
        newRecyclerView.setLayoutManager(GridLayoutManager(this.context, 3))
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.homepage, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Homepage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

