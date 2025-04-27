package com.VrStressRoom.vrstressroom.LoginSignup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore


class AuthViewModel: ViewModel() {
    //Kullanıcınının uygulamaya giriş yapmasını ayarlamak için bu view modeli kullanırız.

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState =MutableLiveData<AuthState>()
    val authState :LiveData<AuthState> = _authState
    private val _userInfo = MutableLiveData<UserData>()
    val userInfo: LiveData<UserData> = _userInfo


    init {
        checkAuthState()
    }

    fun checkAuthState(){
        if(auth.currentUser == null){
            _authState.value=AuthState.Unauthenticated
        }else{
            _authState.value=AuthState.Authenticated
        }
    } //Bu fonksiyon kullanıcının giriş yapıp yapmadığını kontrol eder

    fun login(email:String,password:String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value=AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->

                if(task.isSuccessful){
                     _authState.value=AuthState.Authenticated
                }
                else{
                  _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }

            }
    } //Bu fonksiyon girişler için

    fun signup(name: String, lastname: String, username: String, email: String, password: String) {

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty() || username.isEmpty()) {
            _authState.value = AuthState.Error("Lütfen tüm alanları doldurun.")
            return
        }

        _authState.value = AuthState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: return@addOnCompleteListener

                    // Firestore referansı
                    val db = Firebase.firestore

                    val userMap = hashMapOf(
                        "name" to name,
                        "lastname" to lastname,
                        "username" to username,
                        "email" to email
                    )

                    db.collection("users").document(userId).set(userMap)
                        .addOnSuccessListener {
                            _authState.value = AuthState.Authenticated
                        }
                        .addOnFailureListener { e ->
                            _authState.value = AuthState.Error("Kullanıcı oluşturuldu ama Firestore hatası: ${e.message}")
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Bir hata oluştu.")
                }
            }
    }

    fun getAuthUserInformation() {
        val userId = auth.currentUser?.uid ?: return

        Firebase.firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userData = UserData(
                        name = document.getString("name") ?: "",
                        lastname = document.getString("lastname") ?: "",
                        username = document.getString("username") ?: "",
                        email = document.getString("email") ?: ""
                    )
                    _userInfo.value = userData
                } else {
                    _authState.value = AuthState.Error("Kullanıcı bilgileri bulunamadı.")
                }
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error("Veri alınamadı: ${exception.message}")
            }
    }

    //Bu fonksiyon kayıt için.

    fun signout(){
        auth.signOut()
        _authState.value=AuthState.Unauthenticated
    } //Bu fonskiyon kullanıcının çıkış yapmasını sağlar.

}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated :AuthState()
    object Loading :AuthState()
    data class Error(val message: String) :AuthState()
}

data class UserData(
    val name: String = "",
    val lastname: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = ""
)
