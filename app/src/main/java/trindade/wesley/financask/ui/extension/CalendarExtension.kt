package trindade.wesley.financask.ui.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro() : String {
    val formatoBrasileiro = "dd/MM/yyyy"
    return SimpleDateFormat(formatoBrasileiro).format(time)
}

fun BigDecimal.formataParaBrasileiro() : String
{
    val formatoBrasileiro = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    return formatoBrasileiro.format(this).replace("R$","R$ ")
}