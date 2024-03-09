package com.michel.productsapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.michel.productsapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.v("APP", "MainActivity creating");
    }

    override fun onStart() {
        super.onStart()
        Log.v("APP", "MainActivity starting");
    }

    override fun onResume() {
        super.onResume()
        Log.v("APP", "MainActivity starting");

    }

    override fun onPause() {
        super.onPause()
        Log.v("APP", "MainActivity pausing");

    }

    override fun onStop() {
        super.onStop()
        Log.v("APP", "MainActivity stopping");

    }

}
