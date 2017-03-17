package com.droiddigger.mhlushan.aqib;

/**
 * Created by mihodihasan on 3/17/17.
 */

public class Offers {

    Integer img;
    String offer;

    public Offers(Integer img, String offer) {
        this.img = img;
        this.offer = offer;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
