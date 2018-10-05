package trindade.wesley.financask.ui.financas.extension

import android.media.AudioManager
import java.math.RoundingMode
import java.text.DecimalFormat

fun Int.inPorcentagem() : String {
   return "00${this / 100}%"
}

fun AudioManager.porcentagemAudio() : String
{
   val df = DecimalFormat("###")
   if(this.getStreamMaxVolume(AudioManager.STREAM_MUSIC)>0){
      val value : Float = this.getStreamVolume(AudioManager.STREAM_MUSIC) /  this.getStreamMaxVolume(AudioManager.STREAM_MUSIC) .toFloat()
      return "${df.format(value*100)}%"

   }else
      return "0%"


}