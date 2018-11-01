package trindade.wesley.financask.ui.financas.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import trindade.wesley.financask.R
import trindade.wesley.financask.ui.financas.delegate.TransacaoDelegate
import trindade.wesley.financask.ui.financas.extension.formataParaBrasileiro
import trindade.wesley.financask.ui.financas.model.Tipo
import trindade.wesley.financask.ui.financas.model.Transacao
import java.math.BigDecimal
import java.util.*

class AlteraTransacaoDialog(private val view: View, private val context: Context, private var transacao : Transacao) {

    private val formulario = criaLayout()
    private val data = formulario.form_transacao_data
    private val categoria = formulario.form_transacao_categoria
    private val valor = formulario.form_transacao_valor


    public fun alteraTransacaoDialog(delegate: TransacaoDelegate) {
        configuraDatePikerDialog()
        configuraSpinerCategoriaLancamento()
        configuraAlertDialog(delegate)
        setDadosTransacao()
    }

    private fun setDadosTransacao()
    {
        val tipo = if (this.transacao.tipo == Tipo.RECEITA) R.array.categorias_de_receita else R.array
                .categorias_de_despesa
        this.data.setText(transacao.data.formataParaBrasileiro())
        this.valor.setText(this.transacao.valor.toString())
        val posicaoCategoria = context.resources.getStringArray(tipo).indexOf(this.transacao.categoria)
        this.categoria.setSelection(posicaoCategoria,true)


    }

    private fun configuraAlertDialog(delegate: TransacaoDelegate) {
         val titulo = if (this.transacao.tipo== Tipo.RECEITA)  R.string.altera_receita else R.string.altera_despesa
        AlertDialog
                .Builder(context)
                .setTitle(titulo)
                .setPositiveButton("Alterar",
                        { dialogInterface, i ->
                            val valorEmTexto = try {
                                valor.text.toString().toBigDecimal()
                            } catch (exp: NumberFormatException) {
                                Toast.makeText(context, "Falha na conversao do valor", Toast.LENGTH_LONG).show()
                                BigDecimal.ZERO
                            }
                            val dataemTexto =data.text.toString()
                            val categoriaEmTexto = categoria.selectedItem.toString()
                            val calendar = Calendar.getInstance().formataParaBrasileiro(dataemTexto)
                            this.transacao = Transacao(
                                    tipo = this.transacao.tipo,
                                    valor = valorEmTexto,
                                    categoria = categoriaEmTexto,
                                    data = calendar,
                                    codigo = this.transacao.codigo)
                            delegate.delagate(transacao);
                        })
                .setNegativeButton("Cancelar", null)
                .setView(formulario)
                .show()
    }

    private fun configuraSpinerCategoriaLancamento() {
        val array: Int = if (this.transacao.tipo == Tipo.RECEITA) R.array.categorias_de_receita else R.array.categorias_de_despesa
        val adapter = ArrayAdapter.createFromResource(context, array, R.layout.support_simple_spinner_dropdown_item)

        formulario.form_transacao_categoria.adapter = adapter
    }

    private fun configuraDatePikerDialog() {
        val currentCalendar = Calendar.getInstance()
        formulario.form_transacao_data.setText(Calendar.getInstance().formataParaBrasileiro())
        formulario.form_transacao_data.setOnClickListener {
            DatePickerDialog(context,
                    DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        formulario.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
                    }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun criaLayout() = LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, view as ViewGroup, false)
}