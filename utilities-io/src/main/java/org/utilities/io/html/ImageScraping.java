package org.utilities.io.html;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.jsoup.nodes.Element;
import org.utilities.core.lang.iterable.IterablePipe;

public class ImageScraping {

	public static String big(String thumb) {
		String replaced;
		if (thumb.contains("imgbox.com")) {
			replaced = "http://i.imgbox.com" + thumb.substring(thumb.indexOf("imgbox.com") + "imgbox.com".length());
		} else if (thumb.contains("imgdino.com")) {
			replaced = thumb.replaceAll("_thumb", "");
		} else if (thumb.contains("imgzap.com")) {
			replaced = thumb.replaceAll("_thumb", "");
		} else if (thumb.contains("imgtiger.com")) {
			replaced = thumb.replaceAll("_thumb", "");
		} else if (thumb.contains("imgzap.com")) {
			replaced = thumb.replaceAll("_thumb", "");
		} else if (thumb.contains("imageeer.com")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("imgsee.me")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("imgpaying.com")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("picexposed.com")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("imgclick.net")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("imgspice.com")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("imgmega.com")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("chronos.to")) {
			String t = "/t/";
			int from = thumb.indexOf(t);
			int to = thumb.indexOf('/', from + t.length());
			String[] parts = thumb.substring(from, to)
					.split("/");
			String number = parts[parts.length - 1];
			replaced = thumb.replaceAll("/t/" + number, "/i/")
					.replaceAll("chronos.to", "i" + number + ".chronos.to") + ".jpg";
		} else if (thumb.contains("pixhost.org")) {
			replaced = thumb.replaceAll("t6.pixhost.org", "img6.pixhost.org")
					.replaceAll("thumbs", "images");
		} else if (thumb.contains("imgsen.se")) {
			replaced = thumb.replaceAll("imgsen.se", "i.imgsen.se")
					.replaceAll("upload/small", "big");
		} else if (thumb.contains("img.yt")) {
			// replaced = thumb.replaceAll("img.yt",
			// "i.img.yt").replaceAll("upload/small", "big");
			// replaced = thumb.replaceAll("img.yt",
			// "s.img.yt").replaceAll("upload/small", "big");
			replaced = thumb.replaceAll("small", "big");
		} else if (thumb.contains("imgtrex.com")) {
			replaced = imgtrexScrapping(thumb);
		} else if (thumb.contains("imagevenue.com")) {
			replaced = imagevenueScrapping(thumb);
		} else if (thumb.contains("imgchili")) {
			// replaced = thumb.replaceAll("imgchili.com",
			// "imgchili.net").replaceAll("http://t", "http://i");
			replaced = thumb.replaceAll("http://t", "http://i");
		} else if (thumb.contains("imagebam.com")) {
			replaced = imagebamScrapping(thumb);
		} else if (thumb.contains("minus.com")) {
			replaced = minusScrapping(thumb);
		} else if (thumb.contains("imagetwist")) {
			replaced = thumb.replaceAll("/th/", "/i/");
		} else if (thumb.contains("pic-maniac")) {
			replaced = thumb.replaceAll("_t.", ".");
		} else if (thumb.contains("fireimg")) {
			replaced = thumb.replaceAll("small", "big");
		} else {
			replaced = thumb.replaceAll("/small/", "/big/");
		}
		return replaced;
	}

	private static IterablePipe<String> src(String url) {
		return UtilitiesScraping.getImg(url)
				.map(Element::attr, "src");
	}

	private static IterablePipe<String> src(String url, Predicate<String> filter) {
		return ImageScraping.src(url)
				.filter(filter);
	}

	private static <U> IterablePipe<String> src(String url, BiPredicate<String, U> filter, U u) {
		return ImageScraping.src(url)
				.filter(filter, u);
	}

	private static String first(Iterable<String> it) {
		List<String> list = IterablePipe.newInstance(it)
				.limit(1)
				.toList();
		return !list.isEmpty() ? list.get(0) : null;
	}

	private static String imgtrexScrapping(String url) {
		String imgName = url.substring(url.lastIndexOf("/") + 1);
		return ImageScraping.src(url, String::endsWith, imgName)
				.apply(ImageScraping::first);
	}

	private static String imagevenueScrapping(String url) {
		String imgName = url.substring(url.lastIndexOf("=") + 1);
		String host = url.substring(0, url.indexOf("/", 7) + 1);
		return ImageScraping.src(url, String::endsWith, imgName)
				.map(src -> host + src)
				.apply(ImageScraping::first);
	}

	private static String imagebamScrapping(String url) {
		return ImageScraping.src(url, String::contains, "download")
				.apply(ImageScraping::first);
	}

	private static String minusScrapping(String url) {
		String imgName = url.substring(url.lastIndexOf("/") + 1);
		return ImageScraping.src(url, src -> src.substring(0, src.lastIndexOf("."))
				.endsWith(imgName.substring(1)))
				.apply(ImageScraping::first);
	}

	public static BufferedImage read(String src) throws Exception {
		InputStream inputStream = UtilitiesScraping.getInputStream(src);
		return ImageIO.read(inputStream);
	}

}
