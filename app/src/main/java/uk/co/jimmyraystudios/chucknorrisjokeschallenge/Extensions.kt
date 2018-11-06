package uk.co.jimmyraystudios.chucknorrisjokeschallenge

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Convenience function to allow strings to escape from HTML.
 */
fun String.escapeHtml(): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}