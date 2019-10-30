package duke.ui;

import duke.Duke;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.ContactList;
import duke.task.FixedDuration;
import duke.task.Repeat;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.PriorityList;

import javafx.scene.control.ListView;
import javafx.util.Pair;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a ui that informs the user.
 */
public class Ui {

    protected static final String BACKUP_FILENAME = "duke.txt";
    protected static final String LINE = "    ____________________________________________________________";
    protected final Scanner in;
    protected final PrintStream out;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private Duke duke;
    private ListView<Task> listT;

    /**
     * Creates an empty ui using default scanner and print stream.
     */
    public Ui() {
        this(System.in, System.out);
    }

    /**
     * Creates an ui using specified scanner and print stream.
     *
     * @param in The input stream.
     * @param out The print stream.
     */
    public Ui(InputStream in, PrintStream out) {
        this.in = new Scanner(in);
        this.out = out;
    }

    /**
     * Outputs an horizontal line to the user.
     */
    public void showLine() {
        out.println(LINE);
    }

    /**
     * Show the help page.
     *
     * @param items The task list that contains a list of tasks.
     * @return an event requesting to view the help page
     */
    public static String helpRequest(TaskList items) {
        String str = "     Here are the commands available in Duke Manager:";
        return  str;
    }

    /**
     * Reads the user input, and converts it into string.
     *
     * @return String of the user input.
     */
    public String readCommand() {
        return in.nextLine();
    }

    //@@author gervaiseang
    /**
     * Outputs task that is successfully sets a reminder to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index indicated number of days to set the reminder
     * @return String of the task that is completed.
     */
    public String showReminderGui(TaskList items, int index) {
        String str = "     You have set a reminder for this task \n"
                + items.get(index);
        return str;
    }

    //@@author Dou-Maokang
    /**
     * Outputs all the tasks of the task list to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with each task.
     */
    public void showTaskListWithPriority(TaskList items, PriorityList priorities) {
        ArrayList<Pair> pair = PriorityList.sortPriority(items, priorities);
        out.println("     Here are the tasks in your list with priority shown:\n");
        out.printf("     Priority |\tTask\n");
        for (int i = ZERO; i < items.size() || i < priorities.getSize(); i++) {
            out.printf("        [%d]\t  |\t%s\n", pair.get(i).getKey(), pair.get(i).getValue());
        }
    }
    //@@author

    //@@author talesrune
    /**
     * Outputs all the tasks of the task list to the user.
     *
     * @param items The task list that contains a list of tasks.
     */
    public void showTaskList(TaskList items) {
        out.println("     Here are the tasks in your list:");
        out.print(items.getList());
    }

    /**
     * Outputs all the tasks of the task list to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @return String of all tasks from the task list.
     */
    public static String showTaskListGui(TaskList items) {
        String str = "     Here are the tasks in your list:\n" + items.getListGui();
        return str;
    }

    /**
     * Outputs task that is updated to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showUpdate(TaskList items, int index) {
        out.println("     Nice! I've updated this task ^^:");
        out.println("       " + (index + ONE) + "." + items.get(index).toString());
    }

    /**
     * Outputs notes of the task that is added or updated to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showAddNotes(TaskList items, int index) {
        out.println("     Nice! Added/Updated notes of this task ^^:");
        out.println("       " + (index + ONE) + "." + items.get(index).toString()
                + " | Added Notes: " + items.get(index).getNotes());
    }

    /**
     * Outputs notes of the task that is added or updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the added notes.
     */
    public String showAddNotesGui(TaskList items, int index) {
        String str = "     Nice! Added/Updated notes of this task ^^:\n"
                + "       " + (index + ONE) + "." + items.get(index).toString()
                + "\n      | Added Notes: " + items.get(index).getNotes();
        return str;
    }

    /**
     * Outputs notes of the task that is added or updated to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @param deletedNotes The deleted notes of the task.
     */
    public void showDeleteNotes(TaskList items, int index, String deletedNotes) {
        out.println("     Deleted notes of this task ^^:");
        out.println("       " + (index + ONE) + "." + items.get(index).toString()
                + " | Deleted notes: " + deletedNotes);
    }

    /**
     * Outputs notes of the task that is added or updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @param deletedNotes The deleted notes of the task.
     * @return String of the deleted notes.
     */
    public String showDeleteNotesGui(TaskList items, int index, String deletedNotes) {
        String str = "     Deleted notes of this task ^^:\n"
                + "       " + (index + ONE) + "." + items.get(index).toString()
                + "\n      | Deleted notes: " + deletedNotes;
        return str;
    }

    /**
     * Outputs notes of the task to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showNotes(TaskList items, int index) {
        out.println("     Here is the task and its notes:");
        out.println("       " + (index + ONE) + "." + items.get(index).toString()
                + " | Notes: " + items.get(index).getNotes());
    }

    /**
     * Outputs notes of the task to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of showing notes to the user.
     */
    public String showNotesGui(TaskList items, int index) {
        String str = "     Here is the task and its notes:\n"
                + "       " + (index + ONE) + "." + items.get(index).toString()
                + "\n      | Notes: " + items.get(index).getNotes();
        return str;
    }

    /**
     * Outputs task that is updated to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the task that is updated.
     */
    public static String showUpdateGui(TaskList items, int index) {
        String str = "     Nice! I've updated this task ^^:\n"
                + "       " + (index + ONE) + "." + items.get(index).toStringGui();
        return str;
    }

    /**
     * Outputs task that is completed to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     */
    public void showDone(TaskList items, int index) {
        out.println("     Nice! I've marked this task as done:");
        out.println("       " + items.get(index).toString());
    }

    /**
     * Outputs task that is completed to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param index The index of the task.
     * @return String of the task that is completed.
     */
    public static String showDoneGui(TaskList items, int index) {
        String str = "     Nice! I've marked this task as done:\n       " + items.get(index).toStringGui() + "\n";
        return str;
    }

    /**
     * Outputs task that is deleted to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param deletedTask The task that is deleted.
     */
    public void showDelete(TaskList items, String deletedTask) {
        out.println("     Noted. I've removed this task:");
        out.println(deletedTask);
        out.println("     Now you have " + items.size() + " tasks in the list.");
    }

    /**
     * Outputs task that is deleted to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param deletedTask The task that is deleted.
     * @return String of the task that is deleted.
     */
    public static String showDeleteGui(TaskList items, String deletedTask) {
        String str = "     Noted. I've removed this task:\n" + deletedTask
                + "\n     Now you have " + items.size() + " tasks in the list.\n";
        return str;
    }

    /**
     * Outputs task that is added to the user.
     *
     * @param items The task list that contains a list of tasks.
     */
    public void showAdd(TaskList items) {
        out.println("     Got it. I've added this task:");
        out.println("       " + items.get(items.size() - ONE).toString());
        out.println("     Now you have " + items.size() + " tasks in the list.");
    }

    /**
     * Outputs task that is added to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @return String of the task that is added.
     */
    public static String showAddGui(TaskList items) {
        String str = "     Got it. I've added this task:\n       "
                + items.get(items.size() - ONE).toStringGui() + "\n     Now you have "
                + items.size() + " tasks in the list.\n";
        return str;
    }

    /**
     * Outputs a welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        out.println("     Hello! I'm Duke");
        out.println("     What can I do for you?");
        showLine();
    }

    /**
     * Outputs a welcome message to the user (GUI).
     *
     * @return String of the welcome message.
     */
    public static String showWelcomeGui() {
        String str = LINE + "\n     Hello! I'm Duke\n     What can I do for you?\n"
                + LINE + "\n    Upcoming Reminders in 3 days,\n     refer to Chat Box below:";
        return str;
    }

    /**
     * Outputs a bye message to the user.
     */
    public void showBye() {
        out.println("     Bye. Hope to see you again soon!");
    }

    /**
     * Outputs a bye message to the user (GUI).
     *
     * @return String of the bye message.
     */
    public static String showByeGui() {
        String str = "     Bye. Hope to see you again soon!\n";
        return str;
    }

    /**
     * Outputs the tasks that are matched from the keyword to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The keyword to match the tasks.
     */
    public void showFind(TaskList items, String keyword) {
        out.println("     Here are the matching tasks in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }


    //@@author Dou-Maokang
    /**
     * Outputs the tasks with the target priority.
     *
     * @param items The task list that contains a list of tasks.
     * @param priorities The list of priorities associated with the task list.
     * @param targetPriority The target priority to search.
     */
    public void showFindTasksByPriority(TaskList items, PriorityList priorities, int targetPriority) {
        out.println("     Here are the matching tasks in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (priorities.getPriority(i + 1) == targetPriority) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }

    //@@author Dou-Maokang
    /**
     * Outputs the tasks with the target date.
     *
     * @param items The task list that contains a list of tasks.
     * @param targetDate The target date to search.
     */
    public void showFindTasksByDate(TaskList items, String targetDate) {
        out.println("     Here are the tasks on " + targetDate + " :");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (items.get(i).toString().contains(targetDate)) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     There're no tasks on " + targetDate + ".");
        }
    }

    //@@author talesrune
    /**
     * Outputs the tasks that are matched from the keyword to the user (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param keyword The keyword to match the tasks.
     * @return String of the matching tasks of the task list.
     */
    public static String showFindGui(TaskList items, String keyword) {
        String str = "     Here are the matching tasks in your list:\n";
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (items.get(i).getDescription().contains(keyword)) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == ZERO) {
            str += "     No matching tasks found.\n";
        }
        return str;
    }

    /**
     * Outputs the tasks that are filtered to the user.
     *
     * @param items The task list that contains a list of tasks.
     * @param taskType The type of task.
     */
    public void showFilter(TaskList items, String taskType) {
        out.println("     Here are the filtered tasks in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("repeat") && items.get(i) instanceof Repeat) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                out.println("     " + (i + ONE) + "." + items.get(i).toString());
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }

    /**
     * Outputs the tasks that are filtered to the user. (GUI)
     *
     * @param items The task list that contains a list of tasks.
     * @param taskType The type of task.
     * @return String of the matching tasks of the task list.
     */
    public static String showFilterGui(TaskList items, String taskType) {
        String str = "     Here are the filtered tasks in your list:\n";
        int numFound = ZERO;
        for (int i = ZERO; i < items.size(); i++) {
            if (taskType.equals("todo") && items.get(i) instanceof Todo) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("repeat") && items.get(i) instanceof Repeat) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
                str += "     " + (i + ONE) + "." + items.get(i).toStringGui() + "\n";
                numFound++;
            }
        }
        if (numFound == ZERO) {
            str += "     No matching tasks found." + "\n";
        }
        return str;
    }

    /**
     * Outputs the error when loading the file to the user.
     */
    public void showLoadingError() {
        out.println("File not found, creating an empty list");
    }

    /**
     * Outputs the error message during operating actions to the user.
     *
     * @param message The error message to be outputted.
     */
    public void showErrorMsg(String message) {
        out.println(message);
    }

    /**
     * Outputs the error message during operating actions to the user (GUI).
     *
     * @param message The error message to be outputted.
     * @return String of the error message.
     */
    public static String showErrorMsgGui(String message) {
        return message + "\n";
    }

    //@@author maxxyx96
    /**
     * Outputs a message to the user to let it know that it is updating.
     */
    public void showBackupMessage() {
        out.println("     Duke Manager has been backed up!\n"
                + "     Duke has opened the backup file location in file explorer!");
    }

    public static String showBackupMessageGui() {
        return "     Duke Manager has been backed up! \n"
                + "     Duke has opened the backup file location in file explorer!";
    }
    //@@author



    //@@author Dou-Maokang
    /**
     * Outputs a message to the user to let it know that it has changed the priority of a task.
     *
     * @param taskList The task list that contains a list of tasks.
     * @param taskNum The index of the task in the task list.
     * @param priority The index of the priority.
     */
    public void showSetPriority(TaskList taskList, int taskNum, int priority) {
        out.println("     Updated the priority of \n\t\t" + taskList.get(taskNum - ONE));
        out.println("     Current priority: " + priority);
    }

    //@@author e0318465
    /**
     * Outputs all the contacts of the contact list to user through CLI.
     *
     * @param contactList The list of contacts.
     */
    public void showContactList(ContactList contactList) {
        out.println("     Here are all your contacts:");
        out.print(contactList.getContactList());
    }

    /**
     * Outputs all the contacts of the contact list to user through GUI.
     *
     * @param contactList The list of contacts.
     * @return String of all contacts from contact list.
     */
    public static String showContactListGui(ContactList contactList) {
        String str = "";
        str += "Here are all your contacts:\n";
        str += contactList.getContactList();
        return str;
    }

    /**
     * Outputs an alert when a duplicated inout is detected.
     */
    public void showDuplicateMsg() {
        out.println("     The same task is already in the list!");
    }

    /**
     * Outputs the contact details that are most recently added.
     *
     * @param contactList The list of contacts.
     */
    public void showAddedContact(ContactList contactList) {
        if (contactList.size() == ZERO) {
            out.println("     You have no contacts!");
        } else {
            out.println("     Got it, now you have " + contactList.size() + " contacts. Contact added.");
            out.println(contactList.get(contactList.size() - ONE));
        }
    }

    /**
    * Show the added contacts in GUI.
    *
    * @param contactList The list of contacts.
    * @return String to output list of contacts to GUI.
     */
    public static String showAddedContactGui(ContactList contactList) {
        String str = "";
        if (contactList.size() == ZERO) {
            str += "     You have no contacts!";
        } else {
            str += "\n     Got it, now you have " + contactList.size() + " contacts. Contact added:\n";
            str += contactList.get(contactList.size() - ONE);
        }
        return str;
    }

    /**
     * Outputs contact that is deleted, to the user through CLI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param deletedContact The contact that is deleted.
     */
    public void showContactDeleted(ContactList contactList, String deletedContact) {
        out.println("     Now you have " + contactList.size() + " contact(s). I've removed this contact:");
        out.println(deletedContact);
    }

    /**
     * Outputs contact that is deleted to the user through GUI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param deletedContact The contact that is deleted.
     * @return String of deleted contact to user.
     */
    public static String showContactDeletedGui(ContactList contactList, String deletedContact) {
        String str = "";
        str += "Now you have " + contactList.size() + " contact(s). I've removed this contact:\n";
        str += deletedContact;
        return str;
    }

    /**
     * Outputs the contacts that are matched from the keyword.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param keyword The keyword to match the contacts.
     */
    public void showFoundContacts(ContactList contactList, String keyword) {
        out.println("     Here are the matching contacts in your list:");
        int numFound = ZERO;
        for (int i = ZERO; i < contactList.size(); i++) {
            String details = contactList.getOnlyDetails(i);
            details = details.replaceAll(",", " ");
            details = details.toLowerCase();
            if (details.contains(keyword)) {
                out.println("     " + contactList.getSpecificContactList(i));
                numFound++;
            }
        }
        if (numFound == ZERO) {
            out.println("     No matching tasks found.");
        }
    }

    /**
     * Outputs the tasks that are matched from the keyword to the user in GUI.
     *
     * @param contactList The contact list that contains a list of contacts.
     * @param keyword The keyword to match the contacts.
     * @return All contacts that matches with keyword.
     */
    public static String showFoundContactsGui(ContactList contactList, String keyword) {
        String str = "";
        str += "     Here are the matching contacts in your list:\n";
        int numFound = ZERO;
        for (int i = ZERO; i < contactList.size(); i++) {
            String details = contactList.getOnlyDetails(i);
            String replacedComma = details.replaceAll(",", " ");
            String stringToFind = replacedComma.toLowerCase();
            if (stringToFind.contains(keyword)) {
                str += "     " + contactList.getSpecificContactList(i) + "\n";
                numFound++;
            }
        }
        if (numFound == ZERO) {
            str += "     No matching tasks found.";
        }
        return str;
    }

    //@@author maxxyx96
    /**
     * Shows the current budget of the user.
     *
     * @param amount the budget the user currently has.
     */
    public void showBudget(float amount) {
        out.println("     Your budget is : $" + amount);
    }

    /**
     * Shows the current budget of the user.
     *
     * @param amount the budget the user currently has.
     * @return the message to be shown to the user.
     */
    public String showBudgetGui(float amount) {
        return "     Your current Budget is : $" + amount;
    }

    /**
     * Shows the user the amount that is added/subtracted to the existing budget.
     *
     * @param amount The amount that is to be added/subtracted.
     * @param budget The existing budget of the user.
     */
    public void showAddBudget(float amount, float budget) {
        if (amount > 0) {
            out.println("     You are adding $" + amount + " to your current budget of $" + budget);
        } else {
            out.println("     You are subtracting $" + -amount + " from your current budget of $" + budget);
        }
    }

    /**
     * Shows the user the amount that is added/subtracted to the existing budget. (GUI)
     *
     * @param amount The amount that is to be added/subtracted.
     * @param budget The existing budget of the user.
     * @return String of the text to show user.
     */
    public String showAddBudgetGui(float amount, float budget) {
        if (amount > 0) {
            return "     You are adding $" + amount + " to your current budget of $" + budget;
        } else {
            return "     You are subtracting $" + -amount + " from your current budget of $" + budget;
        }
    }

    /**
     * Shows the budget that the user has before the changes.
     *
     * @param budget The budget of the user.
     */
    public void showResetBudget(float budget) {
        out.println("     Your previous budget of $" + budget + " has been reset.");
    }

    /**
     * Shows the budget that the user has before the changes.
     *
     * @param budget The budget of the user.
     * @return String of the reset message.
     */
    public String showResetBudgetGui(float budget) {
        return "     Your previous budget of " + budget + " has been reset.";
    }

    /**
     * Shows the remarks user input when adding the budget.
     *
     * @param remark the remarks user inputted while adding/subtracting budget.
     * @return String of the remark message.
     */
    public String showRemarkGui(String remark) {
        return "     Remarks entered: " + remark;
    }

    /**
     * Shows the list of budget currently in Duke Manager.
     * @param list List of the budget stored.
     *
     * @return the list of the budget.
     */
    public String showBudgetListGui(String list) {
        return list;
    }

    /**
     * Shows the user that the input limit has been exceeded.
     *
     * @return String of the message to be shown.
     */
    public String showBudgetExceededLimitMessageGui() {
        return "     The limits of budget has been exceeded (> 999,999 or < -999,999),"
                + "\n     No action has been done. ";
    }
    //@@author
}