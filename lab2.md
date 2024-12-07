# Lab2
### 启动方法


### 架构
总体采用**命令模式**
* `CommandPaser`：只负责命令解析，不参与实际的操作
* `Session` : 工作台，包含多个Editor，负责处理切换Editor，输出Editorlist，目录输出，加载Editor等工作台级别的指令,由于这些指令并不需要Undo Redo，所以没有指令栈
* `Editor` ： 对单个文件的编辑器，负责操作单个文件，并且存储指令栈，可以Undo Redo
* `HtmlTree`：保持与Lab1 相同设计
* `TreeNode` ： **使用适配器模式**，便于将htmlelement结构和file的结构转化统一，便于输出操作
* `Visitor` ： **采用Visitor模式**，来解决不同的输出结构，但file的输出结构和html有较些许不同，对二者直接采取不同的visitor（总共四个），避免在代码中出现大量的判断语句




### 类图

```mermaid
classDiagram
    class CommandParser {
        - HTMLModel htmlModel
        - CommandInvoker commandInvoker
        + void parseCommand(String commandline)
    }
    class Session {
        +Editor activateEditor
        +Map<String, Editor> editors
        
    }
    class HTMLTree {
        +HtmlElement root
        +Map<String, HtmlElement> idMap
        
    }

    class HtmlElement {
        +String tagName
        +String id
        +String text
        +String
    }
    class Editor {
        - Document document
        - HTMLTree htmlTree
        - PrintStrategy printStrategy
    }
    
    
    class Command{
     <<interface>>
     + void excute()
    }
   
    class CanUndoCommand{
         <<interface>>
     + void undo()

    }
    class IOCommand{
         <<interface>>
    }
    class CommandInvoker{
        
        - final Stack<CanUndoCommand> commandStack
        - final Stack<CanUndoCommand> undoneCommands
        + void storeAndExecute(Command command)
        + void undoLastCommand()
        + void redoLastCommand()
    }
    class TreeNode{
     + void accept()
     + TreeNode FromHtmlElement(HtmlElement element)
     + TreeNode FromFile(File file)
    }
    class File{
    }

    class Vistor{
    <<interface>>
     + void visit()
    }

    class TreeVisitor

    class IndentVisitor

    class FileIndentVisitor

    class FileTreeVisitor
    Vistor<|--FileIndentVisitor
    Vistor<|--TreeVisitor
    Vistor<|--FileTreeVisitor
    Vistor<|--IndentVisitor
   

    TreeNode <|..Vistor
    TreeNode <|..File 
    TreeNode <|..HtmlElement
   
    HTMLTree *-- HtmlElement 
    Editor o-- HTMLTree 

    Session o-- Editor
    CommandParser o-- Session  
    Editor o-- CommandInvoker
    console o-- CommandParser
    CommandInvoker o-- Command
    Command<|--CanUndoCommand
    Command<|--IOCommand
```