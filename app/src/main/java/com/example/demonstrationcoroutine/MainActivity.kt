package com.example.demonstrationcoroutine

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var numberInputX: EditText
    private lateinit var numberInputY: EditText
    private lateinit var startButton: Button
    private lateinit var mainLayout: LinearLayout
    private lateinit var redCountdownText: TextView
    private lateinit var blueCountdownText: TextView

    private var coroutineJob1: Job? = null
    private var coroutineJob2: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberInputX = findViewById(R.id.numberInputX)
        numberInputY = findViewById(R.id.numberInputY)
        startButton = findViewById(R.id.startButton)
        mainLayout = findViewById(R.id.mainLayout)
        redCountdownText = findViewById(R.id.redCountdownText)
        blueCountdownText = findViewById(R.id.blueCountdownText)

        startButton.setOnClickListener {
            val xValue = numberInputX.text.toString().toLongOrNull() ?: return@setOnClickListener
            val yValue = numberInputY.text.toString().toLongOrNull() ?: return@setOnClickListener

            coroutineJob1?.cancel()
            coroutineJob2?.cancel()

            // Coroutine for Blue
            coroutineJob1 = CoroutineScope(Dispatchers.Main).launch {
                var blueCountdown = xValue
                while (isActive) {
                    blueCountdownText.text = "Blue: $blueCountdown"
                    delay(1000L)
                    blueCountdown--
                    if (blueCountdown < 0) {
                        blueCountdown = xValue
                    }
                    else {
                        if (blueCountdown.toInt() == 0){
                            mainLayout.setBackgroundColor(Color.BLUE)
                        }
                    }
                }
            }

            // Coroutine for Red
            coroutineJob2 = CoroutineScope(Dispatchers.Main).launch {
                var redCountdown = yValue
                while (isActive) {
                    redCountdownText.text = "Red: $redCountdown"
                    delay(1000L)
                    redCountdown--
                    if (redCountdown < 0) {
                        redCountdown = yValue
                    }
                    else {
                        if (redCountdown.toInt() == 0){
                            mainLayout.setBackgroundColor(Color.RED)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineJob1?.cancel()
        coroutineJob2?.cancel()
    }
}
