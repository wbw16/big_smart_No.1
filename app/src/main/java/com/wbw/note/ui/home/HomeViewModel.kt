package com.wbw.note.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wbw.note.util.FileUtil

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    private val _fileList= MutableLiveData<List<String>>().apply {
        value = listOf()
    }
    val text: LiveData<String> = _text
    val fileList: LiveData<List<String>> = _fileList

    fun getFileName(): LiveData<List<String>> {
        // fake data by listof
        _fileList.value = FileUtil.fileName
        return fileList
    }
}