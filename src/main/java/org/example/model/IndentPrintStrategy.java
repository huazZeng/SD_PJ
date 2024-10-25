package org.example.model;


import org.jsoup.nodes.Element;

public class IndentPrintStrategy implements PrintStrategy {
    private int indentSize;

    public IndentPrintStrategy(int indentSize) {
        this.indentSize = indentSize;
    }

    @Override
    public void print(HtmlElement element, int indent) {
        String indentString = " ".repeat(indent * indentSize);
        System.out.println(indentString + "<" + element.getTagName() + ">");
        for (HtmlElement child : element.getChildren()) {
            print(child, indent + 1); // 递归显示子元素
        }
        System.out.println(indentString + "</" + element.getTagName() + ">");
    }
}

