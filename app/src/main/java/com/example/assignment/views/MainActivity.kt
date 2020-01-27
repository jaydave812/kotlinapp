package com.example.assignment.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.adapter.CardViewAdapter
import com.example.assignment.R
import com.example.assignment.db.UserDatabase
import com.example.assignment.model.User
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), EditUser {

    private val modelClasses = mutableListOf<User>()
    var firstName:String=""
    var lastName:String=""
    var email:String=""
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
//        here api call done
        getUser()
    }
//this fun is used for the api call if no data is present in recyclerview
    private fun getUser() {
        viewModel.getUserListFromApi()
        setupUserObserver()
    }
//this fun is check for the array list value
    private fun setupUserObserver() {
        viewModel.userData.observe(this, Observer {
            modelClasses.clear()
            modelClasses.addAll(it)
            cardViewAdapter.notifyDataSetChanged()
        })
    }
//inside init setting up adapter in recyclerview
    private fun init() {
        val manager = LinearLayoutManager(this)
        recyclerview.layoutManager = manager
        recyclerview.adapter = cardViewAdapter
        user_add.setOnClickListener(View.OnClickListener { showAddNoteDialog() })
    }
//this method is user for the delete the user from db
    override fun deleteUser(user: User) {
        viewModel.deleteUser(user)
    }
//this method is use for edit user information
    override fun updateUser(user: User) {
        viewModel.updateUser(user)
    }
//    this fun is use for the generating random number
    private fun IntRange.rendom()= Random().nextInt((endInclusive+1)-start)+start
//    this fun is use for add new user in data base
    private fun showAddNoteDialog() {
        // create an alert builder
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        // builder.setTitle("Name");

        // set the custom layout
        val customLayout = LayoutInflater.from(this).inflate(R.layout.add_user_dialog, null)
        builder.setView(customLayout)
        val title=customLayout.findViewById(R.id.title) as TextView
        title.setText("Add Users")
        val send_btn = customLayout.findViewById(R.id.send_btn) as TextView
        send_btn.setText("Add")
        val cancel_btn = customLayout.findViewById(R.id.cancel_btn) as TextView

        val note = customLayout.findViewById(R.id.add_note_et) as EditText
        val lastname = customLayout.findViewById(R.id.lastName) as EditText
        val emailAddress = customLayout.findViewById(R.id.emailAddress) as EditText
        note.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                firstName= charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        lastname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                lastName= charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        emailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                email= charSequence.toString()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
//    here adding data to user class
        val user=User((7 until 30).rendom(),lastName,null,firstName,email)
        note.isFocusable = true
        note.isFocusableInTouchMode = true
        note.isCursorVisible = true
        note.isClickable = true
        // create and show the alert dialog
        val dialog = builder.create()

        cancel_btn.setOnClickListener { dialog.dismiss() }

        send_btn.setOnClickListener {
//            here insert user to data base
            viewModel.insertUser(user)
            cardViewAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }


        dialog.show()
    }
}
//this is interface which is used in adapter to delete and update user info
interface EditUser {
    fun deleteUser(user: User)
    fun updateUser(user: User)
}