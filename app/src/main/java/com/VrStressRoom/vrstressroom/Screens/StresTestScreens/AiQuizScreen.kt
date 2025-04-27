package com.VrStressRoom.vrstressroom.Screens.StresTestScreens



import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.VideoCalling.Psychologists
import com.VrStressRoom.vrstressroom.VideoCalling.psychologistsList
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import kotlin.collections.listOf
import com.VrStressRoom.vrstressroom.R
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork.StressRequest
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork.StressResponse
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork.StressViewModel


@Composable
fun AiQuizScreen(navController: NavController,
                 context: Context,
                 cameraViewModel: CameraViewModel,
                 stressApi:StressViewModel= viewModel()) {

    val sharedPreferences = context.getSharedPreferences("AI_PREFS", Context.MODE_PRIVATE)
    val aiResponse = sharedPreferences.getString("last_ai_response", null)

    println("${aiResponse}")

    val age = stressApi.extractFirstNumber(aiResponse.toString())
    val gender = stressApi.parseEmotion(aiResponse.toString())
    val emotion = stressApi.parseGender(aiResponse.toString())
    var showAnimation by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000) // 3 saniye bekle
        showAnimation = false
    }

        if (showAnimation) {
            AIQuizStartScreen()
        } else {

            AiQuizPage(
                ageString = age.toString(),
                gender=gender.toString(),
                emotion = emotion.toString(),stressApi=stressApi)
        }

}


//Animation Screen
@Composable
fun AIQuizStartScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB3005E),
                    Color(0xFF0A007A),
                    Color(0xFFB3005E)
                )
            )
        )){
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = "Sana bazı sorularımız\n" +
                    "olacak ",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp,
                textAlign = TextAlign.Center,
                color = Color.White)

            RelaxAnimationTwo()

            Text(text = "Başlıyor...",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White,
                lineHeight = 10.sp)


        }
    }
}

@Composable
fun RelaxAnimationTwo() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("relaxanimationtwo.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AiQuizPage(ageString: String,gender:String,emotion:String,stressApi: StressViewModel) {
    val list = remember { mutableStateOf(kidsTest) }
    val listUser = remember { mutableStateListOf<User>() } // Cevaplananlar burada
    val currentQuestionIndex = remember { mutableStateOf(0) } // Şu an hangi sorudayız

    // Yaşa göre liste güncellemesi
    val age = ageString.toIntOrNull() ?: 0
    LaunchedEffect(age) {
        when {
            age in 12..18 -> list.value = adolescentTest
            age > 18 -> list.value = adultTest
            else -> list.value = kidsTest
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB3005E),
                        Color(0xFF0A007A),
                        Color(0xFFB3005E)
                    )
                )
            )
    ) {
        if (currentQuestionIndex.value < list.value.size) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 150.dp)
            ) {
                AnimatedContent(
                    targetState = currentQuestionIndex.value,
                    transitionSpec = {
                        slideInHorizontally { width -> width } + fadeIn() with
                                slideOutHorizontally { width -> -width } + fadeOut()
                    }
                ) { targetIndex ->
                    Text(
                        text = list.value[targetIndex].name,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomButton("Evet") {
                        listUser.add(User("1"))
                        currentQuestionIndex.value += 1
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    CustomButton("Hayır") {
                        listUser.add(User("0"))
                        currentQuestionIndex.value += 1
                    }
                }
            }

        } else {
            val answers = listUser.map { user ->
                user.name.toIntOrNull() ?: 0
            }

            val request = StressRequest(
                age = age,
                gender = gender,
                emotion = emotion,
                stressAnswers = answers
            )

            if (!stressApi.isRequestCompleted) {
                stressApi.evaluateStress(request)
            }

            if (stressApi.isRequestCompleted) {
                AiQuizFinishScreen(stressApi)
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AiQuizFinishScreen(stressApi: StressViewModel) {

    val cabinBold = FontFamily(
        Font(R.font.cabinbold, FontWeight.Bold)
    )
    val cabinRegular = FontFamily(
        Font(R.font.cabinregular, FontWeight.Normal)
    )

    val stressResult by stressApi.stressResult.observeAsState()

    // Eğer veri yoksa veya hata varsa gösterme
    val response = stressResult?.getOrNull()

    if (stressResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFB3005E),
                                Color(0xFF0A007A),
                                Color(0xFFB3005E)
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    // --- Geri Dön Butonu ---
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 6.dp, top = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { /* Geri dön işlevi buraya */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(0.dp),
                            elevation = ButtonDefaults.buttonElevation(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Geri Dön",
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f) // Responsive: yazılar alanı %50 kaplar
                            ) {
                                Text(
                                    text = response!!.stress_level,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = cabinBold,
                                    color = Color.White,
                                    lineHeight = 36.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = response!!.advice,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Normal,
                                    fontFamily = cabinRegular,
                                    color = Color(0xFFFF5F9E),
                                    lineHeight = 24.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            AiStressLevelIndicator(
                                stressLevel = response!!.stress_score,
                                modifier = Modifier.size(120.dp) // Responsive: gösterge boyutu
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- Psikolog Tavsiyeleri Başlığı ---
                    Text(
                        text = "Psikolog görüşmesi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // --- Psikolog Tavsiyeleri Listesi ---
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(psychologistsList) { item ->
                            RecommendedActivePsychologistsRow(item)
                        }
                    }
                    //Burdayım.

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "VR Odalarda Stres Azalt",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .align(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 16.dp, top = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(R.drawable.meditasyonodasi),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(width = 115.dp, height = 182.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.forestroom),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .size(width = 115.dp, height = 182.dp)

                        )

                    }

                    Text(
                        text = "Nefes Egzersizleri",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 24.dp, top = 16.dp)
                            .align(Alignment.Start)
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp, top = 12.dp, end = 16.dp, bottom = 48.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Column {
                            Text(
                                text = "Nefes egzersizi \n" +
                                        "yap ve rahatla",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                            )

                            Button(
                                onClick = {
                                    // Tıklama işlemi
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp), // Daha yuvarlak köşeler
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 6.dp, // Hafif gölge efekti
                                    pressedElevation = 8.dp,
                                    focusedElevation = 4.dp
                                ),
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .height(48.dp) // Sabit yükseklik ama genişlik içerikle şekillenecek
                                    .wrapContentWidth() // Genişlik yazıya göre ayarlanır
                                    .defaultMinSize(minWidth = 120.dp), // Minimum genişlik garantisi
                                contentPadding = PaddingValues(
                                    horizontal = 16.dp,
                                    vertical = 8.dp
                                ) // İç boşluklar
                            ) {
                                Text(
                                    text = "Başla",
                                    color = Color.Black,
                                    fontSize = 16.sp, // Yazı daha okunabilir
                                    fontWeight = FontWeight.Bold
                                )
                            }


                        }


                        Image(
                            painter = painterResource(R.drawable.nefesegzersizi),
                            contentDescription = null,
                            modifier = Modifier.size(133.dp)
                        )
                    }


                }
            }
        }
}

@Composable
fun AiStressLevelIndicator(
    modifier: Modifier = Modifier,
    stressLevel: Int, // 0 ile 100 arasında
    maxLevel: Int = 100
) {
    val sweepAngle = (stressLevel / maxLevel.toFloat()) * 360f
    val strokeWidth = 20f

    Box(
        modifier = modifier
            .size(140.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Arka plan çemberi (gri)
            drawArc(
                color = Color.White,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Öndeki çember (stres seviyesi kadar)
            drawArc(
                color = if (stressLevel < 40) Color.Green else if (stressLevel < 70) Color.Yellow else Color(0xFFFF5F9E),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Ortadaki metin
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(
                text = "$stressLevel%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
    }
}

@Composable
fun RecommendedActivePsychologistsRow(
    item: Psychologists
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .widthIn(min = 140.dp)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Psikolog Görseli
        Image(
            painter = painterResource(com.VrStressRoom.vrstressroom.R.drawable.userimage),
            contentDescription = "Psikolog Fotoğrafı",
            modifier = Modifier
                .size(60.dp)
                .padding(bottom = 8.dp)
        )

        // İsim
        Text(
            text = "Psk.${item.namePsychologists}",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            lineHeight = 10.sp,
            softWrap = true, // <-- kelime sonuna geldiğinde alta geçer
            maxLines = 2,    // <-- maksimum 2 satır olsun istersen (opsiyonel)
            overflow = TextOverflow.Ellipsis, // <-- Taşarsa üç nokta koyar (opsiyonel)
            modifier = Modifier.fillMaxWidth()
        )


        // Yıldızlar ve Puan
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            repeat(5) { index ->
                val icon = if (index < item.rating) com.VrStressRoom.vrstressroom.R.drawable.selectedstaricon else com.VrStressRoom.vrstressroom.R.drawable.defaultstaricon
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Yıldız",
                    modifier = Modifier.size(12.dp)
                )
            }

            Text(
                text = "${item.rating},0",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Buton
        Button(
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFFFFF)),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(36.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Görüşme Başlat",
                color = Color.Black,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,

            )
        }
    }
}

val kidsTest = listOf(
    Test("Okul değişikliği yaptın mı?", false),
    Test("Yeni kardeşin oldu mu?", false),
    Test("Ebeveynlerinden biri iş değiştirdi mi?", false),
    Test("Taşındınız mı?", false),
    Test("Aile içinde büyük bir tartışma yaşandı mı?", false),
    Test("Ebeveynlerin ayrıldı mı veya boşandı mı?", false),
    Test("Yakın birinin (aile veya evcil hayvan) ölümü oldu mu?", false),
    Test("Okulda zorbalığa uğradın mı?", false),
    Test("Senin veya ailenden birinin uzun süreli hastalığı oldu mu?", false),
    Test("Anne veya baban öldü mü?", false),
    Test("Evde yaşayan birinin uzun süre hastalandı mı?", false),
    Test("Yakın bir arkadaşın veya akraban öldü mü?", false),
    Test("Kardeşin öldü mü?", false),
    Test("Okuldan uzaklaştırıldın mı?", false),
    Test("Ev değiştirdiniz mi?", false),
    Test("Yeni bir kardeşin doğdu mu?", false),
    Test("Anne veya baban işsiz kaldı mı?", false),
    Test("Aile içinde sürekli kavga yaşandı mı?", false),
    Test("Bir evcil hayvanını kaybettin mi?", false),
    Test("Sınıfta başarısızlık yaşadın mı?", false),
    Test("Yakın bir arkadaşınla kavga ettin mi veya ayrıldın mı?", false),
    Test("Öğretmenin değişti mi?", false),
    Test("Tatillerin iptal oldu mu?", false),
    Test("Kardeşin hastaneye yattı mı?", false)
)

val adolescentTest = listOf(
    Test("Sınav stresi (YKS, LGS vb.) yaşadın mı?", false),
    Test("Aile içinde boşanma veya ayrılık yaşandı mı?", false),
    Test("Aşk ilişkin sonlandı mı?", false),
    Test("Yakın bir arkadaşını kaybettin mi (taşınma, küslük gibi)?", false),
    Test("Ciddi bir hastalık geçirdin mi?", false),
    Test("Ailen maddi zorluk yaşadı mı?", false),
    Test("Okul değiştirdin mi?", false),
    Test("Uzun süreli ev içi çatışma yaşadın mı?", false),
    Test("Sosyal dışlanmaya veya zorbalığa maruz kaldın mı?", false),
    Test("Ebeveynlerinden biri öldü mü?", false),
    Test("Planlanmamış bir gebelik veya kürtaj yaşadın mı?", false),
    Test("Ailenden biri ağır hastalandı mı?", false),
    Test("Ebeveynlerin boşandı mı?", false),
    Test("Yakın bir arkadaşın öldü mü?", false),
    Test("Kardeşin öldü mü?", false),
    Test("Uyuşturucu veya alkol kullandın mı?", false),
    Test("Erkek/kız arkadaşınla ayrıldın mı?", false),
    Test("Sınıfta başarısızlık yaşadın mı?", false),
    Test("Okuldan uzaklaştırıldın mı?", false),
    Test("Üniversiteye hazırlık stresi yaşadın mı?", false),
    Test("Ebeveynlerin tartışmaları arttı mı?", false),
    Test("Aile içinde maddi sıkıntı yaşandı mı?", false),
    Test("Ailenden biri hapse girdi mi?", false),
    Test("Kardeşin hastalandı mı veya hastaneye yattı mı?", false),
    Test("Ailene yeni bir yetişkin geldi mi?", false),
    Test("Kardeşin evden ayrıldı mı?", false),
    Test("Ailende dini veya sosyal baskı arttı mı?", false),
    Test("Cinsel kimliğinle ilgili içsel çatışmalar yaşadın mı?", false)
)

val adultTest = listOf(
    Test("Eşin öldü mü?", false),
    Test("Boşandın mı?", false),
    Test("Evliliğinde ayrılık yaşadın mı?", false),
    Test("Hapse girdin mi?", false),
    Test("Yakın bir aile üyesi öldü mü?", false),
    Test("Kişisel bir yaralanma veya hastalık geçirdin mi?", false),
    Test("Evlendin mi?", false),
    Test("İşten çıkarıldın mı?", false),
    Test("Evliliğinde uzlaşma yaşadın mı?", false),
    Test("Emekli oldun mu?", false),
    Test("Aile üyesinin sağlık durumunda değişiklik oldu mu?", false),
    Test("Gebelik yaşadın mı?", false),
    Test("Cinsel zorluklar yaşadın mı?", false),
    Test("Ailene yeni bir üye katıldı mı?", false),
    Test("İşletmende yeniden düzenleme yapıldı mı?", false),
    Test("Mali durumunda değişiklik oldu mu?", false),
    Test("Yakın bir arkadaşın öldü mü?", false),
    Test("Farklı bir iş koluna geçtin mi?", false),
    Test("Argümanların sıklığında değişiklik yaşadın mı?", false),
    Test("Büyük bir ipotek aldın mı?", false),
    Test("İpotek veya kredi haczi yaşadın mı?", false),
    Test("İş yerinde sorumluluklarında değişiklik oldu mu?", false),
    Test("Çocuğun evden ayrıldı mı?", false),
    Test("Kayınvalidelerle sorun yaşadın mı?", false),
    Test("Üstün kişisel bir başarı elde ettin mi?", false),
    Test("Eşin işe başladı mı veya işi bıraktı mı?", false),
    Test("Okulun başlangıcı veya sonu gibi bir değişim yaşadın mı?", false),
    Test("Yaşam koşullarında değişim yaşadın mı?", false),
    Test("Kişisel alışkanlıklarında değişiklik yaptın mı?", false),
    Test("Patronunla sorun yaşadın mı?", false),
    Test("Çalışma saatlerinde veya koşullarında değişiklik oldu mu?", false),
    Test("İkamet adresini değiştirdin mi?", false),
    Test("Okul değiştirdin mi?", false),
    Test("Eğlence alışkanlıklarında değişiklik yaşadın mı?", false),
    Test("Dini faaliyetlerinde değişiklik oldu mu?", false),
    Test("Sosyal aktivitelerinde değişiklik yaşadın mı?", false),
    Test("Küçük bir ipotek veya kredi aldın mı?", false),
    Test("Uyku alışkanlıklarında değişiklik yaşadın mı?", false)
)

data class User(val name: String)

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(2.dp, Color.White),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isPressed) Color.White else Color.Transparent,
            contentColor = if (isPressed) Color.Black else Color.White
        ),
        modifier = Modifier
            .padding(start = 32.dp, top = 24.dp, end = 32.dp)
            .clip(RoundedCornerShape(4.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun mains(){

}








data class Test(val name:String,val reply: Boolean)