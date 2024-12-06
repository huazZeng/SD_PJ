package org.example.model;

import org.jsoup.nodes.Element;

public interface Visitor {
    void visit(TreeNode element, int indent, StringBuilder sb);
}