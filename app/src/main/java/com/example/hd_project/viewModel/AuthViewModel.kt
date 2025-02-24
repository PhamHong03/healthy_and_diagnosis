package com.example.hd_project.viewModel

import android.content.Intent
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hd_project.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val googleSignInClient: GoogleSignInClient,
    private val repository: AuthRepository
) : ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>(AuthState.Idle)

    val authState : LiveData<AuthState> = _authState

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
//        resetAuthState()
        Firebase.auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                _username.value = user.displayName ?: "Bác sĩ"
                loadUserDataFromFirestore(user.uid)
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }
    private fun loadUserDataFromFirestore(userId: String) {
        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _username.value = document.getString("username") ?: "Bác sĩ"
                    _phoneNumber.value = document.getString("phoneNumber") ?: "Không có số điện thoại"
                }
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.message ?: "Lỗi khi lấy dữ liệu từ Firestore")
            }
    }

    fun checkAuthStatus() {
         if(auth.currentUser == null) {
             _authState.value = AuthState.Unauthenticated
         }else{
             _authState.value = AuthState.Authenticated
         }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email hoặc mật khẩu không được trống!")
            return
        }
        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState.Authenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Có một vài lỗi xảy ra")
            }
        }
    }

    fun signup(email: String, password: String, username: String, phoneNumber: String) {
        if (email.isEmpty() || password.isEmpty() || username.isEmpty() || phoneNumber.isEmpty()) {
            _authState.value = AuthState.Error("Email, tên, số điện thoại hoặc mật khẩu không được trống!")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            saveUserToFirestore(user.uid, username, email, phoneNumber)
                        }
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Có một vài lỗi xảy ra")
                }
            }
    }

    private fun saveUserToFirestore(userId: String, username: String, email: String, phoneNumber: String) {
        val userMap = hashMapOf(
            "username" to username,
            "email" to email,
            "phoneNumber" to phoneNumber
        )

        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .set(userMap)
            .addOnSuccessListener {
                _authState.value = AuthState.Authenticated
            }
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.message ?: "Lỗi khi lưu dữ liệu vào Firestore")
            }
    }
    fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val fetchedUsername = document.getString("username")
                    val fetchedPhoneNumber = document.getString("phoneNumber")

                    Log.d("AuthViewModel", "Fetched username: $fetchedUsername")
                    Log.d("AuthViewModel", "Fetched phone: $fetchedPhoneNumber")

                    _username.value = fetchedUsername ?: "Bác sĩ"
                    _phoneNumber.value = fetchedPhoneNumber ?: "Không có số điện thoại"
                }
            }
            .addOnFailureListener { e ->
                Log.e("AuthViewModel", "Error fetching user data", e)
            }
    }

    fun signOut() {
        viewModelScope.launch {
            try {
                googleSignInClient.signOut().await()
                auth.signOut()
                _authState.value = AuthState.Unauthenticated
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Lỗi khi đăng xuất")
            }
        }
    }



    fun resetAuthState() {
        signOut()
        _authState.value = AuthState.Idle
    }
    //sign in with google
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Idle)
    val signInState: StateFlow<SignInState> = _signInState

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                Firebase.auth.signInWithCredential(credential).await()
                _authState.value = AuthState.Authenticated
                _signInState.value = SignInState.Success
            } catch (e: Exception) {
                _signInState.value = SignInState.Error(e.message ?: "Unknown error")
            }
        }
    }


    fun launchGoogleSignIn(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    sealed class SignInState {
        object Idle : SignInState()
        object Success : SignInState()
        data class Error(val message: String) : SignInState()
    }
}

sealed class AuthState{
    object Idle : AuthState()
    object Authenticated: AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}