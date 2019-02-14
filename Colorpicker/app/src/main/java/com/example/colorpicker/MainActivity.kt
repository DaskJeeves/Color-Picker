package com.example.colorpicker

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var redCurrent = 255
    var blueCurrent = 255
    var greenCurrent = 255

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val redSlider = findViewById<SeekBar>(R.id.red_slider)
        redSlider?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val redHex = findViewById<TextView>(R.id.red_hex)
                redCurrent = progress
                redHex.text = progress.toString()
                setBackgroundColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(this@MainActivity, "Red is " + seekBar.progress, Toast.LENGTH_SHORT).show()
            }
        })


        val greenSlider = findViewById<SeekBar>(R.id.green_slider)
        greenSlider?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val greenHex = findViewById<TextView>(R.id.green_hex)
                greenCurrent = progress
                greenHex.text = progress.toString()
                setBackgroundColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(this@MainActivity, "Green is " + seekBar.progress, Toast.LENGTH_SHORT).show()
            }
        })



        val blueSlider = findViewById<SeekBar>(R.id.blue_slider)
        blueSlider?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val blueHex = findViewById<TextView>(R.id.blue_hex)
                blueCurrent = progress
                blueHex.text = progress.toString()
                setBackgroundColor()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                Toast.makeText(this@MainActivity, "Blue is " + seekBar.progress, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setBackgroundColor(){
        val bgText = findViewById<TextView>(R.id.background_color)
        bgText.setBackgroundColor(Color.parseColor(getColorString()))
        val hex_value = findViewById<TextView>(R.id.hex_value)
        hex_value.text = getColorString()
    }


    fun getColorString(): String {
        var r = Integer.toHexString(redCurrent)
        if(r.length==1) r = "0"+r
        var g = Integer.toHexString(greenCurrent)
        if(g.length==1) g = "0"+g
        var b = Integer.toHexString(blueCurrent)
        if(b.length==1) b = "0"+b
        return "#" + r + g + b
    }
}
