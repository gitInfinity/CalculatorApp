package com.example.myapplication.ui.theme
import android.R.style
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope

val buttonList = listOf(
    "C", "(", ")", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "AC","0", ".", "="
)

@Composable
fun Calculator(modifier: Modifier = Modifier, viewModel: CalculatorViewModel = CalculatorViewModel()){
    val equationText = viewModel.equationText.observeAsState()
    val resultText = viewModel.resultText.observeAsState()
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFF1EFEF), // Light Blue
            Color(0xFF0C1473)  // Dark Blue
        )
    )

    Box(
        modifier = modifier
            .background(gradientBackground)
            .padding(8.dp)
            .fillMaxSize()
    ){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ){
            Text(
                text = equationText.value?:"",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = resultText.value?:"",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 48.sp),
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
            ) {
                items(buttonList){
                    calcButton(buttn = it, onClick = {
                        viewModel.onClickButton(it)
                    })
                }
            }
        }
    }
}

@Composable
fun CalculatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color.Cyan,
            onPrimary = White,
            background = DarkGray,
            onBackground = White
        ),
        typography = Typography(
            bodyLarge = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        ),
        shapes = Shapes(
            small = RoundedCornerShape(8.dp),
            medium = RoundedCornerShape(16.dp),
            large = RoundedCornerShape(24.dp)
        ),
        content = content
    )
}

@Composable
fun calcButton(buttn: String, onClick: () -> Unit = {}){
    val scale = remember { Animatable(1f) }

    LaunchedEffect(scale) {
        scale.animateTo(
            targetValue = 0.95f,
            animationSpec = tween(durationMillis = 50, easing = LinearEasing)
        )
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 50, easing = LinearEasing)
        )
    }
   Box(modifier = Modifier.padding(8.dp)){
       FloatingActionButton(
           onClick = onClick,
           modifier = Modifier.size(68.dp).graphicsLayer(scaleX = scale.value, scaleY = scale.value),
           contentColor = MaterialTheme.colorScheme.onPrimary,
           containerColor = getColor(buttn),
           shape = MaterialTheme.shapes.medium,
           elevation = FloatingActionButtonDefaults.elevation(4.dp)
       ) {
           Text(
               text = buttn,
               style = MaterialTheme.typography.bodyLarge)
       }
   }
}

fun getColor(buttn: String): Color {
    return when (buttn) {
        "AC", "C" -> Color(0xFFF05454)
        "=", "/", "*", "-", "+" -> Color(0xFF6B82BE)
        "(", ")" -> Color(0xFF75C082)
        else -> Color(0xFF976AA6)
    }
}