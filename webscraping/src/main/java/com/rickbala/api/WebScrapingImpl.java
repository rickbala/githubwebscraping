package com.rickbala.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WebScrapingImpl implements  WebScraping{

	@Override
	public String getHtmlBody(String urlString) throws IOException{
		StringBuilder body = new StringBuilder();

		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		InputStreamReader inputStreamReader = new InputStreamReader((InputStream)urlConnection.getContent());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str;
		while ((str=bufferedReader.readLine()) != null){
			body.append(str);
		}
		bufferedReader.close();

		return body.toString();
	}

	@Override
	public String findElementByTag(String htmlBody, String tagName) {
		String res;
		int start = htmlBody.indexOf("<" + tagName);
		if (start < 0 ) return null;
		int end = htmlBody.indexOf("</" +tagName + ">", start + 1) + tagName.length() + 3;
		res = htmlBody.substring(start, end).trim();
		return res;
	}

	@Override
	public String extractHrefExtension(String href){
		String res;
		int start = href.lastIndexOf('.');
		if (start < 0) return "no extension";
		int end = href.length();
		res = href.substring(start, end);
		return res;
	}

	@Override
	public Long getFileSize(String urlString) {
		HttpURLConnection httpURLConnection;
		Long size;
		try {
			URL url = new URL(urlString);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("HEAD");
			size = Long.valueOf(httpURLConnection.getContentLength());
			httpURLConnection.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while getting file size: " + urlString);
			return null;
		}
		return size;
	}

	@Override
	public Long getNumberOfLines(String urlString) {
		Long res = null;
		String body = null;
		try {
			body = getHtmlBody(urlString);
			int start = body.indexOf(" lines") - 7;
			if (start < 0) return null;
			int end = body.indexOf("lines");
			String linesStr = body.substring(start,end).trim();
			res = Long.valueOf(linesStr);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error while getting number of lines: " + urlString);
			return null;
		}
		return res;
	}

	@Override
	public void downloadFile(String url, String outputFile) throws IOException {
		//TODO implement this method. Not really necessary for now
	}

}
