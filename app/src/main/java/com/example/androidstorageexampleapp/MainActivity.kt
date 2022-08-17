package com.example.androidstorageexampleapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val themeSwitch: Switch = findViewById(R.id.theme_switch)
        val currentTheme = getCurrentTheme()
        //set current state to theme switcher button
        themeSwitch.isChecked = currentTheme
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->  changeTheme() }

        val writeBtn: Button = findViewById(R.id.write_button)
        val readBtn: Button = findViewById(R.id.read_button)
        val clearBtn: Button = findViewById(R.id.clear_button)

        val messageTv: TextView = findViewById(R.id.message_tv)

        writeBtn.setOnClickListener { writeData("Hello world!") }
        readBtn.setOnClickListener {
            messageTv.text = readData()
        }
        clearBtn.setOnClickListener { clear() }
    }

    /**
     * Implementation for SharedPreferences
     */
    fun changeTheme() {
        val darkModeEnabled = getCurrentTheme()

        val sharedPref: SharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        var newTheme: Boolean
        newTheme = !darkModeEnabled

        editor.putBoolean("theme", newTheme)
        editor.apply()
        Log.i("SORAGE EG", "Theme changed!")
    }

    fun getCurrentTheme(): Boolean {
        val sharedPref: SharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("theme", false)
    }


    /**
     * Implementations for reading/writing internal storage
     */

    fun writeData(message: String) {
        try {
            val fileOutputStream: FileOutputStream = openFileOutput("hello.txt", Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            outputStreamWriter.write(message)
            outputStreamWriter.close()
        } catch (e: Exception) {
            Log.i("FILE_WRITE", e.message.toString())
        }
    }

    fun clear() {
        writeData("")
    }

    fun readData(): String {
        var message = ""
        try {
            val fileInputStream: FileInputStream = openFileInput("hello.txt")
            val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)

            message = inputStreamReader.readText()

        } catch (e: Exception) {
            Log.i("FILE_Read", e.message.toString())
        }

        return message
    }
}