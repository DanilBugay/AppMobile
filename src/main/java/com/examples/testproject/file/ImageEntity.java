
package com.examples.testproject.file;

public class ImageEntity {
    private long uploaded;
    private String url;

    public ImageEntity() {
    }

    public ImageEntity(long uploaded, String url) {
        this.uploaded = uploaded;
        this.url = url;
    }

    public long getUploaded() {
        return uploaded;
    }

    public void setUploaded(long uploaded) {
        this.uploaded = uploaded;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
