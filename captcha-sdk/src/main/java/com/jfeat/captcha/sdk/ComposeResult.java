package com.jfeat.captcha.sdk;

import java.awt.image.BufferedImage;

/**
 * @author jackyhuang
 * @date 2022/1/8
 */
public class ComposeResult {
    private BufferedImage image;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
