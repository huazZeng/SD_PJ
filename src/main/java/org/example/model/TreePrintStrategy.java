package org.example.model;

import org.jsoup.nodes.Element;

public class TreePrintStrategy implements PrintStrategy {
    @Override
    public void print(Element element, int indent) {
        String indentString = " ".repeat(indent * 2);
        System.out.println(indentString + element.tagName());
        for (Element child : element.children()) {
            print(child, indent + 1); // 递归显示子元素
        }
    }
}
