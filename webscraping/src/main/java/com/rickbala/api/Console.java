package com.rickbala.api;

import com.rickbala.api.entity.GitHubAnalyser;
import com.rickbala.api.entity.GitHubItem;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Console {

	private static int count = 0;
	private static String rawContent = "https://raw.githubusercontent.com";
	private static String githubUrl = "https://github.com";

	public static void main(String[] args) throws IOException {
		String repoUrl = "https://github.com/rickbala/sway-webapp";
		List<GitHubItem> gitHubItems = new ArrayList<>();
		GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
		gitHubAnalyser.analyseRepositoryPage(repoUrl,gitHubItems);
		System.out.println(gitHubItems);
	}

	private static void analyseRepositoryPage(String repoUrl, List<GitHubItem> gitHubItems) throws IOException {
		WebScraping ws = new WebScrapingImpl();
		String body = ws.getHtmlBody(repoUrl);
		String tbody = ws.findElementByTag(body, "tbody");

		while(tbody.indexOf("<tr", count) != -1){
			GitHubItem item = new GitHubItem();

			String ariaLabel = extractAttributeValue(tbody, "aria-label");
			boolean isDirectory = false;
			if (ariaLabel.equals("directory")) isDirectory = true;
			item.setDirectory(isDirectory);

			String href = extractAttributeValue(tbody, "href");
			item.setHref(href);

			String extension = null;
			Long bytes = null;
			Long lines = null;
			if (!isDirectory) {
				extension = extractHrefExtension(href);
				bytes = getFileSize(rawContent + href.replace("/blob", ""));
				lines = getNumberOfLines(githubUrl + href);
			}
			item.setExtension(extension);
			item.setBytes(bytes);
			item.setLines(lines);

			gitHubItems.add(item);
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

	private static String extractHrefExtension(String href){
		String res = "";
		int start = href.indexOf(".");
		if (start == -1) return "no extension";
		int end = href.length();
		res = href.substring(start, end);
		return res;
	}

	private static Long getFileSize(String urlString) throws MalformedURLException {
		URL url = new URL(urlString);
		HttpURLConnection httpURLConnection;
		Long size = null;

		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("HEAD");
			size = Long.valueOf(httpURLConnection.getContentLength());
			httpURLConnection.getInputStream().close();
		} catch (Exception e) {
			System.out.println("Error while getting file info: " + urlString);
		}

		return size;
	}

	private static Long getNumberOfLines(String urlString) throws IOException {
		Long res = null;

		WebScraping ws = new WebScrapingImpl();
		String body = ws.getHtmlBody(urlString);

		int start = body.indexOf(" lines") - 7;
		int end = body.indexOf("lines");
		String linesStr = body.substring(start,end).trim();
		res = Long.valueOf(linesStr);
		return res;
	}

}
