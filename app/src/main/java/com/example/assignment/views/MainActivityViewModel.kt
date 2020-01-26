package com.example.assignment.views

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.assignment.Services.Retrofit
import com.example.assignment.db.UserDatabaseDao
import com.example.assignment.model.ModelClass
import com.example.assignment.model.User
import org.jetbrains.anko.doAsync
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityViewModel(
    private val database: UserDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val call: Call<ModelClass> = Retrofit.getApi_services().usersList

    val userData get() = database.getAllUser()

    fun getUserListFromApi() {
        call.enqueue(object : Callback<ModelClass?> {
            override fun onResponse(
                call: Call<ModelClass?>,
                response: Response<ModelClass?>
            ) {
                if (response.code() == 200) {
                    doAsync {
                        database.insertList(response.body()?.data ?: emptyList())
                    }
                }
            }

            override fun onFailure(
                call: Call<ModelClass?>,
                t: Throwable
            ) {
                Log.e("MainActivityViewModel", "error occurred")
            }
        })
    }

    fun deleteUser(user: User) {
        doAsync {
            database.deleteUser(user)
        }
    }

    fun updateUser(user: User) {
        doAsync {
            database.update(user)
        }
    }
}