package trindade.wesley.financask.ui.financas.extension

import java.math.BigDecimal

fun <E> List<E>.sumBy(selector: (E) -> BigDecimal): BigDecimal {
    return this.fold(BigDecimal.ZERO) { acc, e -> acc + selector.invoke(e) }
}
