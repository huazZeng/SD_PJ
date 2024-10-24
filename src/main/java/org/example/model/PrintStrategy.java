package org.example.model;

import org.jsoup.nodes.Element;

public interface PrintStrategy {
    void print(Element element, int indent);
}