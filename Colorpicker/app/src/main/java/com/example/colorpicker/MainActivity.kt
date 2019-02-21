package com.example.colorpicker

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import java.io.File
import android.R.string.cancel
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.DialogInterface
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.text.InputType
import android.widget.EditText



class MainActivity : AppCompatActivity() {
    var redCurrent = 255
    var blueCurrent = 255
    var greenCurrent = 255
    private var saveName = ""
    var itemSelected = false
    val CHANNEL_ID = "com.chikeandroid.tutsplustalerts.ANDROID"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()



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


        val spinner: Spinner = this.findViewById(R.id.favorite_colors)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val spin_array = ArrayAdapter.createFromResource(
            this,
            R.array.default_list,
            R.layout.custom_spinner
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.custom_spinner)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        setFavoriteColorList()
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =
                if(spinner.selectedItem.toString() == "Clear Favorites"){
                    clearFavorites()
                }else {
                    if (!itemSelected) {
                        itemSelected = true
                    } else {
                        val rgb = spinner.selectedItem.toString().replace(")", "").replace(" ", "").split("(")
                        if(rgb.size > 2){

                            val redSlider = findViewById<SeekBar>(R.id.red_slider)
                            redSlider.setProgress(rgb[2].toInt())
                            redCurrent = rgb[2].toInt()
                            val greenSlider = findViewById<SeekBar>(R.id.green_slider)
                            greenSlider.setProgress(rgb[3].toInt())
                            greenCurrent = rgb[3].toInt()
                            val blueSlider = findViewById<SeekBar>(R.id.blue_slider)
                            blueSlider.setProgress(rgb[4].toInt())
                            blueCurrent = rgb[4].toInt()
                            Log.i("ON ITEM SELECT", "$redCurrent,$greenCurrent,$blueCurrent")
                            setBackgroundColor()
                        } else{

                        }
                    }
                }

        }

    }

    fun setBackgroundColor(){
        val bgText = findViewById<TextView>(R.id.background_color)
        bgText.setBackgroundColor(Color.parseColor(getColorString()))
        val hex_value = findViewById<TextView>(R.id.hex_value)
        hex_value.text = getColorString()
    }


    private fun getColorString(): String {
        var r = Integer.toHexString(redCurrent)
        if(r.length==1) r = "0"+r
        var g = Integer.toHexString(greenCurrent)
        if(g.length==1) g = "0"+g
        var b = Integer.toHexString(blueCurrent)
        if(b.length==1) b = "0"+b
        return "#$r$g$b"
    }

    fun showSaveDialog(view: View){
        itemSelected = false
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose Name For Saved Color")

        // Set up the input
        val input = EditText(this)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("SAVE",
            DialogInterface.OnClickListener {
                    dialog, which -> saveName = input.text.toString()
                    saveColor()
            })
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun saveColor(){
        Toast.makeText(this@MainActivity, "Color Saved", Toast.LENGTH_LONG).show()
        val fileContents = "($saveName)($redCurrent)($greenCurrent)($blueCurrent)"
        val file = getFile()
        file.appendText("$fileContents&&&")
        var favoriteColors = ""
        file.forEachLine {favoriteColors += it}
        favoriteColors += "Clear Favorites"

        val arrayAdapter = ArrayAdapter(this, R.layout.custom_spinner, favoriteColors.split("&&&"))
        val spinner: Spinner = findViewById(R.id.favorite_colors)
        spinner.adapter = arrayAdapter
        Log.i("SAVE COLOR", "$redCurrent,$greenCurrent,$blueCurrent")
        itemSelected = false

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.color_picker_logo)
            .setContentTitle("Color Saved")
            .setContentText("You created the color $saveName")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private fun getFile(): File {
        val filename = "color_file"
        return File(filesDir, filename)
    }

    private fun setFavoriteColorList(){
        val file = getFile()

        var favoriteColors = ""

        if(file.isFile){
            file.forEachLine {favoriteColors += it}
        }
        favoriteColors += "Clear Favorites"

        val arrayAdapter = ArrayAdapter(this, R.layout.custom_spinner, favoriteColors.split("&&&"))
        val spinner: Spinner = findViewById(R.id.favorite_colors)
        spinner.adapter = arrayAdapter
    }

    fun clearFavorites(){
        val file = getFile()
        file.writeText("")
        setFavoriteColorList()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
