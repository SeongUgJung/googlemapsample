package com.seongugjung.googlemap.sample.base.extensions

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.Observable

fun ObservableBoolean.asObservable(): Observable<Boolean> {
    return Observable.create { emitter ->
        emitter.onNext(this.get())

        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                emitter.onNext((sender as ObservableBoolean).get())
            }
        }
        addOnPropertyChangedCallback(callback)

        emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}

fun <T> ObservableField<T>.asObservable(): Observable<T> {
    return Observable.create { emitter ->
        this.get()?.let(emitter::onNext)


        val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                sender: androidx.databinding.Observable?,
                propertyId: Int
            ) {
                (sender as ObservableField<T>).get()?.let(emitter::onNext)

            }
        }
        addOnPropertyChangedCallback(callback)

        emitter.setCancellable { removeOnPropertyChangedCallback(callback) }
    }
}