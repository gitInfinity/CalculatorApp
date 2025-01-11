package com.example.myapplication.ui.theme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel: ViewModel() {
    private val _equationText = MutableLiveData("")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData("0")
    val resultText: LiveData<String> = _resultText

    fun onClickButton(btton: String){
        _equationText.value?.let{
            if(btton == "AC"){
                _equationText.value = ""
                _resultText.value = "0"
                return
            }
            if(btton == "C"){
                if(it.isNotEmpty()){
                    _equationText.value = it.substring(0, it.length - 1)
                    return
                }
            }
            if(btton == "="){
                _equationText.value = _resultText.value
                return
            }
            _equationText.value = it + btton
        }
        return try {
             _resultText.value = calcResult(_equationText.value.toString())
        }
        catch (exc: Exception) {
        }
    }

    fun calcResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        val final = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        return final
    }
}