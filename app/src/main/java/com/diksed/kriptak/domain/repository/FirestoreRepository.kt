package com.diksed.kriptak.domain.repository

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getDailyNewsApiParams(): ApiParams {
        return suspendCoroutine { continuation ->
            firestore.collection("NewsApi").document("apiDailyKey")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val params = document.toObject(ApiParams::class.java)
                        continuation.resume(params ?: ApiParams())
                    } else {
                        continuation.resume(ApiParams())
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }
}

data class ApiParams(
    val apiKey: String = "",
    val language: String = "en",
    val newsSize: Int = 10,
    val searchQuery: String = "",
    val unwantedSources: List<String> = emptyList()
)
