package com.shuangning.safeconstruction.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.HitTestResult.UNKNOWN_TYPE
import android.webkit.WebView.setWebContentsDebuggingEnabled
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.MyLog

/**
 * Created by Chenwei on 2023/9/16.
 */

class CommonWebActivity: AppCompatActivity() {
    private var url = ""
    private val ivBack: ImageView by lazy {
        findViewById(R.id.iv_title_back)
    }
    private val tvTitle: TextView by lazy {
        findViewById(R.id.tv_title_middle)
    }
    private val webView: WebView by lazy{
        findViewById(R.id.webview_detail)
    }
    private val progressbar: ProgressBar by lazy {
        findViewById(R.id.pb)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_common)
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.c_fff), 0)
        supportActionBar?.hide()
        if(intent.hasExtra(URL)) {
            val url = intent?.getStringExtra(URL)
            if(!url.isNullOrBlank()){
                this.url = url
            }
            initWebview(webView, true)
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("JavascriptInterface")
    private fun initWebview(webView: WebView?, isSupportMultipleWindows: Boolean) {
        webView?.apply {
            settings.domStorageEnabled = true
            settings.javaScriptEnabled = true
            settings.allowFileAccess = true
            settings.blockNetworkImage = false
            settings.setSupportMultipleWindows(isSupportMultipleWindows)
            settings.loadsImagesAutomatically = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
            } else {
                settings.mixedContentMode = WebSettings.LOAD_NORMAL;
            }
            if (BuildConfig.DEBUG){
                setWebContentsDebuggingEnabled(true);
            }
            isHorizontalScrollBarEnabled = false
            isVerticalScrollBarEnabled = false
            scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
            webChromeClient = object: WebChromeClient(){
                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)
                    MyLog.e("onProgressChanged:${newProgress}")
                    if (progressbar == null) return
                    if (newProgress >= 100){
                        progressbar.visibility = View.GONE
                    } else {
                        if (progressbar.visibility == View.GONE) {
                            progressbar.visibility = View.GONE
                        } else {
                            progressbar.visibility = View.VISIBLE
                        }
                        progressbar.progress = newProgress
                    }
                }

                override fun onReceivedTitle(view: WebView?, title: String?) {
                    super.onReceivedTitle(view, title)
                    MyLog.e("onReceivedTitle:${title?:""}")
                    tvTitle.text = title?:""
                }

                override fun onCreateWindow(
                    view: WebView?,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message?
                ): Boolean {
                    if(view?.hitTestResult?.type != UNKNOWN_TYPE){
                        val url = view?.hitTestResult?.extra
                        url?.let {
                            webView.loadUrl(url)
                            return true
                        }
                    }
                    return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
                }
            }
            webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    MyLog.e("onPageFinished")
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    MyLog.e("shouldOverrideUrlLoading url:${url}")
                    url?.let {
                        if (it.startsWith("http://") || it.startsWith("https://")) { // 4.0以上必须要加
                            view?.loadUrl(url);
                            return true
                        }else{
                            return super.shouldOverrideUrlLoading(view, url)
                        }
                    }?:let {
                        return super.shouldOverrideUrlLoading(view, url)
                    }
                }

                @RequiresApi(Build.VERSION_CODES.M)
                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    MyLog.e("onReceivedError errorCode:${error?.errorCode} errorMsg:${error?.description}")
                    progressbar.setVisibility(View.GONE)
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    super.onReceivedError(view, errorCode, description, failingUrl)
                    MyLog.e("onReceivedError errorCode:${errorCode} errorMsg:${description}")

                }
                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    MyLog.e("onReceivedHttpError errorCode:${errorResponse?.statusCode} errorMsg:${errorResponse?.reasonPhrase}")

                }
                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    MyLog.e("onReceivedSslError error:${error.toString()}")
                    when(error?.primaryError){
                        SslError.SSL_INVALID, SslError.SSL_UNTRUSTED->
                            handler?.proceed()

                        else-> handler?.cancel()
                    }
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    url: String?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, url)
        //                   return WebViewCacheInterceptorInst.getInstance().interceptRequest(url);
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
                ): WebResourceResponse? {
                    return super.shouldInterceptRequest(view, request)
        //                    return WebViewCacheInterceptorInst.getInstance().interceptRequest(request);
                }
            }
            MyLog.d("url:${this@CommonWebActivity.url}")
            loadUrl(this@CommonWebActivity.url)
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }
    }

    override fun onDestroy() {
        webView.apply {
            clearHistory()
            (parent as? ViewGroup)?.removeView(webView)
            destroy()
        }
        super.onDestroy()
    }

    companion object{
        const val URL = "url"
        fun start(ctx: Context, url: String){
            val intent = Intent(ctx, CommonWebActivity::class.java)
            intent.putExtra(URL, url)
            ctx.startActivity(intent)
        }
    }
}