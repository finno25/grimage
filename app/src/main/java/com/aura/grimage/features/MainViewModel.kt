package com.aura.grimage.features

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aura.co99.networks.ImagesResponses
import com.aura.co99.networks.RetrofitService
import com.aura.grimage.models.Hit
import com.aura.grimage.netwokrs.CallbackWrapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class MainViewModel : ViewModel() {
    private val mutableLiveData = MutableLiveData<Event>()
    val event: LiveData<Event> = mutableLiveData
    private var loading: Boolean = false
    private var page = 1

    fun getImages() {
        if (!loading) {
            loading = true
            mutableLiveData.value = Event(loading = loading)
            RetrofitService.instance!!.getApiInterface().getImages(
                "15872222-38736d18a6d20ad24cd94c5b3",
                page,
                "",
                true
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<ImagesResponses>() {
                    override fun onSuccess(t: ImagesResponses) {
                        loading = false
                        mutableLiveData.value =
                            Event(loading = loading, hits = t.hits as ArrayList<Hit>)
                        page++
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        loading = false
                    }
                })
        }
    }

    data class Event(
        var loading: Boolean = false,
        var hits: ArrayList<Hit>? = null
    )
}