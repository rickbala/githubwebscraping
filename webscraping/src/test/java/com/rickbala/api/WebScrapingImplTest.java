package com.rickbala.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebScrapingImplTest {

	@Test
	public void testFindElementByTag(){
		WebScraping ws = new WebScrapingImpl();

		String body = "<body><h1>Title</h1><h2>Some text</h2></body>";
		String searchTag = "h1";

		String result = ws.findElementByTag(body, searchTag);

		assertEquals("<h1>Title</h1>", result);
	}

	@Test
	public void testExtractHrefExtension(){
		WebScraping ws = new WebScrapingImpl();

		String linkHtml = "path/to/my/file.js";

		String result = ws.extractHrefExtension(linkHtml);

		assertEquals(".js", result);
	}

}
