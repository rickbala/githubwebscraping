package com.rickbala.githubanalyser.business;

import com.rickbala.api.WebScraping;
import com.rickbala.api.WebScrapingImpl;
import com.rickbala.githubanalyser.entity.GitHubItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class that implements the web scraping API module
 */
public class GitHubAnalyser {

	private int count = 0;
	private static final String RAW_CONTENT_URL = "https://raw.githubusercontent.com";
	private static final String GITHUB_URL = "https://github.com";

	/**
	 * Main method that iterates over all the table rows of a give repository page searching for data
	 * @param repoUrl the repoUrl that comes from the input field on the index.html file
	 * @param gitHubItems a list of GitHubItem entities that will be later summarized by the MainController
	 * @throws IOException in a case the page searched for is not available
	 */
	public void analyseRepositoryPage(String repoUrl, List<GitHubItem> gitHubItems) throws IOException {
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
			if (!isDirectory && href != null && !href.contains("/commit/")) {
				extension = ws.extractHrefExtension(href);
				bytes = ws.getFileSize(RAW_CONTENT_URL + href.replace("/blob", ""));
				lines = ws.getNumberOfLines(GITHUB_URL + href);
			} else {
				GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
				gitHubAnalyser.analyseRepositoryPage(GITHUB_URL + href, gitHubItems);
			}
			item.setExtension(extension);
			item.setBytes(bytes);
			item.setLines(lines);

			gitHubItems.add(item);
		}
	}

	/**
	 * Auxiliary method for that extracts the value of an html attribute
	 * @param tbody the htmlBody to search for
	 * @param search the attribute name
	 * @return the value inside the quotes of an attribute
	 */
	protected String extractAttributeValue(String tbody, String search) {
		String res;
		int start = tbody.indexOf(search, count) + search.length() + 1;
		if (start < 0) return null;
		int end = tbody.indexOf('\"',  start + 1);
		res = tbody.substring(start+1, end);
		count = end + 1;
		return res;
	}

	/**
	 * Iterates through all the extensions found and makes a list of distinct values
	 * Can evolve into a lambda function later
	 * @param gitHubItems a list of GitHubItem entities created by the analyseRepository method
	 * @return a list of String containing non repeatable file extension names
	 */
	public List<String> createDistinctExtensionsList(List<GitHubItem> gitHubItems){
		List<String> distinctExtensionsList = new ArrayList<>();
		for (GitHubItem gitHubItem : gitHubItems)
			if (gitHubItem.getExtension() != null && !distinctExtensionsList.contains(gitHubItem.getExtension()))
				distinctExtensionsList.add(gitHubItem.getExtension());
		return distinctExtensionsList;
	}

	/**
	 * Summarizes bytes and number of lines from a given list fo GitHubItems, by extension name
	 * @param gitHubItems the list of GitHubItem entities that will serve as input data
	 * @param distinctExtensions the list of distinct extension names in the list of GitHubItems
	 * @return a String that summarizes everything and shall be given back to the user on the front-end
	 */
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
			res.append("-Extension: ").append(extension).append(", totalBytes: ");
			res.append(bytes).append(", totalLines: ").append(lines).append("\n");
		}
		res.append("Total Bytes: ").append(totalBytes).append(", Total Lines: ").append(totalLines);
		return res.toString();
	}

}
