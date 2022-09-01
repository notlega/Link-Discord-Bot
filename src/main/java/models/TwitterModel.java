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
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class TwitterModel {

    public static String getTweetImageLink(String link) throws URISyntaxException, IOException, ParseException {
        String[] parsedLink = link.replaceAll("^(https?://)?(www.)?twitter.com/", "").split("/");
        if (!parsedLink[1].equals("status")) {
            return null;
        }

        if (parsedLink[2].contains("?")) {
            parsedLink[2] = parsedLink[2].split("\\?")[0];
        }

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build()).build();

        try {
            URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
            uriBuilder.addParameter("ids", parsedLink[2]);
            uriBuilder.addParameter("expansions", "attachments.media_keys");
            uriBuilder.addParameter("media.fields", "media_key,type,url,preview_image_url");

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.setHeader("Authorization", "Bearer " + Dotenv.load().get("TWITTER_BEARER_TOKEN"));
            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            String responseEntityStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (response.getStatusLine().getStatusCode() == 200) {
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(responseEntityStr);
                JSONObject includes = (JSONObject) jsonObject.get("includes");
                if (includes == null) {
                    return null;
                }
                JSONArray mediaArray = (JSONArray) includes.get("media");
                return mediaArray.get(0).toString();
            }
        } catch (URISyntaxException e) {
            throw new URISyntaxException("Invalid URI", e.getInput());
        } catch (ClientProtocolException e) {
            throw new ClientProtocolException("Invalid protocol");
        } catch (IOException e) {
            throw new IOException("Invalid IO");
        } catch (ParseException e) {
            throw new ParseException(0, e);
        }

        return null;
    }
}
