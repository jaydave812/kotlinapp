package com.example.assignment.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.Adapter.CardViewAdapter
import com.example.assignment.R
import com.example.assignment.db.UserDatabase
import com.example.assignment.model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), EditUser {

    private val modelClasses = mutableListOf<User>()
    private lateinit var viewModel: MainActivityViewModel

    private val cardViewAdapter by lazy {
        CardViewAdapter(this, modelClasses)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val application = requireNotNull(this).application
        val dataSource = UserDatabase.getInstance(application).userDatabaseDao
        val viewModelFactory = MainActivityViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(MainActivityViewModel::class.java)

        init()
        getUser()
    }

    private fun getUser() {
        viewModel.getUserListFromApi()
        setupUserObserver()
    }

    private fun setupUserObserver() {
        viewModel.userData.observe(this, Observer {
            modelClasses.clear()
            modelClasses.addAll(it)
            cardViewAdapter.notifyDataSetChanged()
        })
    }

    private fun init() {
        val manager = LinearLayoutManager(this)
        recyclerview.layoutManager = manager
        recyclerview.adapter = cardViewAdapter
    }

    override fun deleteUser(user: User) {
        viewModel.deleteUser(user)
    }

    override fun updateUser(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

interface EditUser {
    fun deleteUser(user: User)
    fun updateUser(user: User)
}