package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class HtmlElement {
    private String tagName;
    private String id;
    private String text;
    private HtmlElement parent;
    private List<HtmlElement> children;
    private boolean SpellCheckError;

    public HtmlElement(String tagName, String id, String text) {
        this.tagName = tagName;
        this.id = id != null ? id : tagName;
        this.text = text;
        this.children = new ArrayList<>();
    }

    public void setSpellCheckError(boolean spellCheckError) {
        SpellCheckError = spellCheckError;
    }

    public void addChild(HtmlElement child) {
        child.setParent(this);
        children.add(child);
    }

    public void addChildAt(int index, HtmlElement child) {
        child.setParent(this);
        children.add(index, child);
    }

    public boolean removeChild(HtmlElement child) {
        child.setParent(null);
        return children.remove(child);
    }

    public String getTagName() {
        return tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public String getText() {
        return text;
    }

    public void setText(String newText) {
        this.text = newText;
    }

    public HtmlElement getParent() {
        return parent;
    }

    public void setParent(HtmlElement parent) {
        this.parent = parent;
    }

    public List<HtmlElement> getChildren() {
        return children;
    }

    public String toIndentedString(int indent, int indentLevel) {
        StringBuilder sb = new StringBuilder(" ".repeat(indent * indentLevel));
        sb.append("<").append(tagName).append(" id=\"").append(id).append("\">");
        if (text != null) sb.append(text);
        for (HtmlElement child : children) {
            sb.append("\n").append(child.toIndentedString(indent, indentLevel + 1));
        }
        sb.append("\n");
        sb.append(" ".repeat(indent * indentLevel));
        sb.append("</").append(tagName).append(">");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<").append(tagName).append(" id=\"").append(id).append("\">");
        if (text != null) sb.append(text);
        for (HtmlElement child : children) sb.append("\n  ").append(child.toString());
        sb.append("</").append(tagName).append(">\n");
        return sb.toString();
    }

    public String getInnerHtml() {
        StringBuilder innerHtml = new StringBuilder();
        if (text != null) innerHtml.append(text);
        for (HtmlElement child : children) innerHtml.append(child.toString());
        return innerHtml.toString();
    }


}
