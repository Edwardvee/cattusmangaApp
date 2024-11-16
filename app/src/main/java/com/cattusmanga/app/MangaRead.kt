package com.cattusmanga.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONException

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

    private lateinit var titlemanga: String
    private lateinit var chapters: MutableList<Int>
    private lateinit var mangaId: MutableList<Int>
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Chapters>

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
    private fun getUserData(){
        for (i in chapters.indices) {
            val chapter = Chapters(chapters[i], mangaId[i])
            newArrayList.add(chapter)
        }
        newRecyclerView.adapter = ChaptersAdapter(newArrayList) { id, chapterNumber ->
            val fragment = MangaReader()
            fragment.arguments = Bundle().apply {
                putInt("id", id)
                putInt("chapterNumber", chapterNumber)
            }
            // Realiza la transacción de fragmento
            (activity as? MainActivity)?.replaceFragment(fragment)
        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtén el TextView y establece el texto con el id
        val idTextView: TextView = view.findViewById(R.id.idTexto)
        val idDescription: TextView = view.findViewById(R.id.idDesc)

        val categoryImageView: ImageView = view.findViewById(R.id.category_img)

        idTextView.text = id?.toString() ?: "ID no disponible"
        titlemanga = String()
        chapters= mutableListOf()
        mangaId= mutableListOf()
        //Agarrar informacion del manga
        var queue = Volley.newRequestQueue(this.context)
        val url =
            "http://10.120.2.206/somnifero/cattusmanga_plus/controllers/androidRequests/getManga.php?id=$id"
        var jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    titlemanga = jsonObject.getString("title")
                    idTextView.setText(titlemanga)
                    idDescription.setText(jsonObject.getString("description"))
                    Glide.with(requireView())
                        .load("http://10.120.2.206/somnifero/cattusmanga_plus/mangas/$id/caratula.png")
                        .into(categoryImageView)
                }
            }
            catch (
                e: JSONException
            ) {
                e.printStackTrace()
            }
        }, { error: VolleyError? -> })
        queue.add(jsonObjectRequest)

        //Agarrar capitulos
        val urlChapters =
            "http://10.120.2.206/somnifero/cattusmanga_plus/controllers/androidRequests/getChapters.php?id=$id"
        var jsonObjectRequestChapters = JsonObjectRequest(Request.Method.GET, urlChapters, null, { response ->
            try {
                val jsonArray = response.getJSONArray("data")
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    chapters.add(jsonObject.getInt("number"))
                    mangaId.add(jsonObject.getInt("Manga_ID"))
                }
                getUserData()
            }
            catch (
                e: JSONException
            ) {
                e.printStackTrace()
            }
        }, { error: VolleyError? -> })
        queue.add(jsonObjectRequestChapters)
        newRecyclerView = requireView().findViewById(R.id.reciclerViewChapters)
        newRecyclerView.layoutManager = LinearLayoutManager(this.context)
        newRecyclerView.setLayoutManager(GridLayoutManager(this.context, 1))
        newRecyclerView.setHasFixedSize(true)
        newArrayList = arrayListOf<Chapters>()

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