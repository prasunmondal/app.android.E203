package com.example.e203

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.e203.Utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val submitFormURL: String =
        "https://docs.google.com/forms/d/e/1FAIpQLSdYzijpIalsSmnyQ53tkZawzOM40yYYR92O0TPfAhSRcgo9Wg/viewform?usp=sf_link"
    private val detailsFormURL: String =
        "https://docs.google.com/forms/d/e/1FAIpQLSfpjgKBlK678ncJGTRV1-iwCzGuYsKXea71k7uQtJficGD7kw/viewform"
//    private val enlistFormURL: String =
//        "https://docs.google.com/forms/d/e/1FAIpQLSdoq9CzHE7t2CY85VG7MXLDSphCZhgnXli3blmOE5k-FT04mw/viewform"
    private val editPage =
        "https://docs.google.com/spreadsheets/d/e/2PACX-1vRZQ28x7jpdIOzT2PA6iTCTcyTHM9tVPkv2ezuqd4LFOWu9SJqImGM7ML8ejdQB01SdjfTZnoHogzUt/pubhtml?gid=16104355&single=true"
//    private val apkLink = "https://github.com/prasunmondal/app_E203/blob/master/E203/app/src/main/E203_v5.apk?raw=true"
    private val detailCSV="https://docs.google.com/spreadsheets/d/e/2PACX-1vRZQ28x7jpdIOzT2PA6iTCTcyTHM9tVPkv2ezuqd4LFOWu9SJqImGM7ML8ejdQB01SdjfTZnoHogzUt/pub?gid=1321322233&single=true&output=csv"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView: WebView = findViewById(R.id.formView)
        webView.webViewClient = MyWebViewClient()
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        loadPage(submitFormURL)

        downloadAndUpdateInfo()
        showNot()
    }

    private fun showNot() {

        val mBuilder = NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher_background) // notification icon
            .setContentTitle("E203") // title for notification
            .setContentText("New Transaction Added...") // message for notification
            .setAutoCancel(true) // clear notification after click

        val intent = Intent(this, MainActivity::class.java)
        val pi =
            PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK)
        mBuilder.setContentIntent(pi)
        val mNotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(0, mBuilder.build())
    }

    private fun loadPage(url: String) {
        val webView: WebView = findViewById(R.id.formView)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        Log.d("dirty: ",webView.isDirty.toString())
        webView.stopLoading()
//        progressDialog!!.show()

        webView.webViewClient = object : WebViewClient() {
    //            override fun onPageFinished(view: WebView, url: String) {
    //                if (progressDialog!!.isShowing) {
    //                    progressDialog!!.dismiss()
    //                }
    //            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(this@MainActivity, "Error:$description", Toast.LENGTH_SHORT).show()
            }
        }

        webView.loadUrl(url)
    }





    private fun downloadAndUpdateInfo() {
        val url = detailCSV
        checkStoragePermission()
        downloadControllerInfo = DownloadControllerInfo(this, url)
        downloadControllerInfo.enqueueDownload(findViewById(R.id.formView))
    }

    fun loadAddForm(view: View) {
        val myWebView: WebView = findViewById(R.id.formView)
        myWebView.loadUrl(submitFormURL)
    }

    fun loadDetails(view: View) {
        loadPage(detailsFormURL)
    }

    fun loadEditPage(view: View) {
        loadPage(editPage)
    }


    // Other Utils

    companion object {
        const val PERMISSION_REQUEST_STORAGE = 0
    }

    private lateinit var downloadControllerInfo: DownloadControllerInfo

    private fun checkStoragePermission() {
        // Check if the storage permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            requestStoragePermission()
    }

    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            mainLayout.showSnackbar(
                R.string.storage_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok
            ) {
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_STORAGE
                )
            }

        } else {
            requestPermissionsCompat(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_STORAGE
            )
        }
    }
}

private class MyWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return false
    }
}