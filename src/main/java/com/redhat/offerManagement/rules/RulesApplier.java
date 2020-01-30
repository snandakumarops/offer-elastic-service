package com.redhat.offerManagement.rules;

import com.google.gson.Gson;


import com.myspace.offermanagement.customerModel.CustomerModel;
import com.myspace.offermanagement.customerModel.PastHistoryModel;
import com.redhat.offerManagement.CustomerOfferModel;
import com.redhat.offerManagement.EventStreamModel;
import com.redhat.offerManagement.JdgCustomerRepository;
import com.redhat.offermanagement.CustomerRepository;
import com.redhat.offermanagement.JdgPastHistRepository;
import com.redhat.offermanagement.PastHistoryRepository;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;


public class RulesApplier {


    private static final Logger LOGGER = LoggerFactory.getLogger(RulesApplier.class);


    /**
     * Applies the loaded Drools rules to a given String.
     *
     * @param
     * @return the String after the rule has been applied
     */



    public String processTransactionDMN(String key, String value) {
        try {

            String httpsURL = "https://elasticsearch-sample-elastic.apps.cluster-florida-ee6b.florida-ee6b.example.opentlc.com/offer/off";

            HttpHost targetHost = new HttpHost("localhost", 8082, "http");
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials("elastic", "vzg89xjlq2gfdj4jgk479bhx"));

            AuthCache authCache = new BasicAuthCache();
            authCache.put(targetHost, new BasicScheme());
            HttpClientContext context = HttpClientContext.create();
            context.setCredentialsProvider(credsProvider);
            context.setAuthCache(authCache);

            HttpClient client = HttpClientBuilder.create().build();
            ResponseEntity<String> response = client.execute(
                    new HttpPost(httpsURL);



            TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> socketFactoryRegistry =
                    RegistryBuilder.<ConnectionSocketFactory> create()
                            .register("https", sslsf)
                            .register("http", new PlainConnectionSocketFactory())
                            .build();

            BasicHttpClientConnectionManager connectionManager =
                    new BasicHttpClientConnectionManager(socketFactoryRegistry);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
                    .setConnectionManager(connectionManager).build();

            HttpComponentsClientHttpRequestFactory requestFactory =
                    new HttpComponentsClientHttpRequestFactory(httpClient);
            ResponseEntity<String> response = new RestTemplate(requestFactory)
                    .exchange(httpsURL, HttpMethod.POST, null, String.class);


        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;

    }


}
