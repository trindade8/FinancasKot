package trindade.wesley.financask.ui.player

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_player.*
import trindade.wesley.financask.R
import trindade.wesley.financask.ui.financas.extension.porcentagemAudio
import java.lang.Exception


class Player : AppCompatActivity() {

    private  lateinit var mp: MediaPlayer
    private  lateinit var handler : Handler




    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        configuraPlayer()
        configuraSeekBarMusica()
        configuraSeekBarVolume()

        var totalTime = mp.duration;
        progressoMusica.max=totalTime

        btnPlay.setOnClickListener{

                if(!this.mp.isPlaying())
                {
                    //Stopping
                    mp.start()
                    btnPlay.setBackgroundResource(R.drawable.stop)
                }else
                {
                    //Playing
                    mp.pause()
                    btnPlay.setBackgroundResource(R.drawable.play)

                }


        }

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val possition : Int = msg.what
                progressoMusica.setProgress(possition)
                musicaPosicaoAtual.text =  "${ formatadaTempoRestante(possition)}"
                musicaFim.text = "- ${formatadaTempoRestante(totalTime - possition)}"

            }
        }

        var background = object : Thread(){
            override fun run() {
                while(mp != null)
                {
                    try {
                        val msg: Message =  Message()
                        msg.what = mp.currentPosition
                        handler.sendMessage(msg)
                        Thread.sleep(500)
                    }
                    catch(e : Exception)
                    {

                    }
                }
            }
        }.start()

    }


     fun formatadaTempoRestante(tempo : Int): String
    {

        var timeLabel : String = ""
        var min : Int = tempo /1000 / 60
        var sec : Int = tempo / 1000 % 60

        if(sec < 10 )
            timeLabel = "${min}:0${ sec}"
        else
            timeLabel = "${min}:${ sec}"
        return timeLabel

    }

    fun configuraPlayer() {
        mp = MediaPlayer.create(this, R.raw.sample_one)
        mp.setLooping(true)
        mp.seekTo(0)
        mp.setVolume(0.5f, 0.5f)


    }

    fun configuraSeekBarMusica()
    {


        progressoMusica.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser)
                {
                    mp.seekTo(progress)
                    progressoMusica.progress=progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }
    fun configuraSeekBarVolume()
    {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        progressoAudio.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        porcentagemVolume.text = audioManager.porcentagemAudio()
        progressoAudio.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC))

        progressoAudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val volumeNum  = i;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,volumeNum,0)
                porcentagemVolume.text = audioManager.porcentagemAudio()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }


}
