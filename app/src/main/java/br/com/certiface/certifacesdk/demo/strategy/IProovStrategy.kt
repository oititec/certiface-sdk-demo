package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.certiface.certifacesdk.demo.factories.IProovThemeFactory
import br.com.certiface.domain.callback.CertifaceResultCallback
import br.com.certiface.manager.exports.LivenessResult
import br.com.certiface.manager.main.CertifaceSDK
import br.com.certiface.manager.main.LivenessProvider

class IProovStrategy: LivenessProviderStrategy {
    override fun start(
        context: Context,
        appKey: String,
        isCustom: Boolean,
        useCustomView: Boolean,
        showInstructionScreen: Boolean,
        callback: CertifaceResultCallback<LivenessResult>
    ) {
        val theme = IProovThemeFactory.create(isCustom, useCustomView, showInstructionScreen)
        val opts  = br.com.certiface.manager.exports.IProovManagerOptions(appKey, theme)
        val manager = CertifaceSDK.createLivenessManager(LivenessProvider.IPROOV)
        manager.start(opts, callback)
    }
}
