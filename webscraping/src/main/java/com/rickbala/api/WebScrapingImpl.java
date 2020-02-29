package com.rickbala.api;

import com.rickbala.api.entity.Body;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

public class WebScrapingImpl implements WebScraping {

	public static void main(String[] args) throws IOException {
		WebScraping webScraping = new WebScrapingImpl();

		String htmlBody = webScraping.getHtmlBody("https://github.com/rickbala/feedthebirds").getHtml();
		//System.out.println(htmlBody);

		String tbody = webScraping.findElementByTag(htmlBody, "tbody");
		System.out.println(tbody);
	}

	@Override
	public void downloadFile(String url, String outputFile) throws IOException {
		URL website = new URL(url);
		ReadableByteChannel readableByteChannel = Channels.newChannel(website.openStream());
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		try {
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Body getHtmlBody(String urlString) throws IOException{
		String body = "";

		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		InputStreamReader inputStreamReader = new InputStreamReader((InputStream)urlConnection.getContent());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str;
		while ((str=bufferedReader.readLine()) != null){
			body += str;
		}
		bufferedReader.close();

		Body htmlBody = new Body(body);
		return htmlBody;
	}

//	@Override
//	public Element findElementByTag(Body body, Element element) {
//		int startPos = body.getHtml().indexOf(element.getHtml());
//		int endPos = htmlBody.body.indexOf(">", startPos + 1);
//		String elementBody = this.body.substring(startPos, endPos);
//		return new Element(elementBody);
//	}

	@Override
	public String findElementByTag(String htmlBody, String tagName) {
		String res;
		int startPos = htmlBody.indexOf(tagName);
		int endPos = htmlBody.indexOf(tagName, startPos + tagName.length());
		res = htmlBody.substring(startPos, endPos);
		return res;
	}

	@Override
	public BigInteger getFileSize(String urlString) throws MalformedURLException {
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

	@Override
	public String getFileName(String url) {
		return null;
	}

	@Override
	public String getFileExtension(String url) {
		return null;
	}

	@Override
	public boolean getIsLink(String url) {
		return false;
	}

	@Override
	public List<String> getAllOccurrencesOfTag(String htmlBody) {
		return null;
	}

	@Override
	public String getAttributeFromElement(String element, int startPos) {
		return null;
	}

}
