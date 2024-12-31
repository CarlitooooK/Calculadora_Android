package com.example.designui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    //Declaracion de variables
    private lateinit var ButtonPoint: Button
    private lateinit var ButtonZero: Button
    private lateinit var ButtonOne: Button
    private lateinit var ButtonTwo: Button
    private lateinit var ButtonThree: Button
    private lateinit var ButtonFour: Button
    private lateinit var ButtonFive: Button
    private lateinit var ButtonSix: Button
    private lateinit var ButtonSeven: Button
    private lateinit var ButtonEight: Button
    private lateinit var ButtonNine: Button
    private lateinit var ButtonPlus: Button
    private lateinit var ButtonMinus: Button
    private lateinit var ButtonMultiply: Button
    private lateinit var ButtonDivide: Button
    private lateinit var ButtonModulo: Button
    private lateinit var ButtonClear: Button
    private lateinit var ButtonEquals: Button
    private lateinit var resultTextView: TextView
    private lateinit var postResult: TextView
    private lateinit var eraseButton: Button

    private var currentExpression:String = " "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = resources.getColor(R.color.colorStatusBar)


        eraseButton = findViewById(R.id.returnSymbol)
        postResult = findViewById(R.id.postResult)
        resultTextView = findViewById(R.id.resultTextView)
        ButtonPoint = findViewById(R.id.pointSymbol)
        ButtonZero = findViewById(R.id.zeroSymbol)
        ButtonOne = findViewById(R.id.numberOne)
        ButtonTwo = findViewById(R.id.numberTwo)
        ButtonThree = findViewById(R.id.numberThree)
        ButtonFour = findViewById(R.id.numberFour)
        ButtonFive = findViewById(R.id.numberFive)
        ButtonSix = findViewById(R.id.numberSix)
        ButtonSeven = findViewById(R.id.numberSeven)
        ButtonEight = findViewById(R.id.numberEight)
        ButtonNine = findViewById(R.id.numberNine)
        ButtonPlus = findViewById(R.id.plusOperator)
        ButtonMinus = findViewById(R.id.restOperator)
        ButtonMultiply = findViewById(R.id.multiplicationOperator)
        ButtonDivide = findViewById(R.id.divOperator)
        ButtonClear = findViewById(R.id.buttonAC)
        ButtonModulo = findViewById(R.id.percentageSymbol)
        ButtonEquals = findViewById(R.id.equalOperator)

        ButtonPoint.setOnClickListener {appendToExpression(".")}
        ButtonZero.setOnClickListener {appendToExpression("0")}
        ButtonOne.setOnClickListener { appendToExpression("1") }
        ButtonTwo.setOnClickListener { appendToExpression("2") }
        ButtonThree.setOnClickListener { appendToExpression("3") }
        ButtonFour.setOnClickListener { appendToExpression("4") }
        ButtonFive.setOnClickListener { appendToExpression("5") }
        ButtonSix.setOnClickListener { appendToExpression("6") }
        ButtonSeven.setOnClickListener { appendToExpression("7") }
        ButtonEight.setOnClickListener { appendToExpression("8") }
        ButtonNine.setOnClickListener { appendToExpression("9") }


        ButtonPlus.setOnClickListener { appendToExpression("+") }
        ButtonMinus.setOnClickListener { appendToExpression("-") }
        ButtonMultiply.setOnClickListener { appendToExpression("*") }
        ButtonDivide.setOnClickListener { appendToExpression("/") }
        ButtonModulo.setOnClickListener { appendToExpression("%") }
        eraseButton.setOnClickListener {deleteLastCharacter()}
        ButtonClear.setOnClickListener { appendToExpression("")
            postResult.text = ""
        }

        ButtonEquals.setOnClickListener { evaluateExpression() }


    }

    private fun deleteLastCharacter() {
        if (currentExpression.isNotEmpty()) {
            currentExpression = currentExpression.dropLast(1) // Elimina el último carácter
            resultTextView.text = currentExpression
        }
    }

    private fun appendToExpression(value:String){
        if(value==""){
            currentExpression = ""
        }
        currentExpression += value
        resultTextView.text = currentExpression
    }

    @SuppressLint("SetTextI18n")
    private fun evaluateExpression() {
        try {
            // Llama a la función personalizada para evaluar
            val result = calculateResult(currentExpression)

            // Verifica si el resultado es un número entero o decimal
            val displayResult = if (result % 1 == 0.0) {
                result.toInt().toString() // Elimina el .0 convirtiendo a entero
            } else {
                result.toString() // Muestra como decimal si es necesario
            }

            resultTextView.text = displayResult
            postResult.text = currentExpression
            currentExpression = displayResult // Actualiza con el resultado
        } catch (e: Exception) {
            resultTextView.text = "Error"
            currentExpression = "" // Reinicia la expresión en caso de error
        }
    }

    // Función personalizada para evaluar expresiones simples
    private fun calculateResult(expression: String): Double {
        // Usa una expresión regular para dividir la cadena en números y operadores
        val tokens = Regex("([+\\-*/])|\\d+(\\.\\d+)?").findAll(expression).map { it.value }.toList()
        expression.toList()

        if (tokens.isEmpty()) throw IllegalArgumentException("Expresión inválida")

        var result = tokens[0].toDouble() // El primer número
        var currentOperator = ""

        for (i in 1 until tokens.size) {
            val token = tokens[i]

            if (token in "+-*/%") {
                currentOperator = token // Guarda el operador actual
            } else {
                // Realiza la operación con el número actual
                val number = token.toDouble()
                result = when (currentOperator) {
                    "+" -> result + number
                    "-" -> result - number
                    "*" -> result * number
                    "/" -> result / number
                    "%" -> (result / 100) * number
                    else -> result
                }
            }
        }
        return result
    }




}