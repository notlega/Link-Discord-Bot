package util;

import models.TwitterModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import records.MetaTags;

import java.io.IOException;

public class LinkHandler {

    public LinkHandler() {

    }

    /**
     * Gets the rich info from a link.
     *
     * @param link The link to get the rich info from.
     * @return The rich info from the link.
     */
    public MetaTags getRichInfoFromLink(String link) throws IOException {

        Document document = Jsoup.connect(link).get();
        String url = getMetaTagContent(document, "meta[property=og:url]");

        if (url.isEmpty()) {
            url = link;
        }

        return new MetaTags(
                getMetaTagContent(document, "meta[name=title]"),
                getMetaTagContent(document, "meta[name=description]"),
                url,
                getMetaTagContent(document, "meta[property=og:title]"),
                getMetaTagContent(document, "meta[property=og:description]"),
                getMetaTagContent(document, "meta[property=og:image]"),
                getMetaTagContent(document, "meta[property=og:site_name]")
        );
    }

    public String getRichInfoFromTwitterLink(String link) throws Exception {

        String[] parsedLink = link.replaceAll("^(https?://)?(www.)?twitter.com/", "").split("/");

        if (parsedLink.length == 1) {
            return "";
        }

        if (parsedLink[1].equals("status")) {

            if (parsedLink[2].contains("?")) {
                parsedLink[2] = parsedLink[2].split("\\?")[0];
            }

            System.out.println(TwitterModel.getTweets(new String[]{parsedLink[2]}));
            Object tweets = TwitterModel.getTweets(new String[]{parsedLink[2]});

            return TwitterModel.getTweets(new String[]{parsedLink[2]}).toString();
        }
        return "";
    }

    /**
     * Gets the content of the meta tag with the specified name.
     *
     * @param document The document to search in.
     * @param cssQuery The css query to search for.
     * @return The content of the meta tag.
     */
    public String getMetaTagContent(Document document, String cssQuery) {

        Elements elements = document.select(cssQuery);
        for (Element e : elements) {
            return e.attr("content");
        }
        return "";
    }
}
