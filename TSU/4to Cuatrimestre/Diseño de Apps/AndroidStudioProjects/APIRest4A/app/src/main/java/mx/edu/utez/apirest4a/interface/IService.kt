package mx.edu.utez.apirest4a.`interface`

import mx.edu.utez.apirest4a.model.User
import retrofit2.Call
import retrofit2.http.*

interface IService {
    @GET("users/")
    fun getUsers(): Call<List<User>>
    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>
    @POST("users/")
    fun addUser(@Body user: User): Call<User>
    @PUT("users/")
    fun editUser(@Body user: User): Call<Boolean>
    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Boolean>
}