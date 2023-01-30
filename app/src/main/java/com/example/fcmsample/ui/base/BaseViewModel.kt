package com.example.fcmsample.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class BaseViewModel : ViewModel() {
    protected val logTag = javaClass.simpleName
    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected val backPressedSubject = BehaviorSubject.createDefault(0L) // 생성할 때는 0을 넣는다
    protected val _loading = MutableLiveData<Boolean>().apply {
        postValue(false)
    }
    protected var _error = MutableLiveData<String>()
    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error


    protected val _hideKeyBoardEvent = SingleLiveEvent<Any>()
    protected val _closeEvent = SingleLiveEvent<String>()




    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun showProgress() {
        _loading.value = true
    }

    fun hideProgress() {
        _loading.value = false
    }


}

