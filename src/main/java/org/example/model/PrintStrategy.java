package org.example.model;

import org.jsoup.nodes.Element;

public interface PrintStrategy {
    void print(HtmlElement element, int indent);
}