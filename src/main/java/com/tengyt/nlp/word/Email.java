package com.tengyt.nlp.word;

/**
 * Created by tengyt on 8/25/15.
 */
public class Email {

    private String content;

    public Email(String wholeEmail) {
        this.content = extractContent(wholeEmail);
    }

    private String extractContent(String wholeEmail) {
        //TODO add content extraction logic
        return "";
    }

    public String getContent() {
        return content;
    }
}
