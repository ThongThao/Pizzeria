package com.example.pizzeria.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pizzeria.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun signUp(
        email: String,
        password: String,
        fullName: String,
        phoneNumber: String,
        address: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = auth.currentUser
                    firebaseUser?.let {
                        val hashedPassword = PasswordHash.hashPassword(password)
                        val user = User(
                            userID = it.uid,
                            fullName = fullName,
                            phoneNumber = phoneNumber,
                            email = email,
                            password = hashedPassword,
                            address = address,
                            role = "Customer"
                        )
                        saveUserToFirestore(user, onSuccess, onError)
                    }
                } else {
                    onError(task.exception?.message ?: "Registration failed")
                }
            }
    }

    private fun saveUserToFirestore(
        user: User,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        user.userID?.let {
            firestore.collection("User")
                .document(it)
                .set(user)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Failed to save user data")
                }
        }
    }
}
