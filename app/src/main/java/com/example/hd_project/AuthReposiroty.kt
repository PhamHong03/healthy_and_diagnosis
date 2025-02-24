package com.example.hd_project

import javax.inject.Inject

class AuthRepository @Inject constructor() {
    fun login(username: String, password: String): Boolean {
        return username == "admin" && password == "password"
    }
//    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
//        firebaseAuth.signInWithEmailAndPassword(username, password)
//            .addOnCompleteListener { task ->
//                callback(task.isSuccessful)
//            }
//    }
}
