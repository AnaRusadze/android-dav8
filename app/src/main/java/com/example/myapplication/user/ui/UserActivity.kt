package com.example.myapplication.user.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.service.RetrofitClient
import com.example.myapplication.user_list.module.ReqResData
import com.example.myapplication.user_list.module.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivity : AppCompatActivity() {

    private lateinit var username: TextView
    private lateinit var email: TextView
    private lateinit var avatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        username = findViewById(R.id.usernameTextView)
        email = findViewById(R.id.emailTextView)
        avatar = findViewById(R.id.userAvatar)

        getUser()
    }

    private fun getUser(){
        val userId = intent.getLongExtra("USER_ID", -1)
        RetrofitClient.reqResApi.getUser(userId).enqueue(object : Callback<ReqResData<User>>{
            override fun onResponse(call: Call<ReqResData<User>>, response: Response<ReqResData<User>>) {
                val user = response.body()?.data
                setUI(user)
            }
            override fun onFailure(call: Call<ReqResData<User>>, t: Throwable) {
                Log.d("MyData", t.message.toString())
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUI(user: User?){
        username.text = "${user?.firstName} ${user?.lastName}"
        email.text = user?.email
        Glide.with(this).load(user?.avatar).placeholder(R.drawable.ic_user_placeholder).into(avatar)
    }

}