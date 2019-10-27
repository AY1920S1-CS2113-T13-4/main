package duke;

import duke.command.FindTasksByPriorityCommand;
import duke.command.BackupCommand;
import duke.command.ExitCommand;
import duke.command.ListPriorityCommand;
import duke.command.Command;
import duke.command.AddMultipleCommand;
import duke.command.SetPriorityCommand;
import duke.command.DeleteCommand;
import duke.command.DeleteContactCommand;
import duke.command.ListContactsCommand;
import duke.command.AddContactsCommand;
import duke.command.FilterCommand;

import duke.dukeexception.DukeException;
import duke.parser.Parser;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.storage.ContactStorage;
import duke.storage.BudgetStorage;
import duke.task.BudgetList;
import duke.task.PriorityList;
import duke.task.FilterList;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.IOException;


/**
 * Represents a duke that controls the program.
 */
public class Duke {
    private Storage storage;
    private TaskList items;
    private FilterList filterList;
    private Ui ui;
    private ContactStorage contactStorage;
    private ContactList contactList;

    private PriorityStorage priorityStorage;
    private PriorityList priorityList;

    private BudgetStorage budgetStorage;
    private BudgetList budgetList;
    private static final int ZERO = 0;

    private static final String storageFilePath = "data";
    private static final String taskFilePath = "data/duke.txt";
    private static final String priorityFilePath = "data/priority.txt";
    private static final String budgetFilePath = "data/budget.txt";
    private static final String contactsFilePath = "data/contacts.txt";

    /**
     * Creates a duke to initialize storage, task list, and ui.
     *
     * @param filePath1 The location of the text file.
     * @param filePath2 The location of the priority text file.
     * @param filePathForBudget The location of the budget text file
     * @param filePathForContacts The location of the contact text file.
     */
    public Duke(String filePath1, String filePath2, String filePathForBudget, String filePathForContacts) {
        ui = new Ui();
        filterList = new FilterList();
        storage = new Storage(filePath1);
        priorityStorage = new PriorityStorage(filePath2);
        contactStorage = new ContactStorage(filePathForContacts);
        budgetStorage = new BudgetStorage(filePathForBudget);
        checkStorageExist();
        try {
            items = new TaskList(storage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            ui.showErrorMsg("Storage NF");//temp
            items = new TaskList();
        }
        try {
            priorityList = new PriorityList(priorityStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            ui.showErrorMsg("Priority Storage NF");//temp
            priorityList = new PriorityList();
        }
        try {
            contactList = new ContactList(contactStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            ui.showErrorMsg("Contact List NF");//temp
            contactList = new ContactList();
        }
        try {
            budgetList = new BudgetList(budgetStorage.read());
        } catch (IOException e) {
            ui.showLoadingError();
            budgetList = new BudgetList();
        }
    }

    /**
     * Echoes the user input back the the user.
     * (Not in use)
     *
     * @param input The user input.
     * @return String of the response.
     */
    public String getResponse(String input) {
        return "Duke heard: " + input;
    }

    //@@author maxxyx96
    /**
     * Creates a directory for data storage if there is none created yet.
     *
     */
    public void checkStorageExist() {
        File storageFileDirectory = new File(storageFilePath);
        if (!storageFileDirectory.exists()) {
            storageFileDirectory.mkdirs();
        }
    }

    /**
     * Gets the budget list from Duke.
     *
     * @return the budget List.
     */
    public BudgetList getBudgetList() {
        return budgetList;
    }
    //@@author

    /**
     * Retrieves a command from interpreting the user input (GUI).
     *
     * @param sentence The user input.
     * @return Command to be executed thereafter.
     * @throws Exception  If there is an error reading the command.
     */
    public Command getCommand(String sentence) throws Exception {
        Command cmd = Parser.parse(sentence, items, budgetList, contactList);
        return cmd;
    }

    /**
     * Executes a command to overwrite exiting storage with the current updated lists(GUI).
     *
     * @param cmd Command to be executed.
     * @throws IOException  If there is an error writing the text file.
     */
    public void saveState(Command cmd) throws IOException {
        cmd.executeStorage(items, ui, storage, budgetStorage, budgetList,
                contactStorage, contactList, priorityStorage, priorityList);
    }

    /**
     * Executes a command and outputs the result to the user (GUI).
     *
     * @param cmd Command to be executed.
     * @return String to be outputted.
     * @throws IOException  If there is an error writing the text file
     */
    public String executeCommand(Command cmd) throws IOException {
        if (cmd instanceof AddContactsCommand) {
            String str = cmd.executeGui(items, contactList, ui);
            cmd.executeStorage(items, ui, contactStorage, contactList);
            return str;
        } else if (cmd instanceof ListContactsCommand) {
            String str = cmd.executeGui(items, contactList, ui);
            return str;
        } else if (cmd instanceof DeleteContactCommand) {
            String str = cmd.executeGui(items, contactList, ui);
            cmd.executeStorage(items, ui, contactStorage, contactList);
            return str;
        } else {
            String str = cmd.executeGui(items, ui);
            if (cmd instanceof FilterCommand) {
                cmd.execute(items,filterList);
            }
            return str;
        }
    }

    //@@author talesrune
    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public TaskList getTaskList() {
        return items;
    }

    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public FilterList getFilterList() {
        return filterList;
    }

    //@@author
    /**
     * Runs the duke program until exit command is executed.
     */
    public void run() {
        ui.showWelcome();
        Ui.showReminder(items);
        String sentence;

        while (true) {
            sentence = ui.readCommand();
            ui.showLine(); //Please do not remove!
            try {
                Command cmd = Parser.parse(sentence, items, budgetList, contactList);
                if (cmd instanceof ExitCommand) {
                    priorityStorage.write(priorityList);
                    budgetStorage.write(budgetList);
                    cmd.executeStorage(items, ui, storage);
                    break;
                } else if (cmd instanceof ListPriorityCommand
                        || cmd instanceof AddMultipleCommand
                        || cmd instanceof DeleteCommand
                        || cmd instanceof SetPriorityCommand
                        || cmd instanceof FindTasksByPriorityCommand) {
                    cmd.execute(items, priorityList, ui);
                } else if (cmd instanceof BackupCommand) {
                    priorityStorage.write(priorityList);
                    budgetStorage.write(budgetList);
                    contactStorage.write(contactList);
                    storage.write(items);
                    cmd.execute(items, ui);
                    cmd.executeStorage(items, ui, storage);
                } else if (cmd instanceof AddContactsCommand) {
                    cmd.execute(items, contactList, ui);
                    cmd.executeStorage(items, ui, contactStorage,contactList);
                } else if (cmd instanceof ListContactsCommand) {
                    cmd.execute(items, contactList, ui);
                } else if (cmd instanceof DeleteContactCommand) {
                    cmd.execute(items, contactList, ui);
                    cmd.executeStorage(items, ui, contactStorage,contactList);
                } else {
                    cmd.execute(items,ui);
                    priorityList = priorityList.addDefaultPriority(cmd);
                }
            } catch (DukeException e) {
                ui.showErrorMsg(e.getMessage());
            } catch (Exception e) {
                ui.showErrorMsg("     New error, please fix:");
                e.printStackTrace();
                ui.showErrorMsg("     Duke will continue as per normal.");
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke(taskFilePath, priorityFilePath, budgetFilePath,contactsFilePath).run();
    }
}