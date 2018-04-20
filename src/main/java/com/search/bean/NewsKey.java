package com.search.bean;

public class NewsKey {
    private String title;

    private String author;

    private String source;
    
    
    public NewsKey() {
		super();
	}

	public NewsKey(String title) {
		super();
		this.title = title;
	}

	public NewsKey(String title, String author, String source) {
		super();
		this.title = title;
		this.author = author;
		this.source = source;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }
}