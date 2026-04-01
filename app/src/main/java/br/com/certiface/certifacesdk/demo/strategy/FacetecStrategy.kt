package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.certiface.certifacesdk.demo.factories.FacetecThemeFactory
import br.com.certiface.domain.callback.CertifaceResultCallback
import br.com.certiface.manager.exports.LivenessResult
import br.com.certiface.manager.main.CertifaceSDK
import br.com.certiface.manager.main.LivenessProvider

class FacetecStrategy : LivenessProviderStrategy {
    override fun start(
        context: Context,
        appKey: String,
        isCustom: Boolean,
        useCustomView: Boolean,
        showInstructionScreen: Boolean,
        callback: CertifaceResultCallback<LivenessResult>
    ) {
        val theme = FacetecThemeFactory.create(isCustom, useCustomView, showInstructionScreen)
        val opts = br.com.certiface.manager.exports.FacetecManagerOptions(appKey, theme)
        val mgr = CertifaceSDK.createLivenessManager(LivenessProvider.FACETEC)
        mgr.start(opts, callback)
    }
}
