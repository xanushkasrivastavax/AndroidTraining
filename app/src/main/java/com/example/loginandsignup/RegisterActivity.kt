package com.example.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val redirectToRegister=findViewById<TextView>(R.id.signup_redirect)
        redirectToRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val btnLogin=findViewById<Button>(R.id.btn_signin)
        val displayMessage = findViewById<TextView>(R.id.first_header)
        btnLogin.setOnClickListener { _ ->
            val queue = Volley.newRequestQueue(this)
            val url = "https://api-smartflo.tatateleservices.com/v1/auth/login"
            val email = findViewById<EditText>(R.id.inputEmailLogin)
            val password = findViewById<EditText>(R.id.inputPasswordLogin)
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->

                    val jsonObject = JSONObject(response)
                    val token = jsonObject.getString("access_token")
                    val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("access-token", token)
                    myEdit.apply()
                    val sharedTokenValue = sharedPreferences.getString("access-token",token)
                    Toast.makeText(this,sharedTokenValue , Toast.LENGTH_LONG).show()


                },
                Response.ErrorListener {
                    displayMessage.text = "That didn't work!"
                }) {

                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params.put("email", email.getText().toString())
                    params.put("password", password.getText().toString())
                    return params;
                }}
            queue.add(stringRequest)
        }
    }





    }