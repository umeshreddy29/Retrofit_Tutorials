package gaur.himanshu.august.retrofittutorials.retrofit

import gaur.himanshu.august.retrofittutorials.`object`.JsonAPiResponse
import retrofit2.Response
import retrofit2.http.GET

interface JsonApi {

    @GET("posts")
    suspend fun getPosts(): Response<List<JsonAPiResponse>>

}