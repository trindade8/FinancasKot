package trindade.wesley.financask.ui.financas.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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

class TransacaoDialog(private val view: View, private val context: Context, private val tipoTransacao: Tipo) {

    private val formulario = criaLayout()
    private val data = formulario.form_transacao_data
    private val categoria = formulario.form_transacao_categoria
    private val valor = formulario.form_transacao_valor


    public fun adicionaTransacaoDialog(delegate: TransacaoDelegate) {
        configuraDatePikerDialog()
        configuraSpinerCategoriaLancamento()
        configuraAlertDialog(delegate)
    }

    private fun configuraAlertDialog(delegate: TransacaoDelegate) {
         val titulo = if (this.tipoTransacao== Tipo.RECEITA)  R.string.adiciona_receita else R.string.adiciona_despesa
        AlertDialog
                .Builder(context)
                .setTitle(titulo)
                .setPositiveButton("Adicionar",
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
                            val transacao = Transacao(
                                    tipo = tipoTransacao,
                                    valor = valorEmTexto,
                                    categoria = categoriaEmTexto,
                                    data = calendar,
                                    codigo = UUID.randomUUID().toString()
                                    )

                            delegate.delagate(transacao);
                        })
                .setNegativeButton("Cancelar", null)
                .setView(formulario)
                .show()
    }

    private fun configuraSpinerCategoriaLancamento() {
        val array: Int = if (tipoTransacao == Tipo.RECEITA) R.array.categorias_de_receita else R.array.categorias_de_despesa
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

    private fun configuraTimePikerDialog()
    {
        val currentCalendar = Calendar.getInstance()
        formulario.form_transacao_data.setText(Calendar.getInstance().formataParaBrasileiro())
        formulario.form_transacao_data.setOnClickListener {
//            DatePickerDialog(context,
//                    DatePickerDialog.OnDateSetListener { view, ano, mes, dia ->
//                        val dataSelecionada = Calendar.getInstance()
//                        dataSelecionada.set(ano, mes, dia)
//                        formulario.form_transacao_data.setText(dataSelecionada.formataParaBrasileiro())
//                    }, currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH)).show()
            TimePickerDialog(this.context,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            },currentCalendar.get(Calendar.HOUR_OF_DAY),currentCalendar.get(Calendar.MINUTE),true).show()
        }
    }

    private fun criaLayout() = LayoutInflater.from(context)
            .inflate(R.layout.form_transacao, view as ViewGroup, false)
}