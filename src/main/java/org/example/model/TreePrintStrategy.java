package org.example.model;

import org.jsoup.nodes.Element;

public class TreePrintStrategy implements PrintStrategy {

    @Override
    public void print(HtmlElement element, int indent, StringBuilder sb) {
        String prefix = " ".repeat(indent * 2);
        String id = element.getId() != null ? "#" + element.getId() : "";

        // 输出标签和ID
        sb.append(prefix).append("├── ").append(element.getTagName()).append(id).append("\n");

        // 输出文本内容（如果存在）
        if (element.getText() != null && !element.getText().isEmpty()) {
            sb.append(prefix).append("│   └── ").append(element.getText()).append("\n");
        }

        // 递归输出子元素
        for (int i = 0; i < element.getChildren().size(); i++) {
            HtmlElement child = element.getChildren().get(i);
            print(child, indent + 1, sb);
        }
    }
}
