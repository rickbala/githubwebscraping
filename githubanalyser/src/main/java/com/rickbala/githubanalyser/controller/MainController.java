package com.rickbala.githubanalyser.controller;

import com.rickbala.api.entity.GitHubItem;
import com.rickbala.githubanalyser.business.GitHubAnalyser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

	@GetMapping("/analyseRepo")
	@ResponseBody
	public String analyseRepo(@RequestParam String repoUrl) throws IOException {
		String res = null;
		List<GitHubItem> gitHubItems = new ArrayList<>();
		GitHubAnalyser gitHubAnalyser = new GitHubAnalyser();
		try {
			gitHubAnalyser.analyseRepositoryPage(repoUrl, gitHubItems);
		}catch (Exception e){
			e.printStackTrace();
			return "Error fetching data from repository. " + e.getMessage();
		}
		List<String> distinctExtensionsList = gitHubAnalyser.createDistinctExtensionsList(gitHubItems);
		String summary = gitHubAnalyser.summarizeByExtension(gitHubItems, distinctExtensionsList);
		return summary.replace("\n", "<br/>");
	}

}
