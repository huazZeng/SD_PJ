package org.example.model;

public class TreeVisitor implements Visitor {

    @Override
    public void visit(TreeNode element, int indent, StringBuilder sb) {
        String prefix = " ".repeat(indent * 2);

        String id = element.getId() != null ? "#" + element.getId() : "";
        sb.append(prefix).append("├── ");
        if(element.isSpellerror()){
            sb.append("[X]");
        }
        sb.append(element.getTagName());
        if(element.isIsshowid()){
            sb.append(id);
        }
        sb.append("\n");
        // 输出文本内容（如果存在）
        if (element.getText() != null && !element.getText().isEmpty()) {
            sb.append(prefix).append("│   └── ");

            sb.append(element.getText()).append("\n");
        }

        // 递归输出子元素
        for (int i = 0; i < element.getChildren().size(); i++) {
            TreeNode child = element.getChildren().get(i);
            visit(child, indent + 1, sb);
        }
    }
}
