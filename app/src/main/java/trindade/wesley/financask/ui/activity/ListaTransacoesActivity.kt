package trindade.wesley.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

import trindade.wesley.financask.R
import trindade.wesley.financask.ui.adapter.ListaTransacoesAdapter
import trindade.wesley.financask.ui.model.Tipo
import trindade.wesley.financask.ui.model.Transacao
import java.math.BigDecimal
import java.util.*

class ListaTransacoesActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        val transacoes =
                listOf(Transacao(BigDecimal(25),"Comida", Tipo.DESPESA),
                        Transacao(BigDecimal(45),"Economia",Tipo.RECEITA),
                        Transacao(tipo = Tipo.RECEITA,
                                valor= BigDecimal(150),
                                categoria = "Crédito"))
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,transacoes)
        lista_transacoes_listview.setAdapter(ListaTransacoesAdapter(transacoes,this))


    }
}