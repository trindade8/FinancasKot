package trindade.wesley.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.transacao_item.view.*
import trindade.wesley.financask.R
import trindade.wesley.financask.ui.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.extension.limitaCaracteres
import trindade.wesley.financask.ui.extension.primeiraMaiuscula
import trindade.wesley.financask.ui.extension.primeirasMaiusculas
import trindade.wesley.financask.ui.model.Tipo
import trindade.wesley.financask.ui.model.Transacao


class ListaTransacoesAdapter(transacoes: List<Transacao>,
                             context: Context) : BaseAdapter() {

    private val transacoes = transacoes
    private val contexto = context

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewCriada = LayoutInflater.from(contexto).inflate(R.layout.transacao_item, parent, false)
        var transacao = transacoes[position]

        if (transacao.tipo == Tipo.DESPESA) {
            viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(contexto, R.color.despesa))
            viewCriada.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        } else {
            viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(contexto, R.color.receita))
            viewCriada.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        }


        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
        viewCriada.transacao_categoria.text = transacao.categoria.limitaCaracteres()
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
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