package com.omniful.data.manager.language

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import java.util.Locale

class MyContextWrapper(base: Context?) : ContextWrapper(base) {
    companion object {
        fun wrap(mContext: Context): MyContextWrapper {
            var context = mContext
            val res = context.resources
            val configuration: Configuration = res.configuration
            var newLocale = Locale(LanguageUtil.getSavedLanguage(context))

            if(LanguageUtil.getSavedLanguage(context).contains("-")){
                val code = LanguageUtil.getSavedLanguage(context).split("-").getOrNull(0)
                val region = LanguageUtil.getSavedLanguage(context).split("-").getOrNull(1)
                region?.let { newLocale = Locale(code, it) }
            }

            context = if (Build.VERSION.SDK_INT >= 24) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                context.createConfigurationContext(configuration)
            } else {
                configuration.setLocale(newLocale)
                context.createConfigurationContext(configuration)
            }
            return MyContextWrapper(context)
        }
    }
}
