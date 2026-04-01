package br.com.certiface.certifacesdk.demo

import android.content.Context
import br.com.certiface.doc.domain.models.CertifaceDocResponse
import br.com.certiface.doc.domain.models.CertifaceDocResult
import br.com.certiface.doc.manager.exports.createSDKConfig
import br.com.certiface.doc.manager.main.CertifaceDocSDK
import br.com.certiface.certifacesdk.demo.factories.DocumentThemeFactory
import br.com.certiface.doc.manager.exports.Environment
import br.com.certiface.doc.manager.exports.ResultCallback
import br.com.certiface.domain.model.ErrorResponse
import br.com.certiface.domain.model.LivenessErrorType

class DocumentExecutor(private val appKey: String) {

    fun executeDocument(
        context: Context,
        execOnSuccess: (CertifaceDocResult?) -> Unit,
        execOnError: (ErrorResponse?) -> Unit,
        isCustomEnabled: Boolean = false,
        useCustomView: Boolean = false
    ) {
        val theme = DocumentThemeFactory.create(context, isCustomEnabled, useCustomView)
        val config = createSDKConfig(
            appKey = appKey,
            environment = Environment.HML,
            theme = theme,
            enableCnhDigital = true,
            enablePdfUpload = true
        )

        val documentManager = CertifaceDocSDK.initialize(context, config)

        val callback = object : ResultCallback<CertifaceDocResult> {
            override fun onSuccess(result: CertifaceDocResponse) {
                execOnSuccess(result.docResult)
            }

            override fun onError(result: CertifaceDocResponse) {
                val errorResponse = ErrorResponse(
                    errorType = LivenessErrorType.TRANSACTION_NOT_COMPLETED,
                    errorMessage = result.errorResponse?.errorMessage ?: "Erro desconhecido"
                )
                execOnError(errorResponse)
            }
        }

        documentManager.start(config, callback)
    }
}

