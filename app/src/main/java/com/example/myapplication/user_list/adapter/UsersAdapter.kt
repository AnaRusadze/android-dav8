package com.example.myapplication.user_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.user_list.module.User

class UsersAdapter(private val list: List<User>, private val userCallBack: (userId: Long?) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)
        private val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        private val userAvatar: ImageView = itemView.findViewById(R.id.userAvatar)

        @SuppressLint("SetTextI18n")
        fun bindUser(user: User, userCallBack: (userId: Long?) -> Unit) {
            usernameTextView.text = "${user.firstName} ${user.lastName}"
            emailTextView.text = user.email
            Glide.with(itemView.context).load(user.avatar).placeholder(R.drawable.ic_user_placeholder).into(userAvatar)
            itemView.setOnClickListener { userCallBack.invoke(user.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.users_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindUser(list[position], userCallBack)
    }

    override fun getItemCount() = list.size

}