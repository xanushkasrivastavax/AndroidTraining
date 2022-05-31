package com.example.loginandsignup

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*
import com.auth0.android.jwt.JWT
import org.json.JSONObject


class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaytoken)
        val text = findViewById<TextView>(R.id.getResponse)


//        val bundle = intent.extras
//        if (bundle != null) {
//            text.text = "${bundle.getString("anushka")}"
//
//        }

        val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
        val token = sharedPreferences.getString("access-token", "")
        if (token.isNullOrEmpty()) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            return
        }
        val presentTime=System.currentTimeMillis()/1000
        var jwtToken: JWT = JWT(token!!)
        var expiryTime: String? = jwtToken.getClaim("exp").asString()
        var timeLeftToExpire= ((expiryTime?.toInt())?.minus((presentTime?.toInt()!!)))
        var timeToExpireInMinutes=timeLeftToExpire?.div(60) //TimeInMinutes
        if(timeToExpireInMinutes!!>5) {
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
        }
        else if (timeToExpireInMinutes!!<5)
        {
            val queue = Volley.newRequestQueue(this)
            val url = "https://api-smartflo.tatateleservices.com/v1/auth/refresh"
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
//                    text.text = "${response.substring(0, 5)}"
                    Toast.makeText(this,"Your token will be refreshed when there will be less than 5 minutes left",Toast.LENGTH_SHORT).show()
                    val jsonObject = JSONObject(response)
                    val token = jsonObject.getString("access_token")
                    Toast.makeText(this,"Refresh Token", Toast.LENGTH_SHORT).show()
                    val myEdit = sharedPreferences.edit()
                    myEdit.putString("access-token", token)
                    myEdit.apply()
                    myEdit.commit()


                },
                Response.ErrorListener {
                    text.text = "That didn't work!"
                }) {

                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer $token")
                    return headers
                }

            }
            queue.add(stringRequest)
        }






    }
}