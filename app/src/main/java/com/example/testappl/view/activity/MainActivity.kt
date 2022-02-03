package com.example.testappl.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.testappl.App
import com.example.testappl.R
import com.example.testappl.view.fragment.MainFragment
class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).appComponent.inject(this)
        setContentView(R.layout.activity_main)
        initFragments()
        //there are no fragment in the task, but let it be
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_layout, mainFragment)
            .commit()

    }

    private fun initFragments() {
        mainFragment = MainFragment()
    }
}