package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.certiface.domain.callback.CertifaceResultCallback
import br.com.certiface.manager.exports.LivenessResult

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
