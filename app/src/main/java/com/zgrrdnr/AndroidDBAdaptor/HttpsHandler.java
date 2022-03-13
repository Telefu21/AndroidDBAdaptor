package com.zgrrdnr.AndroidDBAdaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class HttpsHandler implements Runnable
{
    private final Context context;
    private Bitmap bmpImage;
    private StringBuffer    response = new StringBuffer();

    public HttpsHandler(Context context)
    {
        this.context = context;
    }

    public void connect()
    {
        Thread thread = new Thread(this);

        thread.start();
    }

    @Override
    public void run()
    {
        try
        {
            Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(context.getResources().openRawResource(R.raw.selfsigned_192_168_0_12));
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("server", certificate);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            HttpsURLConnection connection = (HttpsURLConnection) new URL("https", "192.168.0.12", 443, "/json").openConnection();
            connection.setSSLSocketFactory(sslContext.getSocketFactory());

            connection.setHostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    if( hostname.compareTo("192.168.0.12") == 0)
                    {
                        return true;
                    }
                    return false;
                }
            });

            connection.setRequestMethod("GET");
            connection.connect();

            int status = connection.getResponseCode();

            bmpImage = null;

            if (status != 200)
            {
                throw new IOException("GET failed with error code " + status);
            }
            else
            {
                if(connection.getContentType().split(";")[0].compareTo("application/json") == 0)
                {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;

                    while ((inputLine = in.readLine()) != null)
                    {
                        response.append(inputLine);
                    }

                    Log.d("asd", response.toString());
                    in.close();
                }

                if(connection.getContentType().split(";")[0].compareTo("image/jpeg") == 0)
                {
                    bmpImage = BitmapFactory.decodeStream(connection.getInputStream());
                }
            }
    }
        catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | IOException | KeyManagementException e )
        {
            e.printStackTrace();
        }
    }
}
