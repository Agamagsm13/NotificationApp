package com.example.testappl.di

import android.content.Context
import com.example.testappl.view.activity.MainActivity
import com.example.testappl.view.activity.WebActivity
import com.example.testappl.view.fragment.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(webActivity: WebActivity)
    fun inject(weatherFragment: MainFragment)
}