import org.example.command.CanUndoCommand;
import org.example.command.CommandInvoker;
import org.example.command.IOCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandInvokerMockTest {
    private CommandInvoker commandInvoker;
    private MockCommand mockCommand;

    @BeforeEach
    public void setUp() {
        commandInvoker = new CommandInvoker();
        mockCommand = new MockCommand(); // 创建一个 MockCommand 实例
    }

    @Test
    public void testStoreAndExecute_CanUndoCommand() {
        commandInvoker.storeAndExecute(mockCommand);
        assertTrue(commandInvoker.getCommandStackSize() > 0);
        assertEquals(mockCommand, commandInvoker.getLastCommand());
        assertTrue(mockCommand.isExecuted());
    }

    @Test
    public void testUndoLastCommand() {
        commandInvoker.storeAndExecute(mockCommand);
        commandInvoker.undoLastCommand();
        assertTrue(mockCommand.isUndone());
        assertTrue(commandInvoker.getUndoneCommandSize() > 0);
    }

    @Test
    public void testRedoLastCommand() {
        commandInvoker.storeAndExecute(mockCommand);
        commandInvoker.undoLastCommand();
        commandInvoker.redoLastCommand();
        assertTrue(mockCommand.isExecuted());
        assertTrue(commandInvoker.getCommandStackSize() > 0);
    }

    @Test
    public void testStoreAndExecute_IOCommand() {
        IOCommand ioCommand = new MockIOCommand(); // 创建一个 IOCommand 实例
        commandInvoker.storeAndExecute(mockCommand);
        commandInvoker.storeAndExecute(ioCommand);
        assertTrue(commandInvoker.getUndoneCommandSize()==0);
        assertTrue(commandInvoker.getCommandStackSize()==0);
    }

    // MockCommand 类用于模拟命令
    private class MockCommand implements CanUndoCommand {
        private boolean executed = false;
        private boolean undone = false;

        @Override
        public void execute() {
            executed = true;
        }

        @Override
        public void undo() {
            undone = true;
        }

        public boolean isExecuted() {
            return executed;
        }

        public boolean isUndone() {
            return undone;
        }
    }

    private class MockIOCommand implements IOCommand {
        private boolean executed = false;
        private boolean undone = false;

        @Override
        public void execute() {
            executed = true;
        }



        public boolean isExecuted() {
            return executed;
        }

        public boolean isUndone() {
            return undone;
        }
    }



}
