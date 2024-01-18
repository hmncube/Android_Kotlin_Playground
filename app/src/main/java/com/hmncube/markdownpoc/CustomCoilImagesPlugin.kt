package com.hmncube.markdownpoc

import android.content.Context
import coil.Coil.imageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Precision
import io.noties.markwon.image.AsyncDrawable
import io.noties.markwon.image.coil.CoilImagesPlugin
import io.noties.markwon.image.coil.CoilImagesPlugin.CoilStore

class CustomCoilImagesPlugin {
    companion object {
        fun create(context: Context): CoilImagesPlugin {
            return CoilImagesPlugin.create(object : CoilStore {
                override fun load(drawable: AsyncDrawable): ImageRequest {
                    return ImageRequest.Builder(context)
                        .data(drawable.destination)
                        .precision(Precision.EXACT)
                        .build()
                }
                override fun cancel(disposable: Disposable) {
                    disposable.dispose()
                }
            }, imageLoader(context))
        }

    }
}