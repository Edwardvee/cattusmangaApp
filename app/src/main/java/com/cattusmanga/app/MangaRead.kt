package com.cattusmanga.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID : String = "id"


/**
 * A simple [Fragment] subclass.
 * Use the [MangaRead.newInstance] factory method to
 * create an instance of this fragment.
 */
class MangaRead : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null
    private var mangaid by Delegates.notNull<Int>()
    private lateinit var titlemanga: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ARG_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manga_read, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén el TextView y establece el texto con el id
        val idTextView: TextView = view.findViewById(R.id.idTexto)

        idTextView.text = id?.toString() ?: "ID no disponible"
        titlemanga = String()
        var queue = Volley.newRequestQueue(this.context)
        val url =
            "http://192.168.1.7/cattusmanga_plus/controllers/androidRequests/getManga.php?id=$id"
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    //titlemanga.add(jsonObject.getString("title"))

                    //imageid.add(
                    //    "http://192.168.1.7/cattusmanga_plus/mangas/" + jsonObject.getString(
                    //        "ID"
                    //    ) + "/caratula.png"
                    //)
                }

            } catch (
                e: JSONException
            ) {
                e.printStackTrace()
            }
        }, { error: VolleyError? -> })
        queue.add(jsonObjectRequest)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int) =
            MangaRead().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }
}