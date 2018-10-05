package trindade.wesley.financask.ui.financas.model

import java.math.BigDecimal
import trindade.wesley.financask.ui.financas.extension.sumBy

class ResumoView(private val transacoes : List<Transacao>) {


    public  fun totalReceita() : BigDecimal{
        return transacoes.filter { it.tipo == Tipo.DESPESA }.sumBy { it-> it.valor }
    }

    public fun totalDespesa(): BigDecimal{
        return transacoes.filter { it.tipo == Tipo.RECEITA }.sumBy { it-> it.valor }
    }

    public fun resumo() : BigDecimal
    {
        return totalReceita().subtract(totalDespesa())
    }


}