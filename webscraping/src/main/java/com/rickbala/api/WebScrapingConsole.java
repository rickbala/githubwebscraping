package com.rickbala.api;

import com.rickbala.api.entity.Body;

import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebScrapingConsole {

	static int count = 0;
	private static String rawContent = "https://raw.githubusercontent.com";

	public static void main(String[] args) throws IOException {
		String repoUrl = "https://github.com/rickbala/feedthebirds";
		//String rawContentRepoUrl = repoUrl.replace("https://github.com/",rawContent) + "/master";
		//System.out.println(rawContentRepoUrl);

		WebScraping ws = new WebScrapingImpl();
		Body body = ws.getHtmlBody(repoUrl);
		String tbody = ws.findElementByTag(body.getHtml(), "tbody");

		while(tbody.indexOf("<tr", count) != -1){
			String ariaLabel = extractAttributeValue(tbody, "aria-label");
			boolean isFolder = false;
			if (ariaLabel.equals("directory")) isFolder = true;
//			System.out.println(ariaLabel);
			System.out.println("isFolder: " + isFolder);

			String href = extractAttributeValue(tbody, "href");
			System.out.println("Href: " + href);

			String extension = null;
			BigInteger size = null;
			if (!isFolder) {
				extension = extractExtension(href);
				size = getFileSize(rawContent + href.replace("/blob", ""));
			}
			System.out.println("Extension: " + extension);
			System.out.println("Bytes: " + size);

			System.out.println();


		}
	}

	private static String extractAttributeValue(String tbody, String search) {
		String res = "";
		int start = tbody.indexOf(search, count) + search.length() + 1;
		if (start == -1) return null;
		int end = tbody.indexOf("\"",  start + 1);
		res = tbody.substring(start+1, end);
		count = end + 1;
		return res;
	}

	private static String extractExtension(String href){
		String res = "";
		int start = href.indexOf(".");
		if (start == -1) return "no extension";
		int end = href.length();
		res = href.substring(start, end);
		return res;
	}

	private static BigInteger getFileSize(String urlString) throws MalformedURLException {
		URL url = new URL(urlString);
		HttpURLConnection httpURLConnection;
		BigInteger size = new BigInteger("0");

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("HEAD");
			size = BigInteger.valueOf(httpURLConnection.getContentLength());
			httpURLConnection.getInputStream().close();
		} catch (Exception e) {
			System.out.println("Error while getting file info: " + urlString);
		}

		return size;
	}

}
