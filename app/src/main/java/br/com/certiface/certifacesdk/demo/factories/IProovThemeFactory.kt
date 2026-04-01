package br.com.certiface.certifacesdk.demo.factories

import androidx.core.graphics.toColorInt
import br.com.certiface.certifacesdk.demo.R
import br.com.certiface.certifacesdk.demo.examples.IProovModernInstructionScreen
import br.com.certiface.certifacesdk.demo.examples.IProovModernLoadingDialog
import br.com.certiface.certifacesdk.demo.examples.IProovModernPermissionScreen
import br.com.certiface.certifacesdk.demo.examples.IProovModernResultScreen
import br.com.certiface.domain.model.iproov.OrientationGPA
import br.com.certiface.domain.model.iproov.OrientationLA
import br.com.certiface.iproov.presentation.ui.builders.IProovCustomScreensBuilder
import br.com.certiface.manager.exports.FilterTheme
import br.com.certiface.manager.exports.IProovDrawablesKey
import br.com.certiface.manager.exports.IProovFontsKey
import br.com.certiface.manager.exports.IProovTheme
import br.com.certiface.manager.exports.LineDrawingStyle


object IProovThemeFactory {
    fun create(
        isCustom: Boolean,
        useCustomView: Boolean = false,
        showInstructionScreen: Boolean = true
    ): IProovTheme =
        if (isCustom) buildCustom(useCustomView, showInstructionScreen) else buildDefault(
            showInstructionScreen
        )

    private fun buildDefault(showInstructionScreen: Boolean = true) = IProovTheme.build {
        setIsEnabledScreenShots(true)
        setInstructionsTheme {
            setShowInstructionScreen(showInstructionScreen)
        }
    }

    private fun buildCustom(
        useCustomView: Boolean = false,
        showInstructionScreen: Boolean = true
    ): IProovTheme {
        return IProovTheme.build {
            if (useCustomView) {
                setCustomScreens(
                    IProovCustomScreensBuilder.build {
                        setCustomInstructionComposable { callbacks ->
                            IProovModernInstructionScreen(
                                onStartClick = callbacks.onStartClick,
                                onBack = callbacks.onBack
                            )
                        }

                        setCustomPermissionComposable { callbacks ->
                            IProovModernPermissionScreen(
                                onPermissionGranted = callbacks.onPermissionGranted,
                                onBack = callbacks.onBack
                            )
                        }

                        setCustomLoadingDialogComposable { message, progress ->
                            IProovModernLoadingDialog(
                                message = message,
                                progress = progress
                            )
                        }

                        setCustomResultComposable { resultState ->
                            IProovModernResultScreen(
                                success = resultState.success,
                                errorMessage = resultState.errorMessage,
                                showRetryButton = resultState.showRetryButton,
                                reason = resultState.reason,
                                onRetry = resultState.onRetry
                            )
                        }
                    }
                )
            }

            val iProovFonts = mapOf(
                IProovFontsKey.INSTRUCTIONS_TITLE_FONT to R.font.sixty,
                IProovFontsKey.INSTRUCTIONS_CAPTION_FONT to R.font.sixty,
                IProovFontsKey.INSTRUCTIONS_DOCUMENT_TYPES_INSTRUCTIONS_FONT to R.font.sixty,
                IProovFontsKey.INSTRUCTIONS_DOCUMENT_TIPS_INSTRUCTIONS_FONT to R.font.sixty,
                IProovFontsKey.INSTRUCTIONS_BUTTON_FONT to R.font.sixty,
                IProovFontsKey.PERMISSION_TITLE_FONT to R.font.sixty,
                IProovFontsKey.PERMISSION_CAPTION_FONT to R.font.sixty,
                IProovFontsKey.PERMISSION_BUTTON_FONT to R.font.sixty,

                IProovFontsKey.RESULT_MESSAGE_FONT to R.font.sixty,
                IProovFontsKey.RESULT_RETRY_BUTTON_FONT to R.font.sixty,
            )

            val iProovDrawables = mapOf(
                IProovDrawablesKey.IPROOV_CLOSE_BUTTON to br.com.certiface.designsystem.R.drawable.close_icon,
                IProovDrawablesKey.IPROOV_LOGO to "test",
                IProovDrawablesKey.INSTRUCTIONS_FIRST_INSTRUCTION_ICON to R.drawable.backhand_left,
                IProovDrawablesKey.INSTRUCTIONS_SECOND_INSTRUCTION_ICON to "backhand_right",
            )

            setTitle("Verificação Facial")
            setTitleColor("#FFFFFF")
            setHeaderBackgroundColor("#121212")
            setPromptTextColor("#FFFFFF")
            setPromptBackgroundColor("#1F1F1F")
            setSurroundColor("#00FF00")
            setFontResource(R.font.sixty)
            setIsEnabledScreenShots(true)
            setDisableExteriorEffects(false)
            setLogo(R.drawable.ic_launcher_foreground)
//            setCloseButton(br.com.oiti.designsystem.R.drawable.close_icon)
//            setCloseButtonColor("#a8324a")
            setTimeoutSecs(60)
            setPromptRoundedCorners(true)

            setIsControlYPositionEnabled(false)
            setIsControlXPositionEnabled(false)
            setIsScanningPromptsEnabled(false)

            setFontsKey(iProovFonts)
            setDrawablesKey(iProovDrawables)
            setFilter(
                FilterTheme.LineDrawing(
                    style = LineDrawingStyle.SHADED,
                    background = "#32a852".toColorInt(),
                    foreground = "#4232a8".toColorInt()
                )
            )

            setOrientation(
                gpa = OrientationGPA.PORTRAIT,
                la = OrientationLA.PORTRAIT
            )

            setOvalColors(
                ready = "#00FF00".toColorInt(),
                notReady = "#FF0000".toColorInt(),
                stroke = "#FFFFFF".toColorInt(),
                completed = "#00FF00".toColorInt()
            )

            setInstructionsTheme {
                setTitleText("Teste title")
                setTitleColor("#FFFFFF")
                setCaptionText("teste caption.")
                setCaptionColor("#AAAAAA")
                setBackgroundColor("#1F1F1F")
                setStatusBarColor("#1F1F1F")
                setStatusBarIsDarkIcons(false)
                setBottomSheetColor("#333333")
                setFirstInstructionText("teste 1")
                setFirstInstructionIcon(br.com.certiface.designsystem.R.drawable.backhand_left)
                setSecondInstructionText("teste 2")
                setBottomSheetCornerRadius(16f)
                setContinueButtonText("Startar")
                setContinueButtonColor("#00FF00")
                setContinueButtonTextColor("#000000")
                setShowInstructionScreen(showInstructionScreen)
            }

            setPermissionTheme {
                setTitle("Permissões Necessárias")
                setTitleColor("#FFFFFF")
                setBackgroundColor("#1F1F1F")
                setStatusBarColor("#1F1F1F")
                setStatusBarIsDarkIcons(false)
            }

            setProcessingTheme {
                setBackgroundColor("#000000")
                setLoadingDialogColor("#FFFFFF")
                setStatusBarColor("#000000")
                setStatusBarIsDarkIcons(true)
                setLoadingIndicatorSize(100)
                setLoadingIndicatorWidth(10)
            }

            setResultTheme {
                setSuccessBackgroundColor("#DFFFD6")
                setSuccessIcon(br.com.certiface.designsystem.R.drawable.success_icon)
                setSuccessText("Verificação concluída com sucesso!")
                setSuccessTextColor("#0F9D58")

                setStatusBarSuccessColor("#DFFFD6")
                setStatusBarErrorColor("#FFD6D6")
                setStatusBarSuccessIsDarkIcons(true)
                setStatusBarErrorIsDarkIcons(true)

                setErrorBackgroundColor("#FFD6D6")
                setErrorIcon(br.com.certiface.designsystem.R.drawable.error_icon)
                setErrorText("Algo deu errado na verificação.")
                setErrorTextColor("#D93025")

                setRetryButtonColor("#0F9D58")
                setRetryButtonText("Tentar novamente")
                setRetryButtonTextColor("#FFFFFF")
            }
        }
    }
}