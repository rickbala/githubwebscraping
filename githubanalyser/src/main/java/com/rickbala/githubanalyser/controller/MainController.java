package com.rickbala.githubanalyser.controller;

import com.rickbala.api.WebScraping;
import com.rickbala.api.WebScrapingImpl;
import com.rickbala.api.entity.Element;
import com.rickbala.api.entity.Body;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class MainController {

	@GetMapping("/analyseRepo")
	@ResponseBody
	public String analyseRepo(@RequestParam String repoUrl) throws IOException {

		WebScraping ws = new WebScrapingImpl();

		Body body = ws.getHtmlBody(repoUrl);

		String tbody = ws.findElementByTag(body.getHtml(), "tbody");

		int start = tbody.indexOf("aria-label=\"");
		int end = tbody.indexOf("\'");
		tbody.substring(start, end);

		return tbody;
	}


}
