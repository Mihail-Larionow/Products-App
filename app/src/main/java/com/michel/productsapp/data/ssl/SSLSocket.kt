package com.michel.productsapp.data.ssl

import android.annotation.SuppressLint
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

// SSLSocket class to provide certificate thrust for OkHttp request
// Otherwise, an untrusted certificate exception will be issued
class SSLSocket {

    // X509TrustManager override to trust all certificates
    val x509TrustManager: X509TrustManager = @SuppressLint("CustomX509TrustManager")
    object : X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {}

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }

    }

    // Creates a factory of new SSLSocket that will be used in OkHttp request
    fun getFactory(): SSLSocketFactory{
        val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        return sslContext.socketFactory
    }

}