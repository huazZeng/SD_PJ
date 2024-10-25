package org.example.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HTMLTree {
    private HtmlElement root;
    private Map<String, HtmlElement> idMap;

    public HTMLTree(Document doc) {
        this.idMap = new HashMap<>();
        this.root = buildTree(doc);
    }
    public HtmlElement getElementById(String id) {
        return idMap.get(id);
    }

    public String getElementContent(String elementId) {
        HtmlElement element = getElementById(elementId);
        return element != null ? element.getInnerHtml() : null;
    }

    public String getNextElementId(String elementId) {
        HtmlElement element = getElementById(elementId);
        if (element == null || element.getParent() == null) return null;

        List<HtmlElement> siblings = element.getParent().getChildren();
        int index = siblings.indexOf(element);

        // Check if the element is the last child
        if (index == siblings.size() - 1) return null;

        HtmlElement nextElement = siblings.get(index + 1);
        return nextElement != null ? nextElement.getId() : null;
    }

    private HtmlElement buildTree(Element element) {
        String tagName = element.tagName();
        String id = element.hasAttr("id") ? element.attr("id") : tagName;
        if (idMap.containsKey(id)) throw new IllegalArgumentException("ID must be unique: " + id);
        HtmlElement htmlElement = new HtmlElement(tagName, id, element.ownText());
        idMap.put(id, htmlElement);
        for (Element child : element.children()) htmlElement.addChild(buildTree(child));
        return htmlElement;
    }

    public void insert(String tagName, String id, String insertLocation, String text) {
        if (idMap.containsKey(id)) throw new IllegalArgumentException("ID must be unique: " + id);
        HtmlElement locationElement = idMap.get(insertLocation);
        if (locationElement == null || locationElement.getParent() == null)
            throw new IllegalArgumentException("Insert location not found or invalid.");

        HtmlElement newElement = new HtmlElement(tagName, id, text);
        HtmlElement parent = locationElement.getParent();
        int index = parent.getChildren().indexOf(locationElement);
        parent.addChildAt(index, newElement);
        idMap.put(id, newElement);
    }

    public void append(String tagName, String id, String parentElementId, String text) {
        if (idMap.containsKey(id)) throw new IllegalArgumentException("ID must be unique: " + id);
        HtmlElement parentElement = idMap.get(parentElementId);
        if (parentElement == null) throw new IllegalArgumentException("Parent element not found.");
        HtmlElement newElement = new HtmlElement(tagName, id, text);
        parentElement.addChild(newElement);
        idMap.put(id, newElement);
    }

    public void editId(String oldId, String newId) {
        if (!idMap.containsKey(oldId)) throw new IllegalArgumentException("Element with old ID not found.");
        if (idMap.containsKey(newId)) throw new IllegalArgumentException("New ID must be unique.");
        HtmlElement element = idMap.remove(oldId);
        element.setId(newId);
        idMap.put(newId, element);
    }

    public void editText(String elementId, String newText) {
        HtmlElement element = idMap.get(elementId);
        if (element == null) throw new IllegalArgumentException("Element with id " + elementId + " not found.");
        element.setText(newText);
    }

    public void delete(String elementId) {
        HtmlElement elementToDelete = idMap.get(elementId);
        if (elementToDelete == null) throw new IllegalArgumentException("Element with id " + elementId + " not found.");
        HtmlElement parent = elementToDelete.getParent();
        if (parent != null) parent.removeChild(elementToDelete);
        idMap.remove(elementId);
    }

    public void printIndented(int indent) {
        System.out.println(root.toIndentedString(indent, 0));
    }

    public void printTree() {
        System.out.println(root.toString());
    }
}