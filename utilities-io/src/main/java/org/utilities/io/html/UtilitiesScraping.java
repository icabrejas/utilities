package org.utilities.io.html;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.utilities.core.lang.exception.QuietException;
import org.utilities.core.lang.iterable.IterablePipe;

public class UtilitiesScraping {

	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.103 Safari/537.36";

	public static Document get(String url) throws QuietException {
		try {
			return Jsoup.connect(url)
					.userAgent(USER_AGENT)
					.get();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

	public static IterablePipe<Element> get(String url, String cssQuery) throws QuietException {
		return IterablePipe.newInstance(get(url).select(cssQuery));
	}

	public static IterablePipe<Element> getImg(String url) throws QuietException {
		return IterablePipe.newInstance(get(url).select("img"));
	}

	public static InputStream getInputStream(String src) throws QuietException {
		try {
			URL url = new URL(URLDecoder.decode(src, "UTF-8"));
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", USER_AGENT);
			connection.connect();
			return connection.getInputStream();
		} catch (IOException e) {
			throw new QuietException(e);
		}
	}

}
