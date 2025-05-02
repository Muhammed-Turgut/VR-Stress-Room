package com.VrStressRoom.vrstressroom.Screens.StresTestScreens



import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
import androidx.navigation.compose.rememberNavController
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
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork.StressViewModel


@Composable
fun AiQuizScreen(navController: NavController,
                 context: Context,
                 cameraViewModel: CameraViewModel,
                 stressApi:StressViewModel= viewModel()) {

    BackHandler {
        
    }

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
                emotion = emotion.toString(),stressApi=stressApi,navController)
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
fun AiQuizPage(ageString: String,gender:String,emotion:String,stressApi: StressViewModel,navController: NavController) {
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
                AiQuizFinishScreen(stressApi,navController)
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
fun AiQuizFinishScreen(stressApi: StressViewModel,navController: NavController) {

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
                            onClick = {
                                navController.navigate("MainPage")
                            },
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

                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            AiStressLevelIndicator(
                                stressLevel = response!!.stress_score,
                                modifier = Modifier.size(120.dp) // Responsive: gösterge boyutu
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    InfoCardWithBot(text = response!!.advice)

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


                    nefesEgzersizleri()
                    Spacer(modifier = Modifier.height(60.dp))

                }
            }
        }
}
@Composable
fun InfoCardWithBot(text: String) {
    // görseli drawable'a koy: resmî adı "bot_icon.png" olsun
    Box(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(end = 80.dp) // sağ alt köşedeki robot için boşluk bırak
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }

        // Sağ alt köşedeki bot karakteri
        Image(
            painter = painterResource(R.drawable.maskotbot),
            contentDescription = "Bot",
            modifier = Modifier
                .size(64.dp)
                .align(Alignment.BottomEnd)
                .padding(8.dp)
        )
    }
}

@Composable
fun AiStressLevelIndicator(
    modifier: Modifier = Modifier,
    stressLevel: Int, // 0 - 100 arasında
    maxLevel: Int = 100
) {
    val sweepAngle = (stressLevel / maxLevel.toFloat()) * 360f
    val strokeWidth = with(LocalDensity.current) { 12.dp.toPx() } // Responsive kalınlık
    val animatedStressLevel by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    Box(
        modifier = modifier
            .aspectRatio(1f) // Her zaman kare kalsın
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Arka plan dairesi (soft gri)
            drawArc(
                color = Color.LightGray.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Öndeki seviye dairesi
            drawArc(
                color = when {
                    stressLevel < 40 -> Color(0xFF4CAF50) // Yeşil
                    stressLevel < 70 -> Color(0xFFFFC107) // Sarı
                    else -> Color(0xFFFF5F9E)             // Pembe
                },
                startAngle = -90f,
                sweepAngle = animatedStressLevel, // Animasyonlu gösterim
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Ortadaki içerik
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$stressLevel%",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (stressLevel < 40) "Rahat" else if (stressLevel < 70) "Orta" else "Stresli",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
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


@Composable
fun nefesEgzersizleri(){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Daha yumuşak köşeler
            .background(Color.White)
            .padding(16.dp), // İç boşluk
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Boşlukları daha dengeli dağıtır
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // İçeriğin fazla yer kaplamasını sağlar
                .padding(end = 12.dp) // Image'den biraz uzak durur
        ) {
            Text(
                text = "Nefes Egzersizi\nYap ve Rahatla",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nefes egzersizleri, stresi azaltıp\n" +
                        "zihni sakinleştirerek bedenin doğal\n" +
                        "dengesini korumaya yardımcı olur.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5F9E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier
                    .height(48.dp)
                    .defaultMinSize(minWidth = 140.dp)
            ) {
                Text(
                    text = "Başla",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.nefesegzersizi),
            contentDescription = "Nefes Egzersizi",
            contentScale = ContentScale.Crop, // Daha iyi görüntü oturması için
            modifier = Modifier
                .size(120.dp) // Biraz daha küçük, tasarıma uyumlu
                .clip(RoundedCornerShape(12.dp)) // Hafif arka plan opsiyonel
        )
    }

} //Burası nefes egzersiz bölümü.


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
    val stressApi:StressViewModel=viewModel()
    val navController = rememberNavController()

    AiQuizFinishScreen(stressApi = stressApi, navController = navController)
}


data class Test(val name:String,val reply: Boolean)