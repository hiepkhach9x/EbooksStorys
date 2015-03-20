package com.CTCStudio.ebookstorys.Util;

public class Ebook {
	String Title;
	String Author;
	String Link;
	String Published;
	String Description;

	public Ebook(String title, String author, String link,String published, String description) {
		Title = title;
		Author = author;
		Link = link;
		Published = published;
		Description = description;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getLink() {
		return Link;
	}

	public void setLink(String link) {
		Link = link;
	}

	public void setPublished(String published) {
		Published = published;
	}

	public String getPublished() {
		return Published;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
