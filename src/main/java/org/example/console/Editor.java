package org.example.console;
import org.example.command.EditorCommand.*;
import org.example.model.visitor.IndentVisitor;
import org.example.model.visitor.Visitor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.languagetool.JLanguageTool;

import org.languagetool.language.AmericanEnglish;
import org.languagetool.rules.RuleMatch;


import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.command.CommandInvoker;
import org.example.model.*;
import java.io.File;


public class Editor  implements Serializable {
    private String filepath;        // 文件路径
    private boolean isModified;     // 是否修改过
    private CommandInvoker commandInvoker;

    private boolean showid;
    private Document document;
    private HTMLTree htmlTree;
    private Visitor visitor;
    private JLanguageTool langTool = new JLanguageTool(new AmericanEnglish());
    // 构造函数和其他方法...

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }
    public boolean isShowid() {
        return showid;
    }


    public String print() {
        StringBuilder sb = new StringBuilder();
        this.SpellCheck();
        this.visitor.visit(TreeNode.fromHtmlElement(this.htmlTree.getRoot().getChildren().get(0),showid),0,sb);
        return  sb.toString();
    }
    public Editor(String filepath) throws IOException {
        this.filepath = filepath;
        this.commandInvoker = new CommandInvoker();
        this.showid=true;
        // 验证文件是否存在
        File file = new File(filepath);
        if (!file.exists()) {
            file.createNewFile();
            this.init();
            this.isModified = true;
            System.out.println("File does not exist. A new file has been created: " + filepath);
        } else {
            String html = new String(Files.readAllBytes(Paths.get(filepath)));

            this.document = Jsoup.parse(html);  // 解析HTML字符串
            this.htmlTree = new HTMLTree(this.document);

            this.visitor = new IndentVisitor();
            this.isModified = false; // 文件未修改
            System.out.println("File exists. Loading existing file: " + filepath);
        }
    }
    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }

    public void setShowid(boolean showid){
        this.showid = showid;
    }

    public String getFilepath() {
        return filepath;
    }


    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    public boolean GetStatus(){
        return this.htmlTree!=null ;
    }


    // 获取指定ID的元素
    public HtmlElement getElementById(String id) {
        return htmlTree.getElementById(id);
    }
    public String getElementContent(String elementId) {
        return htmlTree.getElementContent(elementId);
    }
    public String getNextElementId(String elementId) {
        return htmlTree.getNextElementId(elementId);
    }

    // 获取指定标签内的文本
    public String getTextByTag(String tagName) {
        Element element = document.selectFirst(tagName);
        return element != null ? element.text() : null;
    }


    // 插入新元素
    public void insert(String tagName, String idValue, String insertLocation, String textContent) {
        htmlTree.insert(tagName,idValue,insertLocation,textContent);
    }

    public void append(String tagName, String idValue, String parentElement, String textContent) {
        htmlTree.append(tagName,idValue,parentElement,textContent);
    }

    public void editId(String oldId, String newId) {
        htmlTree.editId(oldId,newId);
    }

    public void editText(String elementId, String newTextContent) {
        htmlTree.editText(elementId,newTextContent);

    }
    public void delete(String elementId) {
        htmlTree.delete(elementId);
    }

    public void handleInsert(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for insert command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String insertLocation = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";
        this.setModified(true);
        InsertCommand insertCommand = new InsertCommand(this, tagName, idValue, insertLocation, textContent);
        this.getCommandInvoker().storeAndExecute(insertCommand);
    }

    public void handleAppend(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid syntax for append command.");
        }
        String tagName = parts[1];
        String idValue = parts[2];
        String parentElement = parts[3];
        String textContent = (parts.length > 4) ? parts[4] : "";

        AppendCommand appendCommand = new AppendCommand(this, tagName, idValue, parentElement, textContent);
        this.getCommandInvoker().storeAndExecute(appendCommand);
    }

    public void handleEditId(String[] parts) {
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid syntax for editId command.");
        }
        String oldId = parts[1];
        String newId = parts[2];


        EditIdCommand editIdCommand = new EditIdCommand(this, oldId, newId);
        this.getCommandInvoker().storeAndExecute(editIdCommand);
    }

    public void handleEditText(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid syntax for editText command.");
        }
        String element = parts[1];

        // 拼接newTextContent为2之后的所有部分
        StringBuilder textContentBuilder = new StringBuilder();
        for (int i = 2; i < parts.length; i++) {
            if (i > 2) {
                textContentBuilder.append(" "); // 添加空格分隔多个部分
            }
            textContentBuilder.append(parts[i]);
        }
        String newTextContent = textContentBuilder.toString();

        EditTextCommand editTextCommand = new EditTextCommand(this, element, newTextContent);
        this.getCommandInvoker().storeAndExecute(editTextCommand);
    }

    public void handleDelete(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for delete command.");
        }
        String element = parts[1];


        DeleteCommand deleteCommand = new DeleteCommand(this, element);
        this.getCommandInvoker().storeAndExecute(deleteCommand);
    }

    void handlePrintIndent(String[] parts) {
        int indent = (parts.length > 1) ? Integer.parseInt(parts[1]) : 2;
        PrintIndentCommand printIndentCommand = new PrintIndentCommand(this, indent);
        this.getCommandInvoker().storeAndExecute(printIndentCommand);
    }

    void handlePrintTree() {

        PrintTreeCommand printTreeCommand = new PrintTreeCommand(this);
        this.getCommandInvoker().storeAndExecute(printTreeCommand);
    }

    void handleSpellCheck() {

        SpellCheckCommand spellCheckCommand = new SpellCheckCommand(this);
        this.getCommandInvoker().storeAndExecute(spellCheckCommand);
    }
    void hadnleShowId(String[] parts) {
        boolean isshowid=Boolean.parseBoolean(parts[0]);
        ShowIdCommand showIdCommand=new ShowIdCommand(this,isshowid);
        showIdCommand.execute();
    }
    private void handleRead(String[] parts) {
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid syntax for Read command.");
        }
        String filepath = parts[1];
        ReadCommand readCommand = new ReadCommand(this, filepath);
        this.getCommandInvoker().storeAndExecute(readCommand);
    }




    public void handleUndo() {
        this.getCommandInvoker().undoLastCommand();
    }

    public void handleRedo() {
        this.getCommandInvoker().redoLastCommand();
    }





    public void init() {
        this.document = Jsoup.parse("<html>\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body></body>\n" +
                "</html>");
        this.htmlTree = new HTMLTree(this.document);
    }
    public void readFromPath(String filepath) throws IOException {
        String html = new String(Files.readAllBytes(Paths.get(filepath)));
        this.document = Jsoup.parse(html);
        this.htmlTree = new HTMLTree(this.document);
    }

    public void saveToPath(String filepath) throws IOException {
        StringBuilder sb = new StringBuilder();
        new IndentVisitor( ).visit(TreeNode.fromHtmlElement(this.htmlTree.getRoot().getChildren().get(0),showid) ,0,sb);
        Files.write(Paths.get(filepath), sb.toString().getBytes());
    }

    public void setDocument(Document doc) {
        this.document = doc;
    }

    public Document getDocument() {
        return document;
    }
    public Map SpellCheck() {
        // 获取所有元素的 ID 和对应的文本内容
        Map<String, String> id2Context = htmlTree.getId2Context();
        Map<String, String> result = new HashMap<>();
        Map<String,HtmlElement> idmap = htmlTree.getIdMap();
        // 初始化 LanguageTool，使用美国英语
        // 遍历每个元素的文本内容，进行拼写检查
        for (Map.Entry<String, String> entry : id2Context.entrySet()) {
            String id = entry.getKey();
            String text = entry.getValue();
            try {
                // 检查文本
                List<RuleMatch> matches = langTool.check(text);
                for (RuleMatch match : matches) {
                    HtmlElement element = idmap.get(id);
                    element.setSpellCheckError(true);
                    result.put(id,text.substring(match.getFromPos(), match.getToPos())+'\n'+match.getSuggestedReplacements().toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

