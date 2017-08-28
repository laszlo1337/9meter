package io.finefabric.ninebanana

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView

class ObservableWebView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {

    private var onYScrollChangedListener: OnScrollChangedListener? = null

    fun setOnYScrollChangedListener(listener: OnScrollChangedListener) {
        this.onYScrollChangedListener = listener
    }

    interface OnScrollChangedListener {
        fun onYScrollChange(previousPosY: Int, currentPosY: Int)
    }

    /**
     * This is called in response to an internal scroll in this view (i.e., the
     * view scrolled its own contents). This is typically as a result of
     * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
     * called.
     *
     * @param l Current horizontal scroll origin.
     * @param t Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        onYScrollChangedListener?.onYScrollChange(oldt, t)
    }
}

class ObservableWebChromeClient : WebChromeClient() {

    private var onPageLoadedListener: OnPageLoadedListener? = null

    /*
     * Above 70% progress level looks good when transitioning from splash to WebView
     */
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (newProgress > 70) onPageLoadedListener?.onPageLoaded()
        super.onProgressChanged(view, newProgress)
    }

    fun setOnPageLoadedListener(listener: OnPageLoadedListener) {
        this.onPageLoadedListener = listener
    }

    interface OnPageLoadedListener {
        fun onPageLoaded()
    }
}