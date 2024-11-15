package com.cattusmanga.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import kotlin.system.measureTimeMillis

class Session {
    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context

    var PRIVATEMODE: Int= 0
    constructor(con : Context){
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME, PRIVATEMODE)
        editor = pref.edit()
    }
    companion object{
        val PREF_NAME = "Login preferences"
        val IS_LOGIN = "isLoggedIn"
        val KEY_USERNAME = "username"
        val KEY_ID = "id"
    }
    fun createLoginSession(username: String, email: String) {
        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_ID, email)
        editor.commit()
    }
    fun checkLogin(){
        if(!this.isLoggedIn()){
            var i : Intent = Intent(con, LoginFragment::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(i)
        }

    }
    fun getUserDetails(): HashMap<String, String>{
        var user: Map<String, String> = HashMap<String, String>()
        (user as HashMap).put(KEY_USERNAME, pref.getString(KEY_USERNAME, null)!!)
        (user as HashMap).put(KEY_ID, pref.getString(KEY_ID, null)!!)
        return user
    }

    fun logOutUser(){
        editor.clear()
        editor.commit()
        var i : Intent = Intent(con, LoginFragment::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(i)
    }

    fun isLoggedIn(): Boolean{
        return pref.getBoolean(IS_LOGIN, false)
    }
}