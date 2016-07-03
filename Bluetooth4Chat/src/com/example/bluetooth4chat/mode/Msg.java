package com.example.bluetooth4chat.mode;

/**
 * Created by yzc on 2016/4/23.
 */
public class Msg {

    public static final int TYPE_RECEIVED = 0;

    public static final int TYPE_SENT = 1;

    private String content;

    

	private int type;

    private int imageId;

    public Msg(String content, int type, int imageId) {
        this.content = content;
        this.type = type;
        this.imageId = imageId;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getImageId() {
        return imageId;
    }
    public void setContent(String content) {
		this.content = content;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
}
