package org.example.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HTMLTree {
    private HtmlElement root;
    private Map<String, HtmlElement> idMap;
    private Map<String, String> id2context;
    public HTMLTree(Document doc) {
        this.idMap = new HashMap<>();
        this.id2context = new HashMap<>();
        this.root = buildTree(doc);
    }
    public HtmlElement getElementById(String id) {
        return idMap.get(id);
    }

    public Map<String, HtmlElement> getIdMap() {
        return idMap;
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
        if (id2context.containsKey(id)) throw new IllegalArgumentException("ID must be unique: " + id);

        HtmlElement htmlElement = new HtmlElement(tagName, id, element.ownText());
        idMap.put(id, htmlElement);
        id2context.put(id, element.ownText());
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
        id2context.remove(elementId);
        id2context.put(elementId, newText);
    }

    public void delete(String elementId) {
        HtmlElement elementToDelete = idMap.get(elementId);
        if (elementToDelete == null) throw new IllegalArgumentException("Element with id " + elementId + " not found.");
        HtmlElement parent = elementToDelete.getParent();
        if (parent != null) parent.removeChild(elementToDelete);
        idMap.remove(elementId);
    }

    public String printIndented(int indent) {
        return root.toIndentedString(indent, 0);
    }

    public String printIndent() {
        return root.toString();
    }

    public HtmlElement getRoot() {
        return this.root;
    }
    public String printTree() {
        StringBuilder sb = new StringBuilder();
        printTreeHelper(root, 0, sb);
        return sb.toString();
    }

    private void printTreeHelper(HtmlElement element, int indent, StringBuilder sb) {
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
            printTreeHelper(child, indent + 1, sb);
        }
    }


    public Map getId2Context() {
        return id2context;

    }
}

