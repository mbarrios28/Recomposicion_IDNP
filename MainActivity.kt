import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CineScreen()
            }
        }
    }
}

@Composable
fun CineScreen() {
    // Estado
    var cantidad by remember { mutableStateOf(1) }
    var conCancha by remember { mutableStateOf(false) }
    var conBebida by remember { mutableStateOf(false) }
    var aplicarDescuento by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    // Precios
    val precioEntrada = 15.0
    val precioCancha = 8.0
    val precioBebida = 6.0

    // Cálculos
    val subtotalEntradas = cantidad * precioEntrada
    val subtotalExtras = (if (conCancha) precioCancha else 0.0) +
                         (if (conBebida) precioBebida else 0.0)
    val subtotal = subtotalEntradas + subtotalExtras
    val descuento = if (aplicarDescuento) subtotal * 0.10 else 0.0
    val total = subtotal - descuento

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "🎬 Cine - Compra de Entradas",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        HorizontalDivider()

        // Cantidad de entradas
        Text("Cantidad de entradas", fontWeight = FontWeight.SemiBold)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { if (cantidad > 1) cantidad-- },
                enabled = cantidad > 1
            ) { Text("−") }

            Text("$cantidad", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Button(onClick = { cantidad++ }) { Text("+") }
        }

        Text("Precio por entrada: S/ $precioEntrada", fontSize = 14.sp)

        HorizontalDivider()

        // Extras
        Text("Extras", fontWeight = FontWeight.SemiBold)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = conCancha,
                onCheckedChange = { conCancha = it }
            )
            Text("Cancha  (S/ $precioCancha)")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = conBebida,
                onCheckedChange = { conBebida = it }
            )
            Text("Bebida  (S/ $precioBebida)")
        }

        HorizontalDivider()

        // Descuento
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = aplicarDescuento,
                onCheckedChange = { aplicarDescuento = it }
            )
            Text("Aplicar descuento 10%")
        }

        HorizontalDivider()

        // Resumen
        Text("Resumen", fontWeight = FontWeight.SemiBold)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal")
            Text("S/ %.2f".format(subtotal))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Descuento")
            Text("- S/ %.2f".format(descuento))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(
                "S/ %.2f".format(total),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        // Botón comprar
        Button(
            onClick = {
                mensaje = "Compra exitosa: $cantidad entrada(s) por S/ %.2f".format(total)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("COMPRAR")
        }

        if (mensaje.isNotEmpty()) {
            Text(
                mensaje,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}