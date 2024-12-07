package org.example.model;

import org.example.model.visitor.Visitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String tagName;
    private String id;
    private String text;
    private List<TreeNode> children;
    private boolean spellerror;
    private boolean isshowid;
    private boolean ismodified;
    public TreeNode(String tagName, String id, String text, boolean spellerror,boolean isshowid ,boolean ismodified) {
        this.tagName = tagName;
        this.id = id != null ? id : tagName;
        this.text = text;
        this.children = new ArrayList<>();
        this.spellerror = spellerror;
        this.isshowid = isshowid;
        this.ismodified = ismodified;
    }

    public void addChild(TreeNode child) {
        children.add(child);
    }

    public boolean isIsmodified() {
        return ismodified;
    }

    public boolean isIsshowid() {
        return isshowid;
    }

    public boolean isSpellerror() {
        return spellerror;
    }

    public String getTagName() {
        return tagName;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    // 接受访问者
    public void accept(Visitor visitor, int indent, StringBuilder sb) {
        visitor.visit(this, indent, sb);
    }

    public static TreeNode fromFile(File file, List<String> modifiedFiles, File baseDir) throws IOException {
        // 计算相对路径
        String relativePath = baseDir.toPath().relativize(file.toPath()).toString();

        // 判断文件是否被修改
        boolean isModified = modifiedFiles.contains(relativePath);

        // 创建节点
        TreeNode node = new TreeNode(null, file.getName(), null, isModified, true,isModified);

        // 如果是目录，递归添加子节点
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    node.addChild(fromFile(childFile, modifiedFiles, baseDir)); // 递归调用
                }
            }
        }

        return node;
    }


    public static TreeNode fromHtmlElement(HtmlElement element,boolean isshowid) {
        TreeNode treeNode = new TreeNode(element.getTagName(), element.getId(), element.getText(), element.isSpellCheckError(),isshowid,false);
        for (HtmlElement child : element.getChildren()) {
            TreeNode childNode = fromHtmlElement(child,isshowid);  // 递归调用
            treeNode.addChild(childNode);
        }
        return treeNode;
    }
}
