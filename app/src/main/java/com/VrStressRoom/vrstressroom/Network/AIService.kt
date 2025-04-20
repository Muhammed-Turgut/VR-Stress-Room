import retrofit2.http.Body
import retrofit2.http.POST
//Bu internete mesaj attığımımz suspend fonksiyon interfacei
interface AIService {
    @POST("/chat")
    suspend fun sendMessage(@Body request: BotRequest): BotResponse
}

