package com.omniful.data.manager.language


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.view.View
import android.view.Window
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.collections.get

object LanguageUtil {
    private lateinit var preferences: SharedPreferences
    private const val NAME = "LanguagePrefs"
    private const val MODE = Context.MODE_PRIVATE
    private const val PREFERRED_LOCALE = "preferred_locale"
    const val BROADCAST_LANGUAGE_CHANGED = "broadcast_language_changed"



    enum class Language(val code: String) {
        EN("en"),
        AR("ar"),
        ES("es"),
        DE("de"),
        PT("pt"),
        FR("fr"),
        FR_CA("fr-rCA"),
        DA("da"),
        NL("nl"),
        FI("fi"),
        IW("iw"),
        IT("it"),
        JA("ja"),
        KO("ko"),
        NB("nb"),
        PT_BR("pt-rBR"),
        RO("ro"),
        RU("ru"),
        ZH_CN("zh-rCN"),
        ES_MX("es-rMX"),
        SV("sv"),
        ZH_TW("zh-rTW"),
        TR("tr"),
        HI("hi"),
        TH("th"),
        VI("vi"),
        HU("hu")
    }


    private var preferredLocale: String?
        get() = preferences.getString(PREFERRED_LOCALE, null)
        set(value) = preferences.edit {
            it.putString(PREFERRED_LOCALE, value)
            it.apply()
        }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            NAME,
            MODE,
        )
    }

    fun Context.findActivity(): Activity? = when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }

    /**
     * Change Localization and Saved on Preference
     * Update Broadcast Manager
     */
    fun changeLocalization(activity: Activity, code: String) {
        try {
            if (code == getSavedLanguage(activity)) return
            preferredLocale = code
            val intent = Intent(BROADCAST_LANGUAGE_CHANGED)
            LocalBroadcastManager.getInstance(activity).sendBroadcast(intent)
        } catch (e: Exception) {
        }
    }

    /**
     * Get Saved Language By User from Preference
     * if not saved bu user then get Device Locale
     */
    fun getSavedLanguage(context: Context): String {
        return preferredLocale ?: getCurrentLocale(context)
    }

    /**
     * Get Device Locale
     * if it's Arabic then return Arabic otherwise English
     */
    private fun getCurrentLocale(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
        return if(Language.entries.map { it.code }.contains(locale.language)){
            locale.language
        } else{
            Language.EN.code
        }
    }

    /**
     * Change Orientation according to Locale
     */
    fun Window.setOrientation(context: Context) {
        if (getSavedLanguage(context) == Language.AR.code) {
            decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
    }
}
