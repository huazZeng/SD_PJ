package org.example.model;


public class IndentPrintStrategy implements PrintStrategy {



    @Override
    public void print(HtmlElement element, int indent, StringBuilder sb) {
        sb.append(" ".repeat(indent * 2));
        sb.append("<").append(element.getTagName()).append(" id=\"").append(element.getId()).append("\">");
        if (element.getChildren().isEmpty() && element.getText() != null) {
            sb.append(element.getText());
        }
        sb.append("\n");
        for (HtmlElement child : element.getChildren()) {
            print(child, indent + 1, sb);
        }
        sb.append(" ".repeat(indent * 2));
        sb.append("</").append(element.getTagName()).append(">\n");
    }
}

