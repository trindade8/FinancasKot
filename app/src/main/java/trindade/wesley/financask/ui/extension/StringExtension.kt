package trindade.wesley.financask.ui.extension

val LIMITE_CARACTERES = 14
fun String.limitaCaracteres() : String{

    if(this.length > LIMITE_CARACTERES)
    {
        return "${this.substring(0, LIMITE_CARACTERES)}..."
    }
    return this
}

fun String.primeiraMaiuscula() : String
{
    if(this.length>1)
    {
        val tamanhoPalavra = this.length-1
        val primeiraLetra =  this.substring(0,1)
        val continuacao = "${this.substring(1,tamanhoPalavra )}"
        return "${primeiraLetra.toUpperCase()}${continuacao}"
    }
    return this.toUpperCase()
}

fun String.primeirasMaiusculas() : String
{
    val split = this.split(' ')
    var stringFormadata = ""
    split.forEach {
        s: String ->
        stringFormadata+= "${s.primeiraMaiuscula()} "

    }
    return stringFormadata

}