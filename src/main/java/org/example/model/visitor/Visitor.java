package org.example.model.visitor;

import org.example.model.TreeNode;
import org.jsoup.nodes.Element;

public interface Visitor {
    void visit(TreeNode element, int indent, StringBuilder sb);
}