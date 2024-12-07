package org.example.model;

public class FileTreeVisitor implements Visitor {

    @Override
    public void visit(TreeNode element, int indent, StringBuilder sb) {
        String prefix = " ".repeat(indent * 2);


        sb.append(prefix).append("├── ");

        sb.append(element.getId());
        if (element.isIsmodified()){
            sb.append("*");
        }
        sb.append("\n");



        // 递归输出子元素
        for (int i = 0; i < element.getChildren().size(); i++) {
            TreeNode child = element.getChildren().get(i);
            visit(child, indent + 1, sb);
        }
    }
}