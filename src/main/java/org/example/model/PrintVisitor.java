package org.example.model;

public interface PrintVisitor {
    void visit(TreeNode node, int indent, StringBuilder sb);
}
