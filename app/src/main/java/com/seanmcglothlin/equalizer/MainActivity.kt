package com.seanmcglothlin.equalizer

import android.*
import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.app.*
import android.widget.*
import android.content.*

import android.media.audiofx.*

class MainActivity : Activity(), SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    internal var bass_boost_label: TextView? = null
    internal var bass_boost: SeekBar? = null
    internal var enabled: CheckBox? = null
    internal var flat: Button? = null

    internal var eq: Equalizer? = null
    internal var bb: BassBoost? = null

    internal var min_level = 0
    internal var max_level = 100
    internal var sliders = arrayOfNulls<SeekBar>(MAX_SLIDERS)
    internal var slider_labels = arrayOfNulls<TextView>(MAX_SLIDERS)
    internal var num_sliders = 0

    /*=============================================================================
    onCreate
=============================================================================*/
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /*=============================================================================
    onCreate
=============================================================================*/
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        enabled = findViewById(R.id.enabled) as CheckBox
        enabled!!.setOnCheckedChangeListener(this)

        flat = findViewById(R.id.flat) as Button
        flat!!.setOnClickListener(this)

        bass_boost = findViewById(R.id.bass_boost) as SeekBar
        bass_boost!!.setOnSeekBarChangeListener(this)
        bass_boost_label = findViewById(R.id.bass_boost_label) as TextView

        sliders[0] = findViewById(R.id.slider_1) as SeekBar
        slider_labels[0] = findViewById(R.id.slider_label_1) as TextView
        sliders[1] = findViewById(R.id.slider_2) as SeekBar
        slider_labels[1] = findViewById(R.id.slider_label_2) as TextView
        sliders[2] = findViewById(R.id.slider_3) as SeekBar
        slider_labels[2] = findViewById(R.id.slider_label_3) as TextView
        sliders[3] = findViewById(R.id.slider_4) as SeekBar
        slider_labels[3] = findViewById(R.id.slider_label_4) as TextView
        sliders[4] = findViewById(R.id.slider_5) as SeekBar
        slider_labels[4] = findViewById(R.id.slider_label_5) as TextView
        sliders[5] = findViewById(R.id.slider_6) as SeekBar
        slider_labels[5] = findViewById(R.id.slider_label_6) as TextView
        sliders[6] = findViewById(R.id.slider_7) as SeekBar
        slider_labels[6] = findViewById(R.id.slider_label_7) as TextView
        sliders[7] = findViewById(R.id.slider_8) as SeekBar
        slider_labels[7] = findViewById(R.id.slider_label_8) as TextView

        eq = Equalizer(0, 0)
        if (eq != null) {
            eq!!.enabled = true
            val num_bands = eq!!.numberOfBands.toInt()
            num_sliders = num_bands
            val r = eq!!.bandLevelRange
            min_level = r[0].toInt()
            max_level = r[1].toInt()
            var i = 0
            while (i < num_sliders && i < MAX_SLIDERS) {
                val freq_range = eq!!.getBandFreqRange(i.toShort())
                sliders[i].setOnSeekBarChangeListener(this)
                slider_labels[i].setText(formatBandLabel(freq_range))
                i++
            }
        }
        for (i in num_sliders until MAX_SLIDERS) {
            sliders[i].setVisibility(View.GONE)
            slider_labels[i].setVisibility(View.GONE)
        }

        bb = BassBoost(0, 0)
        if (bb != null) {
        } else {
            bass_boost!!.visibility = View.GONE
            bass_boost_label!!.visibility = View.GONE
        }

        updateUI()
    }

    /*=============================================================================
    onProgressChanged
=============================================================================*/
    override fun onProgressChanged(seekBar: SeekBar, level: Int,
                                   fromTouch: Boolean) {
        if (seekBar === bass_boost) {
            bb!!.enabled = if (level > 0) true else false
            bb!!.setStrength(level.toShort()) // Already in the right range 0-1000
        } else if (eq != null) {
            val new_level = min_level + (max_level - min_level) * level / 100

            for (i in 0 until num_sliders) {
                if (sliders[i] === seekBar) {
                    eq!!.setBandLevel(i.toShort(), new_level.toShort())
                    break
                }
            }
        }
    }

    /*=============================================================================
    onStartTrackingTouch
=============================================================================*/
    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    /*=============================================================================
    onStopTrackingTouch
=============================================================================*/
    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    /*=============================================================================
    formatBandLabel
=============================================================================*/
    fun formatBandLabel(band: IntArray): String {
        return milliHzToString(band[0]) + "-" + milliHzToString(band[1])
    }

    /*=============================================================================
    milliHzToString
=============================================================================*/
    fun milliHzToString(milliHz: Int): String {
        if (milliHz < 1000) return ""
        return if (milliHz < 1000000)
            "" + milliHz / 1000 + "Hz"
        else
            "" + milliHz / 1000000 + "kHz"
    }

    /*=============================================================================
    updateSliders
=============================================================================*/
    fun updateSliders() {
        for (i in 0 until num_sliders) {
            val level: Int
            if (eq != null)
                level = eq!!.getBandLevel(i.toShort()).toInt()
            else
                level = 0
            val pos = 100 * level / (max_level - min_level) + 50
            sliders[i].setProgress(pos)
        }
    }

    /*=============================================================================
    updateBassBoost
=============================================================================*/
    fun updateBassBoost() {
        if (bb != null)
            bass_boost!!.progress = bb!!.roundedStrength.toInt()
        else
            bass_boost!!.progress = 0
    }

    /*=============================================================================
    onCheckedChange
=============================================================================*/
    override fun onCheckedChanged(view: CompoundButton, isChecked: Boolean) {
        if (view === enabled as View?) {
            eq!!.enabled = isChecked
        }
    }

    /*=============================================================================
    onClick
=============================================================================*/
    override fun onClick(view: View) {
        if (view === flat as View?) {
            setFlat()
        }
    }

    /*=============================================================================
    updateUI
=============================================================================*/
    fun updateUI() {
        updateSliders()
        updateBassBoost()
        enabled!!.isChecked = eq!!.enabled
    }

    /*=============================================================================
    setFlat
=============================================================================*/
    fun setFlat() {
        if (eq != null) {
            for (i in 0 until num_sliders) {
                eq!!.setBandLevel(i.toShort(), 0.toShort())
            }
        }

        if (bb != null) {
            bb!!.enabled = false
            bb!!.setStrength(0.toShort())
        }

        updateUI()
    }

    /*=============================================================================
    showAbout
=============================================================================*/
    fun showAbout() {
        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("About Simple EQ")
        alertDialogBuilder.setMessage(R.string.copyright_message)
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.setPositiveButton(R.string.ok
        ) { dialog, id -> }
        val ad = alertDialogBuilder.create()
        ad.show()

    }

    /*=============================================================================
    onOptionsItemSelected
=============================================================================*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                showAbout()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        internal val MAX_SLIDERS = 8 // Must match the XML layout
    }
}


