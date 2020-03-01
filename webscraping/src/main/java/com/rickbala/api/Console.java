package com.rickbala.api;

import com.rickbala.api.entity.GitHubItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Console {

	public static void main(String[] args) throws IOException {
		String repoUrl = "https://github.com/rickbala/sway-webapp";
		List<GitHubItem> gitHubItems = new ArrayList<>();
		GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
		gitHubAnalyser.analyseRepositoryPage(repoUrl,gitHubItems);
		System.out.println(gitHubItems);

		List<String> distinctExtensionsList = gitHubAnalyser.createDistinctExtensionsList(gitHubItems);
		System.out.println(distinctExtensionsList);

		String summary = gitHubAnalyser.summarizeByExtension(gitHubItems, distinctExtensionsList);
		System.out.println(summary);
	}

}
