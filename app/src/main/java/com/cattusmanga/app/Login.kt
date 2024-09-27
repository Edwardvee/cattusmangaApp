package com.cattusmanga.app
import android.os.Bundle
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

class Login : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)


        requestQueue = Volley.newRequestQueue(this)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {

                loginUser(username, password)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        val url = "http://192.168.228.131/cattusmanga_plus/controllers/androidRequests/login.php" // Cambia esta URL por la de tu servidor
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
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        val username = response.getString("user")
                        Toast.makeText(this, username, Toast.LENGTH_SHORT).show()

                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Log.e("serverconexion", error.toString())
                Toast.makeText(this, "Failed to connect to server", Toast.LENGTH_SHORT).show()
            })


        requestQueue.add(jsonObjectRequest)
    }
}
