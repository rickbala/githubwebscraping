package com.rickbala.githubanalyser.controller;

import com.rickbala.api.entity.GitHubAnalyser;
import com.rickbala.api.entity.GitHubItem;
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
		gitHubAnalyser.analyseRepositoryPage(repoUrl, gitHubItems);
		res = gitHubItems.toString();
		System.out.println(res);
		return res;
	}

	private String summarizeByExtension(List<GitHubItem> gitHubItems){
		String res = null;



		return res;
	}

}
