package com.andro.theflash.walls.model;

public class Walls {

    private String name;
    private String thumb_url;
    private String img_url;


    public Walls(String name, String thumb_url, String img_url) {
        this.name = name;
        this.thumb_url = thumb_url;
        this.img_url = img_url;
    }

    public Walls() {


    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }


}
