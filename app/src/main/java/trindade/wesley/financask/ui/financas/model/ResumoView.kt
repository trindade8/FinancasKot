package trindade.wesley.financask.ui.financas.model

import java.math.BigDecimal
import trindade.wesley.financask.ui.financas.extension.sumBy

class ResumoView(private val transacoes : MutableList<Transacao>) {


    public  fun totalReceita() : BigDecimal{
        return this.totalPorTipo(Tipo.RECEITA)
    }

    public fun totalDespesa(): BigDecimal{
        return this.totalPorTipo(Tipo.DESPESA)
    }

    private fun totalPorTipo(tipo : Tipo) : BigDecimal
    {
        val valor = transacoes.filter { it.tipo == tipo }.sumByDouble { it.valor.toDouble() }
        return valor.toBigDecimal()
    }

    public fun resumo() : BigDecimal
    {
        return totalReceita().subtract(totalDespesa())
    }


}