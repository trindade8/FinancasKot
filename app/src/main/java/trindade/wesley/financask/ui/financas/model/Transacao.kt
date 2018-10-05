package trindade.wesley.financask.ui.financas.model

import java.math.BigDecimal
import java.util.*

class Transacao(val  valor: BigDecimal,
                val categoria : String,
                val tipo : Tipo,
                val data : Calendar = Calendar.getInstance()) {

    constructor(valor : BigDecimal, tipo : Tipo) : this(valor,"Indefinida",tipo)
    constructor(valor : BigDecimal, tipo : Tipo, data : Calendar) : this(valor,"Indefinida",tipo)


    companion object Exemplos{
       fun ListaDeExemploDeTransacoes(): List<Transacao> {
            return listOf(Transacao(BigDecimal(25), "comida ", Tipo.DESPESA),
                    Transacao(BigDecimal(45), "economia", Tipo.RECEITA),
                    Transacao(tipo = Tipo.RECEITA,
                            valor = BigDecimal(150),
                            categoria = "crédito referente ao cliente XML de Saida"))
        }
    }





}