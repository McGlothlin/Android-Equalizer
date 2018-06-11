package com.seanmcglothlin.equalizer

import android.os.Bundle
import android.widget.*
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.media.audiofx.*
import android.media.MediaPlayer
import android.util.Log


class MainActivity : AppCompatActivity() {

    internal val maxBands = 6
    internal var enabled: Switch? = null
    internal var reset: Button? = null
    internal var eq: Equalizer? = null
    internal var bb: BassBoost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bassBoost: SeekBar = findViewById(R.id.bassBoostSeekBar) as SeekBar

        var band0: SeekBar = findViewById(R.id.band0) as SeekBar
        var band1: SeekBar = findViewById(R.id.band1) as SeekBar
        var band2: SeekBar = findViewById(R.id.band2) as SeekBar
        var band3: SeekBar = findViewById(R.id.band3) as SeekBar
        var band4: SeekBar = findViewById(R.id.band4) as SeekBar

        enabled = findViewById(R.id.enabledSwitch) as Switch
        reset = findViewById(R.id.resetButton) as Button

        eq = Equalizer(0, MediaPlayer().audioSessionId)
        bb = BassBoost(0, MediaPlayer().audioSessionId)

        eq!!.enabled

        Log.d("SEAN", "Init complete")
        Log.d("SEAN", "Number of bands on this device: " + eq!!.numberOfBands)
        Log.d("SEAN", eq!!.getCenterFreq(1).toString())


        band0!!.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {

                internal var progress: Int = 0

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    Log.d("SEAN", "Band level range: " + eq!!.bandLevelRange[0] + " to " + eq!!.bandLevelRange[1])

                    var newLevel: Short = progress.toShort()

//                    eq!!.setBandLevel(0, (eq!!.bandLevelRange[0] / progress).toShort())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

        })

        Log.d("SEAN", "END TEH DEBUGZ")

    }

    override fun onPause() {
        super.onPause()
    }

}


