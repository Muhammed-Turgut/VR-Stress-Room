import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//Bu obje BASE_URL kısmına yazdığımız url ile interneteki bu url kısmına istek attığımız kısım.
object BotRetrofitInstance {
    private const val BASE_URL = "https://koesan-piskochatboot.hf.space"

    val api: AIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AIService::class.java)
    }
}

