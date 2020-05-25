package com.example.kinandcartachallenge.di.component

import com.example.kinandcartachallenge.activity.main.MainViewModel
import dagger.Subcomponent

@Subcomponent
interface MainActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }


    fun inject(mainViewModel: MainViewModel)
}