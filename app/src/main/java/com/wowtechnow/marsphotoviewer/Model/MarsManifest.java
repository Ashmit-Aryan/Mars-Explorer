package com.wowtechnow.marsphotoviewer.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarsManifest{
    @SerializedName("photo_manifest")
    @Expose
    private PhotoManifest manifest;

    public PhotoManifest getManifest() {
        return manifest;
    }

    public void setManifest(PhotoManifest manifest) {
        this.manifest = manifest;
    }
}
