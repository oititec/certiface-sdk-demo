package br.com.certiface.certifacesdk.demo.factories

import android.content.Context
import br.com.certiface.certifacesdk.demo.R
import br.com.certiface.certifacesdk.demo.examples.ModernCaptureScreen
import br.com.certiface.certifacesdk.demo.examples.ModernInstructionScreen
import br.com.certiface.certifacesdk.demo.examples.ModernLoadingDialog
import br.com.certiface.certifacesdk.demo.examples.ModernPermissionScreen
import br.com.certiface.certifacesdk.demo.examples.ModernQrScreen
import br.com.certiface.certifacesdk.demo.examples.ModernResultScreen
import br.com.certiface.certifacesdk.demo.examples.ModernUploadScreen
import br.com.certiface.doc.designsystem.ui.builders.CustomCaptureScreenBuilder
import br.com.certiface.doc.designsystem.ui.builders.CustomLoadingDialogBuilder
import br.com.certiface.doc.designsystem.ui.builders.CustomResultScreenBuilder
import br.com.certiface.doc.designsystem.ui.builders.CustomScreensBuilder
import br.com.certiface.doc.designsystem.ui.builders.DocCoreCustomInstructionsBuilder
import br.com.certiface.doc.designsystem.ui.builders.DocumentUploadScreenCustomBuilder
import br.com.certiface.doc.designsystem.ui.builders.InstructionCustomsBuilder
import br.com.certiface.doc.designsystem.ui.builders.PermissionCustomsBuilder
import br.com.certiface.doc.designsystem.ui.builders.QrScreenCustomBuilder
import br.com.certiface.doc.designsystem.ui.doccore.DocCoreFontsKey
import br.com.certiface.doc.domain.theme.CertifaceDocTheme

object DocumentThemeFactory {
    fun create(context: Context, isCustom: Boolean, useCustomView: Boolean = false): CertifaceDocTheme? =
        if (isCustom) buildCustom(context, useCustomView) else buildDefault()

    private fun buildDefault() = CertifaceDocTheme.build {}

    private fun buildCustom(context: Context, useCustomView: Boolean): CertifaceDocTheme {
        val docCoreFonts = createFontsMap(context)

        return CertifaceDocTheme.build {
            setFontsKey(docCoreFonts)

            if (useCustomView) {
                setCustomScreens(
                    CustomScreensBuilder.build {
                        setCustomInstructionComposable { callbacks ->
                            ModernInstructionScreen(
                                onDocumentClick = callbacks.onDocumentClick,
                                onDigitalClick = callbacks.onDigitalClick,
                                onDocumentFileClick = callbacks.onDocumentFileClick,
                                isCnhDigital = callbacks.isCnhDigital,
                                isUploadFile = callbacks.isUploadFile,
                                onBackClick = callbacks.onBack
                            )
                        }

                        setCustomPermissionComposable { callbacks ->
                            ModernPermissionScreen(
                                onPermissionGranted = callbacks.onPermissionGranted,
                                onBack = callbacks.onBack
                            )
                        }

                        setCustomCaptureComposable { cameraPreview, cameraPreviewComponents, captureState ->
                            ModernCaptureScreen(
                                cameraPreview = cameraPreview,
                                cameraPreviewComponents = cameraPreviewComponents,
                                captureState = captureState
                            )
                        }

                        setCustomQrComposable { qrScanner, qrScannerComponents, qrScanState ->
                            ModernQrScreen(
                                qrScanner = qrScanner,
                                qrScannerComponents = qrScannerComponents,
                                qrScanState = qrScanState
                            )
                        }

                        setCustomDocumentUploadComposable { documentUploadState ->
                            ModernUploadScreen(documentUploadState)
                        }

                        setCustomCnhUploadComposable { documentUploadState ->
                            ModernUploadScreen(documentUploadState)
                        }

                        setCustomResultComposable { resultState ->
                            ModernResultScreen(
                                success = resultState.success,
                                errorMessage = resultState.errorMessage,
                                showRetryButton = resultState.showRetryButton,
                                onRetry = resultState.onRetry,
                                onSuccess = resultState.onSuccess,
                                onError = resultState.onError
                            )
                        }

                        setCustomLoadingDialogComposable { message, progress ->
                            ModernLoadingDialog(
                                message = message,
                                progress = progress
                            )
                        }
                    }
                )
            }

            setInstructionsTheme(
                DocCoreCustomInstructionsBuilder.build {
                    // Cores gerais - ROXO para background
                    setInstructionBackgroundColor("#4A148C")
                    setInstructionBottomSheetColor("#7B1FA2")
                    setInstructionBottomSheetCornerRadius(20f)
                    setInstructionLoadingColor("#FF9800")

                    // Imagem de contexto - Adicione seu drawable aqui
                    setInstructionContextImage(br.com.certiface.doc.designsystem.R.drawable.neutral_face)

                    // Título e subtítulo - AMARELO para título, CIANO para caption
                    setInstructionTitle("Verificação de Documento")
                    setInstructionTitleColor("#FFEB3B")
                    setInstructionCaption("Siga as instruções na tela para capturar seu documento")
                    setInstructionCaptionColor("#00BCD4")

                    // Status bar - ROXO escuro
                    setInstructionStatusBarColor("#311B92")
                    setInstructionStatusBarIsDarkIcons(false)

                    // Botão voltar - LARANJA
                    setInstructionBackButtonIcon(br.com.certiface.doc.designsystem.R.drawable.backhand_left)
                    setInstructionBackButtonIconColor("#FF9800")
                    setInstructionBackButtonBackgroundColor("#FF6F00")
                    setInstructionBackButtonBorderColor("#FF6F00")

                    // Opção Documento Físico - AZUL
                    setInstructionPhysicalDocumentOptionImage(br.com.certiface.doc.designsystem.R.drawable.camera_alt)
                    setInstructionPhysicalDocumentOptionTitle("Documento Físico")
                    setInstructionPhysicalDocumentOptionTitleColor("#FFFFFF")
                    setInstructionPhysicalDocumentOptionDescription("Capture a frente e o verso")
                    setInstructionPhysicalDocumentOptionDescriptionColor("#64B5F6")
                    setInstructionPhysicalDocumentOptionBackgroundColor("#0D47A1")
                    setInstructionPhysicalDocumentOptionBorderColor("#2196F3")
                    setInstructionPhysicalDocumentOptionBorderWidth(3f)
                    setInstructionPhysicalDocumentOptionCornerRadius(16f)
                    // Customizações do ícone do documento físico
                    setInstructionPhysicalDocumentOptionIconColor("#2196F3")
                    setInstructionPhysicalDocumentOptionCircleBackgroundColor("#0D47A1")
                    setInstructionPhysicalDocumentOptionIconBorderColor("#2196F3")
                    // Cor da seta (arrow) do documento físico
                    setInstructionPhysicalDocumentOptionArrowIconColor("#2196F3")

                    setInstructionDigitalDocumentOptionImage(br.com.certiface.doc.designsystem.R.drawable.doc_img)
                    setInstructionDigitalDocumentOptionTitle("CNH Digital")
                    setInstructionDigitalDocumentOptionTitleColor("#FFFFFF")
                    setInstructionDigitalDocumentOptionDescription("Selecione o arquivo da CNH")
                    setInstructionDigitalDocumentOptionDescriptionColor("#F48FB1")
                    setInstructionDigitalDocumentOptionBackgroundColor("#880E4F")
                    setInstructionDigitalDocumentOptionBorderColor("#E91E63")
                    setInstructionDigitalDocumentOptionBorderWidth(3f)
                    setInstructionDigitalDocumentOptionCornerRadius(16f)
                    // Customizações do ícone do documento digital
                    setInstructionDigitalDocumentOptionIconColor("#E91E63")
                    setInstructionDigitalDocumentOptionCircleBackgroundColor("#880E4F")
                    setInstructionDigitalDocumentOptionIconBorderColor("#E91E63")
                    // Cor da seta (arrow) do documento digital
                    setInstructionDigitalDocumentOptionArrowIconColor("#E91E63")

                    setInstructionFileDocumentOptionImage(br.com.certiface.doc.designsystem.R.drawable.ic_file_upload_blue)
                    setInstructionFileDocumentOptionTitle("Enviar Arquivo")
                    setInstructionFileDocumentOptionTitleColor("#000000")
                    setInstructionFileDocumentOptionDescription("Envio em formato PDF.")
                    setInstructionFileDocumentOptionDescriptionColor("#acc724")
                    setInstructionFileDocumentOptionBackgroundColor("#d7f542")
                    setInstructionFileDocumentOptionBorderColor("#acc724")
                    setInstructionFileDocumentOptionBorderWidth(3f)
                    setInstructionFileDocumentOptionCornerRadius(16f)
                    setInstructionFileDocumentOptionIconColor("#acc724")
                    setInstructionFileDocumentOptionCircleBackgroundColor("#d7f542")
                    setInstructionFileDocumentOptionIconBorderColor("#acc724")
                    setInstructionFileDocumentOptionArrowIconColor("#acc724")
                }
            )

            setPermissionTheme(
                PermissionCustomsBuilder.build {
                    setTitle("Permissão de Câmera")
                    setTitleColor("#FFEB3B")
                    setSubTitle("Precisamos da sua permissão para capturar o documento")
                    setSubTitleColor("#00BCD4")
                    setBackgroundColor("#4A148C")
                    setStatusBarColor("#311B92")
                    setStatusBarIsDarkIcons(false)
                    setBackButtonIcon(R.drawable.test)
                    setCameraIcon(br.com.oiti.designsystem.R.drawable.face)
                    setPermissionButtonText("Permitir")
                    setPermissionButtonColor("#2196F3")
                    setPermissionButtonTextColor("#FFFFFF")
                }
            )

            setCaptureTheme(
                CustomCaptureScreenBuilder.build {
                    setBackgroundColor("#000000")
                    setBackButtonColor("#FF9800")
                    setCloseButtonColor("#E91E63")

                    // Indicadores - LARANJA
                    setFrontIndicatorText("Frente")
                    setBackIndicatorText("Verso")
                    setIndicatorColor("#FF9800")
                    setIndicatorTextColor("#FFFFFF")

                    // Instruções - CIANO
                    setInstructionsGuideTextFront("Posicione a frente do documento")
                    setInstructionsGuideTextBack("Posicione o verso do documento")
                    setInstructionsGuideTextConfirmation("Confirme se está legível")
                    setInstructionsGuideColor("#00BCD4")

                    // Preview - AZUL quando capturado, ROXO quando não
                    setCapturePreviewBorderColorCaptured("#2196F3")
                    setCapturePreviewBorderColorUncaptured("#9C27B0")

                    // Botão de captura - LARANJA
                    setCaptureButtonColor("#FF9800")
                    setCaptureButtonBorderColor("#FF6F00")

                    // Bottom sheet - ROXO
                    setBottomSheetColor("#7B1FA2")
                    setBottomSheetCornerRadius(20f)
                }
            )

            setLoadingDialogTheme(
                CustomLoadingDialogBuilder.build {
                    setBackgroundColor("#80000000")
                    setCircularProgressIndicatorColor("#FF9800")
                    setCircularProgressIndicatorSize(100)
                    setCircularProgressIndicatorWidth(8)
                    setStatusBarColor("#000000")
                    setStatusBarIsDarkIcons(true)
                }
            )

            setResultScreenTheme(
                CustomResultScreenBuilder.build {
                    // Sucesso - CIANO claro
                    setSuccessBackgroundColor("#B2EBF2")
                    setSuccessText("Documento verificado com sucesso!")
                    setSuccessTextColor("#00695C")
                    setStatusBarSuccessColor("#B2EBF2")
                    setStatusBarSuccessIsDarkIcons(true)

                    // Erro - ROSA claro
                    setErrorBackgroundColor("#FCE4EC")
                    setErrorText("Algo deu errado na verificação")
                    setErrorTextColor("#C2185B")
                    setStatusBarErrorColor("#FCE4EC")
                    setStatusBarErrorIsDarkIcons(true)

                    // Botão retry - AZUL
                    setRetryButtonColor("#2196F3")
                    setRetryButtonText("Tentar novamente")
                    setRetryButtonTextColor("#FFFFFF")
                }
            )

            setQrScreenTheme(
                QrScreenCustomBuilder.build {
                    setQrCodeTitle("Escaneie o QR Code")
                    setQrCodeTitleColor("#FFEB3B")
                    setQrCodeCaption("Aponte a câmera para o QR Code do documento")
                    setQrCodeCaptionColor("#00BCD4")
                    setQrCodeBottomSheetColor("#7B1FA2")
                    setQrCodeBottomSheetCornerRadius(20f)
                    setQrCodeSquareTargetColor("#FF9800")
                    setQrCodeBackButtonIconColor("#FF9800")
                    setQrCodeFileButtonText("Selecionar arquivo")
                    setQrCodeFileButtonTextColor("#FFFFFF")
                    setQrCodeFileButtonBackgroundColor("#2196F3")
                }
            )

            setDocumentUploadTheme(
                DocumentUploadScreenCustomBuilder.build {
                    // Background geral - ROXO
                    setUploadBackgroundColor("#4A148C")
                    setUploadStatusBarColor("#311B92")
                    setUploadStatusBarIsDarkIcons(false)

                    // Título e descrição - AMARELO e CIANO
                    setUploadFileTitle("Selecione o arquivo")
                    setUploadFileTitleColor("#FFEB3B")
                    setUploadFileDescription("Escolha o arquivo da CNH Digital")
                    setUploadFileDescriptionColor("#00BCD4")

                    // Botões de navegação - LARANJA e ROSA
                    setUploadBackButtonIconColor("#FF9800")
                    setUploadBackButtonBackgroundColor("#FF6F00")
                    setUploadCloseButtonIconColor("#E91E63")
                    setUploadCloseButtonBackgroundColor("#C2185B")

                    // Área de seleção de arquivo (quando vazio) - AZUL
                    setUploadFilePickerText("Selecionar arquivo")
                    setUploadFilePickerHintText("Clique aqui para")
                    setUploadFilePickerTextColor("#FFFFFF")
                    setUploadFilePickerIconColor("#2196F3")
                    setUploadFilePickerBackgroundColor("#0D47A1")
                    setUploadFilePickerBorderColor("#2196F3")
                    setUploadFilePickerBorderWidth(3f)
                    setUploadFilePickerCornerRadius(16f)
                    setUploadAreaHeightDp(250f)

                    // Arquivo selecionado - LARANJA para borda, CIANO para background
                    setUploadSelectedFileBorderColor("#FF9800")
                    setUploadSelectedFileContainerBackgroundColor("#00BCD4")
                    setUploadFileNameAreaBackgroundColor("#00838F")
                    setUploadFileNameTextColor("#FFFFFF")
                    setUploadSelectedFileSuccessText("Documento selecionado!")
                    setUploadSelectedFileSuccessTextColor("#e60935")
                    setUploadSelectedFileSuccessIconSizeDp(56f)
                    setUploadSelectedFileSuccessIcon(br.com.certiface.doc.designsystem.R.drawable.neutral_face)

                    // Ícone de delete - ROSA vibrante
                    setUploadDeleteFileIconColor("#E91E63")

                    // Botão enviar - AZUL
                    setUploadSendButtonText("Enviar")
                    setUploadSendButtonTextColor("#FFFFFF")
                    setUploadSendButtonBackgroundColor("#2196F3")
                    setUploadSendButtonDisabledBackgroundColor("#9E9E9E")
                    setUploadSendButtonDisabledTextColor("#616161")
                    setUploadSendButtonBorderColor("#1565C0")
                    setUploadSendButtonBorderWidth(2f)
                    setUploadSendButtonDisabledBorderColor("#455A64")
                    setUploadSendButtonCornerRadius(25f)
                    setUploadSendButtonHeightDp(50f)
                }
            )

            setCnhUploadTheme(
                DocumentUploadScreenCustomBuilder.build {
                    setUploadBackgroundColor("#4A148C")
                    setUploadStatusBarColor("#311B92")
                    setUploadStatusBarIsDarkIcons(false)

                    setUploadFileTitle("CNH Digital")
                    setUploadFileTitleColor("#E1BEE7")
                    setUploadFileDescription("Envie o PDF da sua CNH Digital")
                    setUploadFileDescriptionColor("#CE93D8")

                    setUploadBackButtonIconColor("#FFFFFF")
                    setUploadBackButtonBackgroundColor("#7B1FA2")
                    setUploadCloseButtonIconColor("#F8BBD9")
                    setUploadCloseButtonBackgroundColor("#AD1457")

                    setUploadFilePickerText("Selecionar o PDF")
                    setUploadFilePickerHintText("Clique aqui para")
                    setUploadFilePickerTextColor("#FFFFFF")
                    setUploadFilePickerIconColor("#F48FB1")
                    setUploadFilePickerBackgroundColor("#6A1B9A")
                    setUploadFilePickerBorderColor("#E91E63")
                    setUploadFilePickerBorderWidth(3f)
                    setUploadFilePickerCornerRadius(16f)
                    setUploadAreaHeightDp(250f)

                    setUploadSelectedFileSuccessIcon(br.com.certiface.doc.designsystem.R.drawable.neutral_face)
                    setUploadSelectedFileBorderColor("#E91E63")
                    setUploadSelectedFileContainerBackgroundColor("#FCE4EC")
                    setUploadFileNameAreaBackgroundColor("#880E4F")
                    setUploadFileNameTextColor("#FFFFFF")
                    setUploadSelectedFileSuccessText("CNH selecionada!")
                    setUploadSelectedFileSuccessTextColor("#e60935")
                    setUploadSelectedFileSuccessIconSizeDp(56f)
                    setUploadDeleteFileIconColor("#C2185B")

                    setUploadSendButtonText("Enviar CNH")
                    setUploadSendButtonTextColor("#FFFFFF")
                    setUploadSendButtonBackgroundColor("#AD1457")
                    setUploadSendButtonDisabledBackgroundColor("#546E7A")
                    setUploadSendButtonDisabledTextColor("#CFD8DC")
                    setUploadSendButtonBorderColor("#880E4F")
                    setUploadSendButtonBorderWidth(2f)
                    setUploadSendButtonDisabledBorderColor("#455A64")
                    setUploadSendButtonCornerRadius(25f)
                    setUploadSendButtonHeightDp(50f)
                }
            )

            setInstructionScreenTheme(
                InstructionCustomsBuilder.build {
                    setTitleText("Instruções")
                    setTitleColor("#FFEB3B")
                    setCaptionText("Siga as instruções abaixo")
                    setCaptionColor("#00BCD4")
                    setFirstInstructionText("Mantenha o documento estável")
                    setFirstInstructionTextColor("#FFFFFF")
                    setFirstInstructionIconBackgroundColor("#2196F3")
                    setSecondInstructionText("Certifique-se de que está bem iluminado")
                    setSecondInstructionTextColor("#FFFFFF")
                    setSecondInstructionIconBackgroundColor("#E91E63")
                    setContinueButtonText("Continuar")
                    setContinueButtonColor("#FF9800")
                    setContinueButtonTextColor("#FFFFFF")
                    setBackgroundColor("#4A148C")
                    setStatusBarColor("#311B92")
                    setStatusBarIsDarkIcons(false)
                }
            )
        }
    }

    private fun createFontsMap(context: Context): Map<DocCoreFontsKey, Int> {
        val fontResource = R.font.sixty

        return mapOf(
            DocCoreFontsKey.INSTRUCTIONS_TITLE_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_CAPTION_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_FIRST_BUTTON_TITLE_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_FIRST_BUTTON_CAPTION_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_SECOND_BUTTON_TITLE_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_SECOND_BUTTON_CAPTION_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_THIRD_BUTTON_TITLE_FONT to fontResource,
            DocCoreFontsKey.INSTRUCTIONS_DOCUMENT_THIRD_BUTTON_CAPTION_FONT to fontResource,

            DocCoreFontsKey.PERMISSION_TITLE_FONT to fontResource,
            DocCoreFontsKey.PERMISSION_CAPTION_FONT to fontResource,
            DocCoreFontsKey.PERMISSION_BUTTON_FONT to fontResource,

            DocCoreFontsKey.CAPTURE_INSTRUCTION_TEXT_FONT to fontResource,
            DocCoreFontsKey.CAPTURE_INSTRUCTION_GUIDE_FONT to fontResource,
            DocCoreFontsKey.CAPTURE_CONFIRMATION_MESSAGE_FONT to fontResource,
            DocCoreFontsKey.CAPTURE_BOTTOM_SHEET_FONT to fontResource,
            DocCoreFontsKey.CAPTURE_CONFIRM_BUTTON_FONT to fontResource,
            DocCoreFontsKey.CAPTURE_RETRY_BUTTON_FONT to fontResource,

            DocCoreFontsKey.QR_BOTTOM_SHEET_TITLE_FONT to fontResource,
            DocCoreFontsKey.QR_BOTTOM_SHEET_CAPTION_FONT to fontResource,
            DocCoreFontsKey.QR_BOTTOM_SHEET_BUTTON_FONT to fontResource,

            DocCoreFontsKey.DIGITAL_CNH_TITLE_FONT to fontResource,
            DocCoreFontsKey.DIGITAL_CNH_CAPTION_FONT to fontResource,
            DocCoreFontsKey.DIGITAL_CNH_SELECT_FILE_FONT to fontResource,
            DocCoreFontsKey.DIGITAL_CNH_SELECTED_FILE_FONT to fontResource,
            DocCoreFontsKey.DIGITAL_CNH_SELECTED_FILE_SUCCESS_TEXT_FONT to fontResource,
            DocCoreFontsKey.DIGITAL_CNH_BUTTON_FONT to fontResource,

            DocCoreFontsKey.PDF_UPLOAD_TITLE_FONT to fontResource,
            DocCoreFontsKey.PDF_UPLOAD_CAPTION_FONT to fontResource,
            DocCoreFontsKey.PDF_UPLOAD_SELECT_FILE_FONT to fontResource,
            DocCoreFontsKey.PDF_UPLOAD_SELECTED_FILE_FONT to fontResource,
            DocCoreFontsKey.PDF_UPLOAD_SELECTED_FILE_SUCCESS_TEXT_FONT to fontResource,
            DocCoreFontsKey.PDF_UPLOAD_BUTTON_FONT to fontResource,

            DocCoreFontsKey.RESULT_MESSAGE_FONT to fontResource,
            DocCoreFontsKey.RESULT_RETRY_BUTTON_FONT to fontResource
        )
    }
}