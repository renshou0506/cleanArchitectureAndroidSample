package com.example.learningcleanarchitecture.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context
) :ViewModel(){
    // drawerにさせたいロジックがあればこちらに.
}
