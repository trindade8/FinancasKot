package trindade.wesley.financask.ui.financas.activity

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import kotlinx.android.synthetic.main.resumo_card.*

import trindade.wesley.financask.R
import trindade.wesley.financask.R.id.lista_transacoes_adiciona_despesa
import trindade.wesley.financask.R.id.lista_transacoes_listview
import trindade.wesley.financask.ui.financas.adapter.ListaTransacoesAdapter
import trindade.wesley.financask.ui.financas.delegate.TransacaoDelegate
import trindade.wesley.financask.ui.financas.dialog.AlteraTransacaoDialog
import trindade.wesley.financask.ui.financas.dialog.TransacaoDialog
import trindade.wesley.financask.ui.financas.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.financas.model.ResumoView
import trindade.wesley.financask.ui.financas.model.Tipo
import trindade.wesley.financask.ui.financas.model.Transacao
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity : AppCompatActivity() , TransacaoDelegate {


    private var transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)


        configuraListaAdapter()
        populaCabecalhoSuperior()
        configuraFabButtons()
    }

    fun configuraListaAdapter() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transacoes)
        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes, this))
        lista_transacoes_listview.setOnItemClickListener{parent,view,posicao,id->
            val transacao = transacoes[posicao]
            AlteraTransacaoDialog(window.decorView,this,transacao)
                    .alteraTransacaoDialog(object : TransacaoDelegate{
                        override fun delagate(transacao: Transacao) {
                            transacoes[posicao]=transacao
                            configuraListaAdapter()
                            populaCabecalhoSuperior()
                        }

                    })


        }


    }

    private fun configuraFabButtons()
    {
        lista_transacoes_adiciona_receita.setOnClickListener{
            chamaDialogTransacao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener{
            chamaDialogTransacao(Tipo.DESPESA)
        }
    }


    private fun chamaDialogTransacao(tipo : Tipo) {
        TransacaoDialog(window.decorView, this, tipo).adicionaTransacaoDialog(this)
    }


    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
        configuraListaAdapter()
        populaCabecalhoSuperior()
    }

    fun populaCabecalhoSuperior() {
        val resumo = ResumoView(transacoes)
        with(resumo_card_receita)
        {
            resumo_card_receita.text = resumo.totalReceita().formataParaBrasileiro()
            resumo_card_receita.setTextColor(ContextCompat.getColor(resumo_card_receita.context, R.color.resumo_receita))
        }

        with(resumo_card_despesa)
        {
            resumo_card_despesa.text = resumo.totalDespesa().formataParaBrasileiro()
            resumo_card_despesa.setTextColor(ContextCompat.getColor(resumo_card_despesa.context, R.color.resumo_despesa))
        }

        with(resumo_card_total)
        {
            resumo_card_total.text = resumo.resumo().formataParaBrasileiro()
            resumo_card_total.setTextColor(porSaldo(resumo.resumo()))
        }

    }

    fun porSaldo(valorSaldo: BigDecimal): Int {
        if (valorSaldo == BigDecimal.ZERO)
            return ContextCompat.getColor(this, return R.color.colorBackground)
        if (valorSaldo > BigDecimal.ZERO)
            return ContextCompat.getColor(this, R.color.colorAccent)
        else
            return ContextCompat.getColor(this, R.color.despesa)


    }

    override fun delagate(transacao: Transacao) {
        atualizaTransacoes(transacao)
        lista_transacoes_adiciona_menu.close(true)
    }
}

