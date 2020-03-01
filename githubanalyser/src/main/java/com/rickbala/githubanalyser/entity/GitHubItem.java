package com.rickbala.githubanalyser.entity;

/**
 * Represents either a folder a file from a github repository
 */
public class GitHubItem {

	private boolean isDirectory;
	private String href;
	private String extension;
	private Long bytes;
	private Long lines;

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean directory) {
		isDirectory = directory;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Long getBytes() {
		return bytes;
	}

	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}

	public Long getLines() {
		return lines;
	}

	public void setLines(Long lines) {
		this.lines = lines;
	}

	/**
	 * @return a representation of the object and its fields values
	 */
	@Override
	public String toString() {
		return "GitHubItem{" +
				"isDirectory=" + isDirectory +
				", href='" + href + '\'' +
				", extension='" + extension + '\'' +
				", bytes=" + bytes +
				", lines=" + lines +
				"}\n";
	}

}
