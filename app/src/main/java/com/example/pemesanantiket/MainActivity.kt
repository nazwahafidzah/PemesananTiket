package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale


const val HARGA_TIKET = 40000
const val MAKS_TIKET = 10


val Ink = Color(0xFF0E1116)
val LatarAtas = Color(0xFF1E1250)
val LatarBawah = Color(0xFF0A0916)
val Cream = Color(0xFFFFF4E0)
val CreamGelap = Color(0xFFEADFC8)
val CokelatMuda = Color(0xFF8A7F68)
val Coral = Color(0xFFFF5F6D)
val Violet = Color(0xFF7C3AED)
val Lime = Color(0xFFD4FF3F)
val AbuGelap = Color(0xFFA7A3C2)


val TinggiBagianAtas = 140.dp
val RadiusLekuk = 14.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PemesananTiketScreen()
        }
    }
}


fun formatRupiah(nilai: Int): String {
    val format = NumberFormat.getIntegerInstance(Locale.forLanguageTag("id-ID"))
    return "Rp" + format.format(nilai)
}


class TicketShape(
    private val sudut: Dp,
    private val radiusLekuk: Dp,
    private val posisiLekuk: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val sudutPx = with(density) { sudut.toPx() }
        val r = with(density) { radiusLekuk.toPx() }
        val y = with(density) { posisiLekuk.toPx() }

        val dasar = Path().apply {
            addRoundRect(
                RoundRect(0f, 0f, size.width, size.height, CornerRadius(sudutPx))
            )
        }
        val lubang = Path().apply {
            addOval(Rect(Offset(-r, y - r), Size(r * 2, r * 2)))
            addOval(Rect(Offset(size.width - r, y - r), Size(r * 2, r * 2)))
        }
        return Outline.Generic(Path.combine(PathOperation.Difference, dasar, lubang))
    }
}

@Composable
fun PemesananTiketScreen() {

    var jumlah by rememberSaveable { mutableIntStateOf(1) }

    // Turunan dari state
    val total = HARGA_TIKET * jumlah


    val totalAnimasi by animateIntAsState(
        targetValue = total,
        animationSpec = tween(durationMillis = 400),
        label = "total"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(LatarAtas, LatarBawah)))
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
        ) {

            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 24.dp)) {
                Text(
                    text = "E-TICKET",
                    color = Lime,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Pemesanan Tiket",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pesan tiket dengan mudah!",
                    color = AbuGelap,
                    fontSize = 15.sp
                )
            }


            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .clip(TicketShape(24.dp, RadiusLekuk, TinggiBagianAtas + 1.dp))
                    .background(Cream)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(TinggiBagianAtas)
                        .background(Brush.linearGradient(listOf(Coral, Violet)))
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = "Harga Tiket",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = formatRupiah(HARGA_TIKET),
                            color = Color.White,
                            fontSize = 36.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            text = "per tiket",
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 13.sp
                        )
                    }
                    Text(
                        text = "ADMIT\nONE",
                        color = Color.White.copy(alpha = 0.55f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 3.sp,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 30.dp)
                    )
                }


                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = RadiusLekuk + 8.dp)
                        .height(2.dp)
                ) {
                    drawLine(
                        color = Color(0xFFCDBF9F),
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(16f, 14f))
                    )
                }


                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = "Jumlah Tiket",
                        color = CokelatMuda,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(12.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(50))
                            .background(CreamGelap)
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StepBtn(label = "−", aktif = jumlah > 1) { jumlah-- }
                        AnimatedContent(
                            targetState = jumlah,
                            modifier = Modifier.weight(1f),
                            transitionSpec = {
                                if (targetState > initialState) {
                                    (slideInVertically { it } + fadeIn()) togetherWith
                                            (slideOutVertically { -it } + fadeOut())
                                } else {
                                    (slideInVertically { -it } + fadeIn()) togetherWith
                                            (slideOutVertically { it } + fadeOut())
                                }
                            },
                            label = "jumlah"
                        ) { angka ->
                            Text(
                                text = "$angka",
                                color = Ink,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.ExtraBold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        StepBtn(label = "+", aktif = jumlah < MAKS_TIKET) { jumlah++ }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        for (i in 1..MAKS_TIKET) {
                            Segmen(aktif = i <= jumlah, modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Maksimal $MAKS_TIKET tiket per pesanan",
                        color = CokelatMuda,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Lime,
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .navigationBarsPadding()
                .padding(horizontal = 24.dp, vertical = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "TOTAL BAYAR",
                    color = Ink.copy(alpha = 0.65f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
                Text(
                    text = formatRupiah(totalAnimasi),
                    color = Ink,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "$jumlah × ${formatRupiah(HARGA_TIKET)}",
                    color = Ink.copy(alpha = 0.65f),
                    fontSize = 13.sp
                )
            }


            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Ink)
                    .clickable { jumlah = 1 }
                    .padding(horizontal = 18.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset",
                    tint = Lime
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "RESET",
                    color = Lime,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Composable
fun Segmen(aktif: Boolean, modifier: Modifier = Modifier) {
    val warna by animateColorAsState(
        targetValue = if (aktif) Violet else CreamGelap,
        animationSpec = tween(250),
        label = "segmen"
    )
    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(50))
            .background(warna)
    )
}

@Composable
fun StepBtn(label: String, aktif: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(if (aktif) Ink else Color(0xFFD3C7AE))
            .clickable(enabled = aktif, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (aktif) Cream else Color(0xFF9A8F78),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    }
}