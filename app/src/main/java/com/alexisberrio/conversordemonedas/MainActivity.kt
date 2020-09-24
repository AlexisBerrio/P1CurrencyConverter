package com.alexisberrio.conversordemonedas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        private const val copToUsd = 0.0003
        private const val copToEur = 0.0002
        private const val usdToEur = 0.8574
        private const val usdToCop = 3862.18
        private const val eurToCop = 4503.0888
        private const val eurToUsd = 1.1663
    }

    private var value1 = ""
    private var value2 = ""
    private var numConverted = 0.0
    private var numToConvert = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        value1 = conversor1_spinner.selectedItem.toString()
        value2 = conversor2_spinner.selectedItem.toString()


        // Make adapterView
        val aa =
            ArrayAdapter.createFromResource(this, R.array.moneda1, R.layout.spinner_text_size)
        aa.setDropDownViewResource(R.layout.spinner_text_size)

        // Customize spinner 1- First input
        with(conversor1_spinner)
        {
            adapter = aa
            setSelection(0)
            onItemSelectedListener = this@MainActivity
        }

        // Customize spinner 2 - Second input
        with(conversor2_spinner)
        {
            adapter = aa
            setSelection(1)
            onItemSelectedListener = this@MainActivity
        }

        // Update values in Text View Out when input changes
        conversor_editTextNumberDecimal.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                numToConvert = s.toString().toDouble()
                convertValor()
            }
        })

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        value1 = conversor1_spinner.selectedItem.toString()
        value2 = conversor2_spinner.selectedItem.toString()
        convertValor()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) = Unit

    fun convertValor() {
        when (value1) {
            "COP" -> {
                numConverted = when (value2) {
                    "USD" -> numToConvert * copToUsd
                    "EUR" -> numToConvert * copToEur
                    else -> numToConvert
                }
            }
            "USD" -> {
                numConverted = when (value2) {
                    "COP" -> numToConvert * usdToCop
                    "EUR" -> numToConvert * usdToEur
                    else -> numToConvert
                }
            }
            "EUR" -> {
                numConverted = when (value2) {
                    "COP" -> numToConvert * eurToCop
                    "USD" -> numToConvert * eurToUsd
                    else -> numToConvert
                }
            }

        }
        conversor_out_textView.text = numConverted.toString()
    }
}

