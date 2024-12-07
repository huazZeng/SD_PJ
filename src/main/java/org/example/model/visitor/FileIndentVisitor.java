package org.example.model.visitor;


import org.example.model.TreeNode;

public class FileIndentVisitor implements Visitor {



    @Override
    public void visit(TreeNode element, int indent, StringBuilder sb) {
        sb.append(" ".repeat(indent * 2));
        sb.append(element.getId());
        if (element.isIsmodified()){
            sb.append("*");
        }
        sb.append("\n");
        for (TreeNode child : element.getChildren()) {
            visit(child, indent + 1, sb);
        }
    }
}

