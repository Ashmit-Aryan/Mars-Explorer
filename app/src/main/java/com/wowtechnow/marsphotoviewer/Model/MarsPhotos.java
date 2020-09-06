package com.wowtechnow.marsphotoviewer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarsPhotos {
    @SerializedName("photos")
    @Expose
    private List<Photos> photos;

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    public List<Photos> getPhotos() {
        return photos;
    }
}
