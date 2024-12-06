package org.example.model;


public class IndentVisitor implements Visitor {



    @Override
    public void visit(TreeNode element, int indent, StringBuilder sb) {
        sb.append(" ".repeat(indent * 2));
//        if(element.isSpellerror()){
//            sb.append("[x]");
//        }

        sb.append("<").append(element.getTagName());
        if(element.isIsshowid()){
            sb.append(" id=\"").append(element.getId());
        }
        sb.append("\">");


        if (element.getChildren().isEmpty() && element.getText() != null ) {
            sb.append("\n");
            sb.append(" ".repeat(indent * 2 + 2));
            sb.append(element.getText());
        }
        sb.append("\n");
        for (TreeNode child : element.getChildren()) {
            visit(child, indent + 1, sb);
        }
        sb.append(" ".repeat(indent * 2));
        sb.append("</").append(element.getTagName()).append(">\n");
    }
}

