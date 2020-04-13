package com.ccys.qyuilib.network;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * 包名：com.qinyang.qyuilib.network
 * 创建人：秦洋
 * 创建时间：2019/3/1
 * 配置自己的证书 (需要将证书放到本地asset目录下)
 */
public class SetingsSSlUtil {
    @SuppressLint("TrulyRandom")
    public static SSLSocketFactory createSSLSocketFactory(Context context,String sslName) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManager[] trustManagers = {new MyX509TrustManager(context,sslName)};
            sslContext.init(null, trustManagers, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static class MyX509TrustManager implements X509TrustManager {
        private Context context;
        private String sslName;
       public MyX509TrustManager(Context context,String sslName){
            this.context = context;
            this.sslName = sslName;
        }
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (chain == null) {
                throw new CertificateException("checkServerTrusted: X509Certificate array is null");
            }
            if (chain.length < 1) {
                throw new CertificateException("checkServerTrusted: X509Certificate is empty");
            }
            if (!(null != authType && authType.equals("ECDHE_RSA"))) {
                throw new CertificateException("checkServerTrusted: AuthType is not ECDHE_RSA");
            }

            //检查所有证书
            try {
                TrustManagerFactory factory = TrustManagerFactory.getInstance("X509");
                factory.init((KeyStore) null);
                for (TrustManager trustManager : factory.getTrustManagers()) {
                    ((X509TrustManager) trustManager).checkServerTrusted(chain, authType);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            //获取本地证书中的信息
            String clientEncoded = "";
            String clientSubject = "";
            String clientIssUser = "";
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                InputStream inputStream = context.getAssets().open(sslName);
                X509Certificate clientCertificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);
                clientEncoded = new BigInteger(1, clientCertificate.getPublicKey().getEncoded()).toString(16);
                clientSubject = clientCertificate.getSubjectDN().getName();
                clientIssUser = clientCertificate.getIssuerDN().getName();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //获取网络中的证书信息
            X509Certificate certificate = chain[0];
            PublicKey publicKey = certificate.getPublicKey();
            String serverEncoded = new BigInteger(1, publicKey.getEncoded()).toString(16);

            if (!clientEncoded.equals(serverEncoded)) {
                throw new CertificateException("server's PublicKey is not equals to client's PublicKey");
            }
            String subject = certificate.getSubjectDN().getName();
            if (!clientSubject.equals(subject)) {
                throw new CertificateException("server's subject is not equals to client's subject");
            }
            String issuser = certificate.getIssuerDN().getName();
            if (!clientIssUser.equals(issuser)) {
                throw new CertificateException("server's issuser is not equals to client's issuser");
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}
