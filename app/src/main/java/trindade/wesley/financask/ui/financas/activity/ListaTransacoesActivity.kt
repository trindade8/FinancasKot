package trindade.wesley.financask.ui.financas.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*

import trindade.wesley.financask.R
import trindade.wesley.financask.ui.financas.adapter.ListaTransacoesAdapter
import trindade.wesley.financask.ui.financas.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.financas.model.ResumoView
import trindade.wesley.financask.ui.financas.model.Transacao
import java.math.BigDecimal

class ListaTransacoesActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = Transacao.Exemplos.ListaDeExemploDeTransacoes()
        configuraListaAdapter(transacoes)
        populaCabecalhoSuperior(transacoes)

    }

    fun configuraListaAdapter(transacoes: List<Transacao>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transacoes)
        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))

    }



    fun populaCabecalhoSuperior(lista : List<Transacao> )
    {
        val  resumo =  ResumoView(lista)
        with(resumo_card_receita)
        {
            resumo_card_receita.text = resumo.totalReceita().formataParaBrasileiro()
            resumo_card_receita.setTextColor(ContextCompat.getColor(resumo_card_receita.context,R.color.resumo_receita))
        }

        with(resumo_card_despesa)
        {
            resumo_card_despesa.text = resumo.totalDespesa().formataParaBrasileiro()
            resumo_card_despesa.setTextColor(ContextCompat.getColor(resumo_card_despesa.context,R.color.resumo_despesa))
        }

        with(resumo_card_total)
        {
            resumo_card_total.text   = resumo.resumo().formataParaBrasileiro()
            resumo_card_total.setTextColor(porSaldo(resumo.resumo()))
        }

   }

    fun porSaldo( valorSaldo : BigDecimal) : Int{
        if(valorSaldo == BigDecimal.ZERO)
            return ContextCompat.getColor(this,return R.color.colorBackground)
        if(valorSaldo> BigDecimal.ZERO)
            return ContextCompat.getColor(this,R.color.colorAccent)
        else
            return ContextCompat.getColor(this,R.color.despesa)


    }
}

