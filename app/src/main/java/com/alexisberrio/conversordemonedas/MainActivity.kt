/*
Currency converter
Created by:
            Alexis Berrio Arenas
            Dario Fernando Arévalo
 */
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

        // Initialize variables with values from both spinners
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

        // Update values in Text View Out when input in Edit Text changes
        conversor_editTextNumberDecimal.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            // When input on Edit Text changes, do the following
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                // numToConvert = actual value from Edit Text or 0.0 when is Empty, without this
                // conditional, app crashes because it can't convert to String a null value
                numToConvert = if (s.isEmpty()) 0.0
                else s.toString().toDouble()
                // Make a call to function that displays the actualized value
                convertValor()
            }
        })

    }

    // Update values from spinners when detect a change
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        value1 = conversor1_spinner.selectedItem.toString()
        value2 = conversor2_spinner.selectedItem.toString()

        // Make a call to function that displays the actualized value
        convertValor()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) = Unit

    // Depending on spinners and Edit text values, this function makes the current conversion
    fun convertValor() {
        when (value1) {
            "COP" -> {
                moneda_in_textView.text = getString(R.string.peso)
                when (value2) {
                    "USD" -> {
                        numConverted = numToConvert * copToUsd
                        moneda_out_textView.text = getString(R.string.dolar)
                    }
                    "EUR" -> {
                        numConverted = numToConvert * copToEur
                        moneda_out_textView.text = getString(R.string.euro)
                    }
                    else -> {
                        numConverted = numToConvert
                        moneda_out_textView.text = getString(R.string.peso)
                    }
                }
            }
            "USD" -> {
                moneda_in_textView.text = getString(R.string.dolar)
                when (value2) {
                    "COP" -> {
                        numConverted = numToConvert * usdToCop
                        moneda_out_textView.text = getString(R.string.peso)
                    }
                    "EUR" -> {
                        numConverted = numToConvert * usdToEur
                        moneda_out_textView.text = getString(R.string.euro)
                    }
                    else -> {
                        numConverted = numToConvert
                        moneda_out_textView.text = getString(R.string.dolar)
                    }
                }
            }
            "EUR" -> {
                moneda_in_textView.text = getString(R.string.euro)
                when (value2) {
                    "COP" -> {
                        numConverted = numToConvert * eurToCop
                        moneda_out_textView.text = getString(R.string.peso)
                    }
                    "USD" -> {
                        numConverted = numToConvert * eurToUsd
                        moneda_out_textView.text = getString(R.string.dolar)
                    }
                    else -> {
                        numConverted = numToConvert
                        moneda_out_textView.text = getString(R.string.euro)
                    }
                }
            }
        }
        // Limit decimal format to 4
        numConverted = "%.4f".format(numConverted).toDouble()
        conversor_out_textView.text = numConverted.toString()
    }

}

