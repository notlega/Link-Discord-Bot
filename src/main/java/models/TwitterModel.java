package models;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterModel {

	public static Object getTweets(String[] idArray) throws URISyntaxException, IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setCookieSpec(CookieSpecs.STANDARD).build()).build();

		try {
			URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
			if (idArray.length > 1) {
				uriBuilder.addParameter("ids", String.join(",", idArray));
			} else {
				uriBuilder.addParameter("ids", idArray[0]);
			}
			uriBuilder.addParameter("expansions", "attachments.media_keys");
			uriBuilder.addParameter("media.fields", "media_key,type,url,preview_image_url");

			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpGet.setHeader("Authorization", "Bearer " + Dotenv.load().get("TWITTER_BEARER_TOKEN"));
			httpGet.setHeader("Content-Type", "application/json");

			HttpResponse response = httpClient.execute(httpGet);
			System.out.println(response.getStatusLine().getStatusCode());
			System.out.println(response.getEntity().toString());
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}
		} catch (URISyntaxException e) {
			throw new URISyntaxException("Invalid URI", e.getInput());
		} catch (ClientProtocolException e) {
			throw new ClientProtocolException("Invalid protocol");
		} catch (IOException e) {
			throw new IOException("Invalid IO");
		}

		return null;
	}
}
