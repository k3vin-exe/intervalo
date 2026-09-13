package com.devmobile.intervalo.model;


public class Post {
    private String id;
    private String authorName;
    private String authorCourse;
    private String text;
    private long timestamp;
    private int likes;
    private int comments;

    public Post() {
    }

    public Post(String authorName, String authorCourse, String text, long timestamp) {
        this.authorName = authorName;
        this.authorCourse = authorCourse;
        this.text = text;
        this.timestamp = timestamp;
        this.likes = 0;
        this.comments = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorCourse() { return authorCourse; }
    public void setAuthorCourse(String authorCourse) { this.authorCourse = authorCourse; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public int getComments() { return comments; }
    public void setComments(int comments) { this.comments = comments; }
}
