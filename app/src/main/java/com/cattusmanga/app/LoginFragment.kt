package com.cattusmanga.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var requestQueue: RequestQueue
    lateinit var session: Session
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        session = Session(this.requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view :View = inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etUsername = requireView().findViewById(R.id.etUsername)
        etPassword = requireView().findViewById(R.id.etPassword)
        btnLogin = requireView().findViewById(R.id.btnLogin)


        requestQueue = Volley.newRequestQueue(this.context)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this.context, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {

                loginUser(username, password)

            }
        }
    }
    private fun loginUser(username: String, password: String) {
        val url = "http://192.168.1.7/cattusmanga_plus/controllers/androidRequests/login.php" // Cambia esta URL por la de tu servidor
        val params = JSONObject()
        params.put("username", username)
        params.put("password", password)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, params,
            { response ->
                try {

                    val status = response.getString("status")
                    val message = response.getString("message")

                    if (status == "success") {
                        Toast.makeText(this.context, "Login Successful", Toast.LENGTH_SHORT).show()
                        val id = response.getInt("ID")

                        val username = response.getString("Name")
                        session.createLoginSession(username, id.toString())
                        Log.d("login", session.isLoggedIn().toString())
                        Toast.makeText(this.context, username, Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this.context, "Error parsing response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Log.e("serverconexion", error.toString())
                Toast.makeText(this.context, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            })


        requestQueue.add(jsonObjectRequest)
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment()
    }
}