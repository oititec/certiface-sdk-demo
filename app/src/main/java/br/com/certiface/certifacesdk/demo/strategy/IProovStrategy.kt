package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.oiti.domain.callback.CertifaceResultCallback
import br.com.oiti.iproov.domain.model.IProovManagerOptions
import br.com.oiti.manager.exports.LivenessResult
import br.com.oiti.manager.main.CertifaceSDK
import br.com.oiti.manager.main.LivenessProvider
import br.com.certiface.certifacesdk.demo.factories.IProovThemeFactory

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
        val opts  = IProovManagerOptions(appKey, theme)
        val manager = CertifaceSDK.createLivenessManager(LivenessProvider.IPROOV)
        manager.start(opts, callback)
    }
}
