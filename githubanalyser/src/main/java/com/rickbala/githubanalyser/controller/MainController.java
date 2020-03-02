package com.rickbala.githubanalyser.controller;

import com.rickbala.githubanalyser.business.GitHubAnalyser;
import com.rickbala.githubanalyser.entity.GitHubItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Controller for the webapp
 */
@Controller
public class MainController {

	/**
	 * This is the only method that the index.html will use.
	 * @param repoUrl the repository URL as typed in by the user
	 * @return returns the summarized info collected by the API
	 */
	@GetMapping("/analyseRepo")
	@ResponseBody
	public String analyseRepo(@RequestParam String repoUrl) {
		List<GitHubItem> gitHubItems = new ArrayList<>();
		GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
		try {
			gitHubAnalyser.analyseRepositoryPage(repoUrl, gitHubItems);
		}catch (Exception e){
			return "Error fetching data from repository. " + e.getMessage();
		}
		List<String> distinctExtensionsList = gitHubAnalyser.createDistinctExtensionsList(gitHubItems);
		String summary = gitHubAnalyser.summarizeByExtension(gitHubItems, distinctExtensionsList);
		return summary.replace("\n", "<br/>");
	}

}
