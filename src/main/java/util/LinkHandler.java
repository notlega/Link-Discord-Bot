package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	public static String getRichInfoFromLink(String link) throws IOException {
		Document document = Jsoup.connect(link).get();
		return getMetaTagContent(document, "meta[property=og:image]");
	}

	/**
	 * Gets the content of the meta tag with the specified name.
	 *
	 * @param document The document to search in.
	 * @param cssQuery The css query to search for.
	 * @return The content of the meta tag.
	 */
	public static String getMetaTagContent(Document document, String cssQuery) {

		Elements elements = document.select(cssQuery);
		for (Element e : elements) {
			return e.attr("content");
		}
		return "";
	}
}
