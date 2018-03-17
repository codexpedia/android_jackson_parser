package com.example.jacksonparser

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun jsonToObject(view : View) {
        val mapper = ObjectMapper()
        try {
            val personJsonStr = "{\"firstname\":\"John\",\"lastname\":\"Doe\"}"
            //Person person = mapper.readValue(new File(getFilesDir(), "person.json"), Person.class);    // read from file
            val person = mapper.readValue(personJsonStr, Person::class.java)                               // read from json string
            tv_display.text = "json string -> object\n" + person.firstname + " " + person.lastname
        } catch (e: IOException) {
            e.printStackTrace()
            tv_display.text = e.message
            progress_bar.visibility = View.GONE
        }

    }

    fun objectToJson(view : View) {
        val mapper = ObjectMapper()
        try {
            //            mapper.writeValue(new File(getFilesDir(), "person.json"), person);  // write to file
            val person = Person("John", "Doe")
            val jsonStr = mapper.writeValueAsString(person)                 // write to string
            tv_display.text = "object -> json string\n" + jsonStr
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            tv_display.text = e.message
        } catch (e: IOException) {
            e.printStackTrace()
            tv_display.text = e.message
        }

    }

    private fun jsonToObjectUrl() {
        val mapper = ObjectMapper()
        val handler = Handler()
        Thread(Runnable {
            try {
                val person = mapper.readValue(URL("https://api.myjson.com/bins/hoh4j"), Person::class.java)                                 // read from url
                handler.post { tv_display.text = "json string -> object\n" + person!!.firstname + " " + person!!.lastname }
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }

}
