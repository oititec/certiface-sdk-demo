package br.com.certiface.certifacesdk.demo.strategy

import android.content.Context
import br.com.oiti.domain.callback.CertifaceResultCallback
import br.com.oiti.facetecsdk.domain.model.FacetecManagerOptions
import br.com.oiti.manager.exports.LivenessResult
import br.com.oiti.manager.main.CertifaceSDK
import br.com.oiti.manager.main.LivenessProvider
import br.com.certiface.certifacesdk.demo.factories.FacetecThemeFactory

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
        val opts = FacetecManagerOptions(appKey, theme)
        val mgr = CertifaceSDK.createLivenessManager(LivenessProvider.FACETEC)
        mgr.start(opts, callback)
    }
}
