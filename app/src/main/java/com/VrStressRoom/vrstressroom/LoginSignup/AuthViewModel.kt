package com.VrStressRoom.vrstressroom.LoginSignup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    //Kullanıcınının uygulamaya giriş yapmasını ayarlamak için bu view modeli kullanırız.

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState =MutableLiveData<AuthState>()
    val authState :LiveData<AuthState> = _authState

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

    fun signup(email:String,password:String){

        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }
        _authState.value=AuthState.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task ->

                if(task.isSuccessful){
                    _authState.value=AuthState.Authenticated

                }
                else{
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }

            }
    } //Bu fonksiyon kayıt için.

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