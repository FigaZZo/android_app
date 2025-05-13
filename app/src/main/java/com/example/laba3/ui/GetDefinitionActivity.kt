package com.example.laba3.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.laba3.BuildConfig
import com.example.laba3.R
import com.example.laba3.viewmodel.DefinitonViewModel

class GetDefinitionActivity : AppCompatActivity() {
    companion object {
        private const val WORD = "word"

        fun getIntent(context: Context, word: String) =
            Intent(context, GetDefinitionActivity::class.java).apply {
                putExtra(WORD, word)
            }
    }

    private val textViewDefinition by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.textViewDefinition) }
    private val buttonFeedback by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.buttonFeedback) }
    private val viewModel by viewModels<DefinitonViewModel> {
        viewModelFactory {
            initializer {
                DefinitonViewModel()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_definition)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.getDefinition)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val word = intent.getStringExtra(WORD)
        viewModel.definition.observe(this) { definition ->
            textViewDefinition.text = definition
        }
        word?.let {
            viewModel.searchWord(it)
        }

        buttonFeedback.setOnClickListener {
            startActivity(AddFeedbackActivity.getIntent(this))
        }
    }
}