package com.seanmcglothlin.equalizer

import android.os.Bundle
import android.widget.*
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.media.audiofx.*




class MainActivity : AppCompatActivity() {

    internal val maxBands = 6
    internal var enabled: Switch? = null
    internal var reset: Button? = null
    internal var eq: Equalizer? = null
    internal var bb: BassBoost? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enabled = findViewById(R.id.enabledSwitch) as Switch
        reset = findViewById(R.id.resetButton) as Button

        var bassBoost: SeekBar = findViewById(R.id.bassBoostSeekBar) as SeekBar
        var band1: SeekBar = findViewById(R.id.band1) as SeekBar
        var band2: SeekBar = findViewById(R.id.band2) as SeekBar
        var band3: SeekBar = findViewById(R.id.band2) as SeekBar
        var band4: SeekBar = findViewById(R.id.band2) as SeekBar
        var band5: SeekBar = findViewById(R.id.band2) as SeekBar
        var band6: SeekBar = findViewById(R.id.band2) as SeekBar

        eq = Equalizer(0, 0)
        bb = BassBoost(0, 0)

        band1!!.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {

                internal var progress: Int = 0

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

        })

    }

    override fun onPause() {
        super.onPause()
    }

}


