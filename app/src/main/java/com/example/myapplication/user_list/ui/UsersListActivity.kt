package com.example.myapplication.user_list.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.RetrofitClient
import com.example.myapplication.user.ui.UserActivity
import com.example.myapplication.user_list.adapter.UsersAdapter
import com.example.myapplication.user_list.module.ReqResData
import com.example.myapplication.user_list.module.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersListActivity : AppCompatActivity() {

    private var userList = mutableListOf<User>()
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var leftArrow: ImageButton
    private lateinit var rightArrow: ImageButton
    private lateinit var pagesBar: TextView

    private var userPage = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        usersRecyclerView = findViewById(R.id.usersRecyclerView)
        leftArrow = findViewById(R.id.leftArrow)
        rightArrow = findViewById(R.id.rightArrow)
        pagesBar = findViewById(R.id.pagesBar)

        getUsers(userPage)
        setAdapter()
    }

    private fun getUsers(page: Int) {
        userList.clear()

        //jsonshi mxolod 1 da 2 gverdis monacemebia, amitom shemdegi gverdebi cariel datas wamoigebs

        RetrofitClient.reqResApi.getUsers(page).enqueue(object : Callback<ReqResData<List<User>>> {
            override fun onResponse(call: Call<ReqResData<List<User>>>, response: Response<ReqResData<List<User>>>) {
                if (response.isSuccessful && response.body() != null) {

                    userPage = response.body()?.page!!
                    totalPages = response.body()?.total!!
                    setPagesUI()

                    response.body()?.data?.forEach { user ->
                        Log.d("MyData", user.toString())
                        userList.add(user)
                    }

                   usersAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ReqResData<List<User>>>, t: Throwable) {
                Log.d("MyData", t.message.toString())
            }
        })

    }

    private fun setAdapter() {
        usersAdapter = UsersAdapter(userList) { id: Long? -> navigateToUser(id) }
        usersRecyclerView.layoutManager = GridLayoutManager(this, 2)
        usersRecyclerView.adapter = usersAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun setPagesUI(){

        leftArrow.setOnClickListener {
            if(userPage > 1) getUsers(userPage -1)
        }
        rightArrow.setOnClickListener {
            if(userPage < totalPages) getUsers(userPage +1)
        }

        pagesBar.text = "$userPage/$totalPages"
    }

    private fun navigateToUser(id: Long?) {
        val intent = Intent(this, UserActivity::class.java).apply {
            putExtra("USER_ID", id)
        }
        startActivity(intent)
    }

}