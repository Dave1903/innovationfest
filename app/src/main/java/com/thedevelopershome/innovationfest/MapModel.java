package com.thedevelopershome.innovationfest;

public class MapModel {
 // private String lat,lang,river,area,village,imgUrl,id;
  private String name,imgUrl,position,fbUrl;

    public String getName() {
        return name;
    }

    public void setName(String id) {
        this.name = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPosition() {

        return position;
    }

    public void setPosition(String lat) {
        this.position = lat;
    }

    public String getFbUrl() {
        return fbUrl;
    }

    public void setFbUrl(String lang) {
        this.fbUrl = lang;
    }


}
