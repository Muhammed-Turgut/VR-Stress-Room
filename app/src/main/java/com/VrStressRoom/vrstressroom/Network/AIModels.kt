
//Bu model kullanıcının chat bota mesaj gönderdiği kısım.
data class BotRequest(
    val previousMessages: List<Map<String, String>>, // Önceki mesajlarlrın saklandığı liste
    val message: String // Yeni kullanıcı mesajı
)

//Bu alacağımız cevabın model sınıfı.
data class BotResponse(val response:String?) //Bu kısım Chat Botan mesajları aldığımız yer