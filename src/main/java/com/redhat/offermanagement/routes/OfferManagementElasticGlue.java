package com.redhat.offermanagement.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.elasticsearch.ElasticsearchComponent;
import org.apache.camel.component.http4.HttpComponent;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.TrustManagersParameters;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Component
public class OfferManagementElasticGlue extends RouteBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(OfferManagementElasticGlue.class);

	private String kafkaBootstrap = "my-cluster-kafka-bootstrap:9092";
	private String offerTopic = "offer-output";
	private String consumerMaxPollRecords = "500";
	private String consumerCount = "1";
	private String consumerSeekTo = "end";
	private String consumerGroup = "elastic-glue";


	@Override
	public void configure() throws Exception {
		LOG.info("Configuring Creditor Core Banking Routes");
		KeyStore truststore = KeyStore.getInstance("jks");
		try (InputStream is = getClass()
				.getClassLoader().getResourceAsStream("keystore.jks") ) {

			truststore.load(is, "changeit".toCharArray());
		}

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
                "elastic", System.getenv("PASSWORD")));

        final SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(truststore,
                        TrustAllStrategy.INSTANCE)
                .build();


         RestClientBuilder builder = RestClient.builder(
        new HttpHost(System.getenv("ROUTEADDR"), 443, "https")).
        setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setDefaultCredentialsProvider(credentialsProvider)
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier((host,session) -> true)
        );

        RestHighLevelClient client = new RestHighLevelClient(builder);


		ElasticsearchComponent elasticsearchComponent = new ElasticsearchComponent();

		elasticsearchComponent.setClient(client.getLowLevelClient());
		elasticsearchComponent.setUser("elastic");
		elasticsearchComponent.setPassword(System.getenv("PASSWORD"));
		elasticsearchComponent.setEnableSSL(true);

		this.getContext().addComponent("elasticsearch-rest", elasticsearchComponent);


		KafkaComponent kafka = new KafkaComponent();
		kafka.setBrokers(kafkaBootstrap);
		this.getContext().addComponent("kafka", kafka);

		try {



			from("kafka:" + offerTopic + "?brokers=" + kafkaBootstrap + "&maxPollRecords="
					+ consumerMaxPollRecords + "&consumersCount=" + consumerCount + "&seekTo=" + consumerSeekTo
					+ "&groupId=" + consumerGroup
			)
					.log("${body}")
					.to("elasticsearch-rest://elasticsearch-sample?operation=Index&indexName=off&indexType=txn");


		}catch (Exception e) {
			e.printStackTrace();
		}


	}


	private HttpComponent configureHttp4() {
		KeyStoreParameters ksp = new KeyStoreParameters();
		ksp.setResource("src/main/fabric8/keystore.jks");
		ksp.setPassword("changeit");
		TrustManagersParameters tmp = new TrustManagersParameters();
		tmp.setKeyStore(ksp);
		SSLContextParameters scp = new SSLContextParameters();
		scp.setTrustManagers(tmp);
		HttpComponent httpComponent = getContext().getComponent("https4", HttpComponent.class);
		httpComponent.setSslContextParameters(scp);
		return httpComponent;
	}
}
