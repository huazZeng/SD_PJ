import org.example.model.TreeNode;
import org.example.model.visitor.FileTreeVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileVistor {

    private TreeNode root;

    @BeforeEach
    public void setup() throws IOException {
        // 构造一个测试文件结构
        File baseDir = new File("src/test/resources");
        List<String> modifiedFiles = Arrays.asList("file1.txt", "file2.txt");

        // 创建文件树根节点
        root = TreeNode.fromFile(new File("src/test/resources"), modifiedFiles, baseDir);
    }

    @Test
    public void testFileTreeVisitor() {
        // 创建FileTreeVisitor实例
        FileTreeVisitor visitor = new FileTreeVisitor();

        // 使用StringBuilder存储遍历结果
        StringBuilder sb = new StringBuilder();

        // 访问文件树
        visitor.visit(root, 0, sb);

        // 获取结果
        String result = sb.toString();
        System.out.println(result);

        // 验证文件树输出格式
        assertTrue(result.contains("├── file1.txt*"));
        assertTrue(result.contains("├── subdir"));
        assertTrue(result.contains("├── file2.txt*"));
        assertTrue(result.contains("├── file3.txt"));
    }

    @Test
    public void testFileTreeVisitorEmptyDir() throws IOException {
        // 创建一个空目录来测试
        File emptyDir = new File("src/test/resources/emptyDir");
        if (!emptyDir.exists()) {
            emptyDir.mkdir(); // 创建测试目录
        }

        List<String> modifiedFiles = Arrays.asList("src/test/resources/emptyDir/modifiedFile.txt");
        TreeNode emptyRoot = TreeNode.fromFile(emptyDir, modifiedFiles, emptyDir);

        // 创建FileTreeVisitor实例
        FileTreeVisitor visitor = new FileTreeVisitor();

        // 使用StringBuilder存储遍历结果
        StringBuilder sb = new StringBuilder();

        // 访问文件树
        visitor.visit(emptyRoot, 0, sb);

        // 获取结果
        String result = sb.toString();
        System.out.println(result);

        // 验证空目录输出
        assertTrue(result.contains("├── emptyDir"));
        assertFalse(result.contains("*"));

        // 清理测试目录
        emptyDir.delete();
    }

    @Test
    public void testModifiedFileTree() throws IOException {
        // 构造一个包含多个文件和目录的树
        File baseDir = new File("src/test/resources");
        List<String> modifiedFiles = Arrays.asList("file1.txt", "subdir\\file2.txt");

        // 创建文件树
        TreeNode testRoot = TreeNode.fromFile(new File("src/test/resources"), modifiedFiles, baseDir);

        // 创建FileTreeVisitor实例
        FileTreeVisitor visitor = new FileTreeVisitor();

        // 使用StringBuilder存储遍历结果
        StringBuilder sb = new StringBuilder();

        // 访问文件树
        visitor.visit(testRoot, 0, sb);

        // 获取结果
        String result = sb.toString();
        System.out.println(result);

        // 验证修改文件标识是否正确
        assertTrue(result.contains("file1.txt*"));
        assertTrue(result.contains("file2.txt*"));
        assertFalse(result.contains("file3.txt*"));
    }
}
