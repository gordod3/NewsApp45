package kg.geektech.newsapp45

import android.content.Context
import android.net.Uri

class Prefs(context : Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun saveBoardState() {
        prefs.edit().putBoolean(Enum.PREFS_IS_SHOWN.name, true).apply()
    }

    fun isShown() : Boolean {
        return prefs.getBoolean(Enum.PREFS_IS_SHOWN.name, false)
    }

    fun saveAvatarURI(uri: String) {
        prefs.edit().putString(Enum.PREFS_PROFILE_URI.name, uri).apply()
    }

    fun getAvatarUri() : String? {
        return prefs.getString(Enum.PREFS_PROFILE_URI.name, null)
    }
}