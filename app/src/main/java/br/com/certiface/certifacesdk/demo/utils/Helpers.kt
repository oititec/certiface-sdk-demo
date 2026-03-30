package br.com.certiface.certifacesdk.demo.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("AppKey", text)
    clipboard.setPrimaryClip(clip)
}
