package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.oiti.domain.callback.CertifaceResultCallback
import br.com.oiti.manager.exports.LivenessResult

interface LivenessProviderStrategy {
    fun start(
        context: Context,
        appKey: String,
        isCustom: Boolean,
        useCustomView: Boolean = false,
        showInstructionScreen: Boolean = true,
        callback: CertifaceResultCallback<LivenessResult>
    )
}
