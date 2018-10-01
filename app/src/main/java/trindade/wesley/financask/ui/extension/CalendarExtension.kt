package trindade.wesley.financask.ui.extension

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Calendar.formataParaBrasileiro() : String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    val  dataFormatada = format.format(this.time)
    return dataFormatada
}

fun BigDecimal.formataParaBrasileiro() : String
{
    val formatoBrasileiro = DecimalFormat.getCurrencyInstance(Locale("pt", "br"))
    val moedaFormatada = formatoBrasileiro.format(this).replace("R$","R$ ")
    return moedaFormatada
}