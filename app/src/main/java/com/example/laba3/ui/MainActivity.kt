package com.example.laba3.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.laba3.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    private val textViewStudentFio by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.textViewStudentFio) }
    private val textViewQuestion by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.textViewQuestion) }
    private val editTextWord by lazy(LazyThreadSafetyMode.NONE) { findViewById<EditText>(R.id.editTextWord) }
    private val linearLayoutButtons by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<LinearLayout>(
            R.id.linearLayoutButtons
        )
    }
    private val buttonDefinition by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.buttonDefinition) }
    private val buttonFeedback by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.buttonFeedback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            textViewStudentFio.typeWriterEffect(getEditedFio(), 50L)
            textViewQuestion.typeWriterEffect(getEditedQuestion(), 50L)

            buttonDefinition.setOnClickListener {
                val text = editTextWord.text
                if (text.isBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Enter text, please",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    startActivity(GetDefinitionActivity.getIntent(this@MainActivity, text.toString()))
                }
            }

            buttonFeedback.setOnClickListener {
                startActivity(FeedbacksPageActivity.getIntent(this@MainActivity))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "onDestroy")
    }

    private fun getEditedQuestion(): SpannableString {
        val text = getString(R.string.student_question)
        return SpannableString(text)
//            .apply {
//                val str = "${getString(R.string.student_question_second_part)}"
//                val start = text.indexOf(str)
//                val end = start + str.length
//                setSpan(
////                    UnderlineSpan(),
////                    ForegroundColorSpan(Color.rgb(222, 205, 97)),
//                    StyleSpan(Typeface.BOLD_ITALIC),
//                    start,
//                    end,
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//                )
//            }
    }

    private fun getEditedFio(): SpannableString {
        val text = getString(R.string.student_fio)
        return SpannableString(text)
            .apply {
                val str = " ${getString(R.string.student_fio_second_part)}"
                val start = text.indexOf(str)
                val end = start + str.length
                setSpan(
                    ForegroundColorSpan(Color.rgb(222, 205, 97)),
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
    }

    private suspend fun TextView.typeWriterEffect(
        text: SpannableString,
        delayMillis: Long = 150L
    ) {
        withContext(Dispatchers.Main) {
            for (i in 1..text.length) {
                this@typeWriterEffect.text = text.subSequence(0, i)
                delay(delayMillis)
            }
        }
    }
}