package gaur.himanshu.august.retrofittutorials

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gaur.himanshu.august.retrofittutorials.`object`.JsonAPiResponse
import gaur.himanshu.august.retrofittutorials.recycler.RecyclerAdapter
import gaur.himanshu.august.retrofittutorials.retrofit.JsonApi
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val data = MutableLiveData<List<JsonAPiResponse>>()

    val delete= MutableLiveData<Int>()
    //  val data = MutableLiveData<JsonAPiResponce>()  for jsonApi.getPostsParticularId(4)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonApi = retrofit.create(JsonApi::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)

        // getData(jsonApi) Part-1

        // postRequest(jsonApi) Part-2

        // putPostRequest(jsonApi) Part-3

        // patchtPostRequest(jsonApi) Part-4

        deletePosts(jsonApi,4)

        delete.observe(this, {
            if (it != null) {
                Toast.makeText(this@MainActivity,it.toString(),Toast.LENGTH_SHORT).show()
                Log.d("TAG", "onCreate: $it")
            }

        })

        data.observe(this, {
            val adapter = RecyclerAdapter(it)
            recyclerView.adapter = adapter
        })


    }


    fun getData(jsonApi: JsonApi) {
        CoroutineScope(Dispatchers.IO).launch {
            val responce = jsonApi.postDataToServer(JsonAPiResponse("This is Body", 102, "This is Title", 3))
            if (responce.isSuccessful) {
                responce.body()?.let {
                    data.postValue(listOf(it))
                }

            }
        }
    }

    // POST
    fun postRequest(jsonApi: JsonApi) {
        CoroutineScope(Dispatchers.IO).launch {
            val responce = jsonApi.postDataToServer(JsonAPiResponse("This is Body", 100, "This is Title", 3))

            if (responce.isSuccessful) {
                responce.body()?.let {
                    data.postValue(listOf(it))
                }

            } else {

            }
        }
    }

    // PUT
    fun putPostRequest(jsonApi: JsonApi) {
        CoroutineScope(Dispatchers.IO).launch {
            val responce = jsonApi.putPostRequest(4, JsonAPiResponse("Body", 4, null, 6))

            if (responce.isSuccessful) {
                responce.body()?.let {
                    data.postValue(listOf(it))
                }

            } else {

            }
        }

    }

    // PATCH
    fun patchtPostRequest(jsonApi: JsonApi) {
        CoroutineScope(Dispatchers.IO).launch {
            val responce = jsonApi.patchPostRequest(4, JsonAPiResponse("Body", 4, " ", 6))

            if (responce.isSuccessful) {
                responce.body()?.let {
                    data.postValue(listOf(it))
                }

            } else {

            }
        }

    }

    //DELETE
    fun deletePosts(jsonApi: JsonApi,id:Int){
        CoroutineScope(Dispatchers.IO).launch {
            val response=jsonApi.deletePost(id)
            if(response.isSuccessful){
                Log.d("TAG", "patchtPostRequest: ${response.code()}.")
                delete.postValue(response.code())
            }else{

            }
        }
    }


}