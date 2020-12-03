package gaur.himanshu.august.retrofittutorials

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import gaur.himanshu.august.retrofittutorials.`object`.CommentResponce
import gaur.himanshu.august.retrofittutorials.recycler.RecyclerAdapter
import gaur.himanshu.august.retrofittutorials.retrofit.JsonApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val data = MutableLiveData<List<CommentResponce>>()

    //  val data = MutableLiveData<JsonAPiResponce>()  for jsonApi.getPostsParticularId(4)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonApi = retrofit.create(JsonApi::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler)


        getData(jsonApi)

        data.observe(this, {
            val adapter = RecyclerAdapter(it)
            recyclerView.adapter = adapter
        })


    }


    fun getData(jsonApi: JsonApi) {
        CoroutineScope(Dispatchers.IO).launch {

            // val responce = jsonApi.getPostsParticularId(5)

            val responce = jsonApi.getCommentPostId(6)
            if (responce.isSuccessful) {
                data.postValue(responce.body())
            }
        }
    }


}