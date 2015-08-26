package com.tengyt.nlp.word;

import com.google.common.base.Joiner;

import java.util.List;

public class Email {

    private String content;

    public Email(List<String> wholeEmail) {
        this.content = extractContent(wholeEmail);
    }

    private String extractContent(List<String> wholeEmail) {
        return Joiner.on("").skipNulls().join(wholeEmail.subList(10, wholeEmail.size()));
    }

    public String getContent() {
        return content;
    }
}
