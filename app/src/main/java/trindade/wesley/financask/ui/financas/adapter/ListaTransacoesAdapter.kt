package trindade.wesley.financask.ui.financas.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.transacao_item.view.*
import trindade.wesley.financask.R
import trindade.wesley.financask.ui.financas.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.financas.extension.limitaCaracteres
import trindade.wesley.financask.ui.financas.model.Tipo
import trindade.wesley.financask.ui.financas.model.Transacao


class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val contexto: Context) : BaseAdapter() {

    private val LimiteCaracteres = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewCriada = LayoutInflater.from(contexto).inflate(R.layout.transacao_item, parent, false)
        var transacao = transacoes[position]

        adicionaValor(transacao, viewCriada)
        adicionaCategoria(transacao, viewCriada)
        adicionaData(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)

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

    fun adicionaValor(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_valor.setTextColor(porCor(transacao.tipo))
        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
    }

    fun adicionaIcone(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_icone.setBackgroundResource(getIconeLancamento(transacao.tipo))
    }

    fun adicionaCategoria(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaCaracteres()
    }

    fun adicionaData(transacao: Transacao, viewCriada: View) {
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    fun getIconeLancamento(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.drawable.icone_transacao_item_receita
        else
            return R.drawable.icone_transacao_item_despesa
    }

    fun porCor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return ContextCompat.getColor(contexto, R.color.receita)
        else
            return ContextCompat.getColor(contexto, R.color.despesa)

    }

}