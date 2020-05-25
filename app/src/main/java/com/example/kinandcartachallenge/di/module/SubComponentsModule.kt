package com.example.kinandcartachallenge.di.module

import com.example.kinandcartachallenge.di.component.MainActivityComponent
import dagger.Module

@Module(subcomponents = arrayOf(MainActivityComponent::class))
class SubComponentsModule {}