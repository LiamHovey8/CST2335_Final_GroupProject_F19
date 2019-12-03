package com.example.cst2335_final_groupproject_f19;

/**
 * Class to obtain, and set article information
 * Author: Lindsay Deng
 */

public class News {

    private long id;
    private String name;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;

    /**
     * no-arg constructor
     */
    News (){}

    /**
     * overloaded constructor to set id, title and author
     * @param id
     * @param title
     * @param author
     */
    News (long id, String title, String author) {
        setId(id);
        setTitle(title);
        setDescription(author);

    }

    /**
     * overloaded constructor to set name, author, title, description, url, url to image, publisher, and content
     * @param name
     * @param author
     * @param title
     * @param description
     * @param url
     * @param urlToImage
     * @param publishedAt
     * @param content
     */
    News (String name, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {

        setName(name);
        setAuthor(author);
        setTitle(title);
        setDescription(description);
        setUrl(url);
        setUrlToImage(urlToImage);
        setPublishedAt(publishedAt);
        setContent(content);
    }


    /**
     * obtain id
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * obtain name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * obtain author
     *
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * obtain title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * obtain description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * obtain url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * obtian url to image
     * @return url to image
     */
    public String getUrlToImage() {
        return urlToImage;
    }

    /**
     * obtain publisher
     * @return publisher
     */
    public String getPublishedAt() {
        return publishedAt;
    }

    /**
     * obtain content
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * set id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set author
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * set title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * set description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set url
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * set url to image
     * @param urlToImage
     */
    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    /**
     * set publisher
     * @param publishedAt
     */
    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * set content
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
