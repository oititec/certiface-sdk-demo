package br.com.certiface.certifacesdk.demo

import android.content.Context
import br.com.certiface.certifacesdk.demo.strategy.FacetecStrategy
import br.com.certiface.certifacesdk.demo.strategy.IProovStrategy
import br.com.certiface.certifacesdk.demo.strategy.LivenessProviderStrategy
import br.com.certiface.certifacesdk.demo.domain.model.Features
import br.com.certiface.domain.liveness.LivenessResponse
import br.com.certiface.domain.model.ErrorResponse
import br.com.certiface.manager.exports.Environment
import br.com.certiface.manager.exports.LivenessResult
import br.com.certiface.manager.exports.ResultCallback
import br.com.certiface.manager.main.CertifaceSDK

class LivenessExecutor(val appkey: String, val feature: Features) {

    private val strategies: Map<Features, LivenessProviderStrategy> = mapOf(
        Features.Facetec to FacetecStrategy(),
        Features.IProov to IProovStrategy()
    )

    fun executeLiveness(
        context: Context,
        execOnSuccess: (LivenessResult?) -> Unit,
        execOnError: (ErrorResponse?) -> Unit,
        isCustomEnabled: Boolean = false,
        useCustomView: Boolean = false,
        showInstructionScreen: Boolean = true
    ) {
        CertifaceSDK.initialize(
            context,
            br.com.certiface.manager.exports.SDKConfig(
                environment = Environment.HML,
                appKey = appkey
            )
        )

        val strategy = strategies[feature]
            ?: error("Nenhuma strategy pra feature $feature")

        val callback = object : ResultCallback<LivenessResult> {
            override fun onSuccess(result: LivenessResponse) {
                execOnSuccess(result.livenessResult)
            }

            override fun onError(result: LivenessResponse) {
                execOnError(result.errorResponse)
            }
        }
        strategy.start(context, appkey, isCustomEnabled, useCustomView, showInstructionScreen, callback)
    }
}
