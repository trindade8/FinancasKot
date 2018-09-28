package trindade.wesley.financask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.form_transacao.view.*
import kotlinx.android.synthetic.main.transacao_item.view.*
import trindade.wesley.financask.R
import trindade.wesley.financask.ui.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.model.Transacao
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


class ListaTransacoesAdapter(transacoes : List<Transacao>,
                             context : Context) : BaseAdapter(){

    private val transacoes = transacoes
    private val contexto   = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var    viewCriada = LayoutInflater.from(contexto).inflate(R.layout.transacao_item, parent, false)
        var transacao = transacoes[position]
        viewCriada.transacao_valor.text = transacao.valor.toString()
        viewCriada.transacao_categoria.text = transacao.categoria
        viewCriada.transacao_data.text =  transacao.data.formataParaBrasileiro()
        return viewCriada
    }

    override fun getItem(position: Int): Transacao {
        return this.transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return this.transacoes.size
    }


}