package trindade.wesley.financask.ui.financas.extension

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
    if(this.compareTo(BigDecimal.ZERO) < 0 )
        return formatoBrasileiro.format(this* BigDecimal(-1)).replace("R$","-R$ ")
    else
        return formatoBrasileiro.format(this).replace("R$","R$ ")
}

fun Calendar.formataParaBrasileiro(texto : String) : Calendar{

    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyy")
    this.time =formatoBrasileiro.parse(texto)
    return this

}