package trindade.wesley.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.resumo_card.*

import trindade.wesley.financask.R
import trindade.wesley.financask.ui.adapter.ListaTransacoesAdapter
import trindade.wesley.financask.ui.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.extension.sumBy
import trindade.wesley.financask.ui.model.Tipo
import trindade.wesley.financask.ui.model.Transacao
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = geraListaDeExemploDeTransacoes()
        configuraListaAdapter(transacoes)
        populaCabecalhoSuperior(transacoes)

    }

    fun configuraListaAdapter(transacoes: List<Transacao>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transacoes)
        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))

    }

    fun geraListaDeExemploDeTransacoes(): List<Transacao> {
        return listOf(Transacao(BigDecimal(25), "comida ", Tipo.DESPESA),
                Transacao(BigDecimal(45), "economia", Tipo.RECEITA),
                Transacao(tipo = Tipo.RECEITA,
                        valor = BigDecimal(150),
                        categoria = "crédito referente ao cliente XML de Saida"))
    }

    fun populaCabecalhoSuperior(lista : List<Transacao> )
    {
        val _totalDespesas = lista.filter { it.tipo == Tipo.DESPESA }.sumBy { it-> it.valor }
        val _totalReceita = lista.filter { it.tipo == Tipo.RECEITA }.sumBy { it-> it.valor }
        val _saldo = _totalReceita - _totalDespesas

        resumo_card_receita.text = _totalReceita.formataParaBrasileiro()
        resumo_card_despesa.text = _totalDespesas.formataParaBrasileiro()
        resumo_card_total.text   = _saldo.formataParaBrasileiro()


    }
}

