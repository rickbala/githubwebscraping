package com.rickbala.githubanalyser.business;

import com.rickbala.api.WebScrapingImpl;
import com.rickbala.api.entity.GitHubItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHubAnalyser {

	private int count = 0;
	private String rawContent = "https://raw.githubusercontent.com";
	private String githubUrl = "https://github.com";

	public void analyseRepositoryPage(String repoUrl, List<GitHubItem> gitHubItems) throws IOException {
		WebScrapingImpl ws = new WebScrapingImpl();
		System.out.println("Fetching data for " + repoUrl);

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
				extension = ws.extractHrefExtension(href);
				bytes = ws.getFileSize(rawContent + href.replace("/blob", ""));
				lines = ws.getNumberOfLines(githubUrl + href);
			} else {
				GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
				gitHubAnalyser.analyseRepositoryPage(githubUrl + href, gitHubItems);
			}
			item.setExtension(extension);
			item.setBytes(bytes);
			item.setLines(lines);

			gitHubItems.add(item);
		}
	}

	private String extractAttributeValue(String tbody, String search) {
		String res = "";
		int start = tbody.indexOf(search, count) + search.length() + 1;
		if (start < 0) return null;
		int end = tbody.indexOf("\"",  start + 1);
		res = tbody.substring(start+1, end);
		count = end + 1;
		return res;
	}

	public List<String> createDistinctExtensionsList(List<GitHubItem> gitHubItems){
		List<String> distinctExtensionsList = new ArrayList<>();
		for (GitHubItem gitHubItem : gitHubItems)
			if (gitHubItem.getExtension() != null && !distinctExtensionsList.contains(gitHubItem.getExtension()))
				distinctExtensionsList.add(gitHubItem.getExtension());
		return distinctExtensionsList;
	}

	public String summarizeByExtension(List<GitHubItem> gitHubItems, List<String> distinctExtensions){
		StringBuilder res = new StringBuilder();
		Long totalBytes = 0L;
		Long totalLines = 0L;
		for (String extension : distinctExtensions){
			Long bytes = 0L;
			Long lines = 0L;
			for (GitHubItem gitHubItem : gitHubItems){
				if (gitHubItem.getExtension()!= null && gitHubItem.getExtension().equals(extension)) {
					if (gitHubItem.getBytes() != null) bytes += gitHubItem.getBytes();
					if (gitHubItem.getLines() != null) lines += gitHubItem.getLines();
				}
			}
			totalBytes += bytes;
			totalLines += lines;
			res.append("-Extension: " + extension + ", totalBytes: " + bytes + ", totalLines: " + lines + "\n");
		}
		res.append("Total Bytes: " + totalBytes +", Total Lines: " + totalLines);
		return res.toString();
	}

}
