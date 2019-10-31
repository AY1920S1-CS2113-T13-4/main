package duke.command;

import duke.dukeexception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Task;
import duke.task.Repeat;
import duke.task.FixedDuration;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author talesrune
/**
 * Represents a command that updates an existing task.
 */
public class UpdateCommand extends Command {
    protected String taskDesc;
    protected String dateDesc;
    protected String typeDesc;
    protected int typeOfUpdate;
    protected int index;
    private static final Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Creates a command with the specified parameters to update.
     *
     * @param taskDesc The task description to be updated.
     * @param dateDesc The date/time to be updated.
     * @param typeDesc The type of task to be updated.
     * @param typeOfUpdate The type of update.
     * @param index The index to be updated.
     */
    public UpdateCommand(String taskDesc, String dateDesc, String typeDesc, int typeOfUpdate, int index) {
        this.taskDesc = taskDesc;
        this.dateDesc = dateDesc;
        this.typeDesc = typeDesc;
        this.typeOfUpdate = typeOfUpdate;
        this.index = index;
    }

    /**
     * Executes a command that updates the task in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is updated successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        try {
            if (typeOfUpdate == 1) {
                items.get(index).setDescription(taskDesc);
            } else if (typeOfUpdate == 2) {
                if (items.get(index) instanceof Todo || items.get(index) instanceof FixedDuration) {
                    throw new DukeException("     (>_<) OOPS!!! This task does not have date and time!");
                }
                items.get(index).setDateTime(dateDesc);
            } else if (typeOfUpdate == 3) {
                Task newtaskObj = null;
                if (typeDesc.equals("todo")) {
                    if (items.get(index) instanceof Todo) {
                        throw new DukeException("     You are updating the same type of task! (Todo)");
                    } else {
                        newtaskObj = new Todo(items.get(index).getDescription());
                    }
                } else if (typeDesc.equals("deadline")) {
                    if (items.get(index) instanceof Repeat) {
                        newtaskObj = new Deadline(items.get(index).getDescription(), items.get(index).getDateTime());
                    } else if (items.get(index) instanceof Deadline) {
                        throw new DukeException("     You are updating the same type of task! (Deadline)");
                    } else {
                        newtaskObj = new Deadline(items.get(index).getDescription(), "01/01/2001 0001");
                    }
                } else if (typeDesc.equals("fixedduration")) {
                    if (items.get(index) instanceof FixedDuration) {
                        throw new DukeException("     You are updating the same type of task! (FixedDuration)");
                    } else {
                        newtaskObj = new FixedDuration(items.get(index).getDescription(), 0, "min");
                    }
                } else if (typeDesc.equals("repeat")) {
                    if (items.get(index) instanceof Deadline) {
                        newtaskObj = new Repeat(items.get(index).getDescription(), items.get(index).getDateTime());
                    } else if (items.get(index) instanceof Repeat) {
                        throw new DukeException("     You are updating the same type of task! (Repeat)");
                    } else {
                        newtaskObj = new Repeat(items.get(index).getDescription(), "01/01/2001 0001");
                    }
                }
                items.setTaskType(index, newtaskObj);
            }
            ui.showUpdate(items, index);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ui.showErrorMsg(e.getMessage());
        }

    }

    /**
     * Executes a command that updates the task in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is updated successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = "";
        try {
            if (typeOfUpdate == 1) {
                items.get(index).setDescription(taskDesc);
            } else if (typeOfUpdate == 2) {
                if (items.get(index) instanceof Todo || items.get(index) instanceof FixedDuration) {
                    return "     (>_<) OOPS!!! This task does not have date and time!";
                }
                items.get(index).setDateTime(dateDesc);
            } else if (typeOfUpdate == 3) {
                Task newtaskObj;
                if (typeDesc.equals("todo")) {
                    if (items.get(index) instanceof Todo) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (Todo)";
                    } else {
                        newtaskObj = new Todo(items.get(index).getDescription());
                    }
                } else if (typeDesc.equals("deadline")) {
                    if (items.get(index) instanceof Repeat) {
                        newtaskObj = new Deadline(items.get(index).getDescription(), items.get(index).getDateTime());
                    } else if (items.get(index) instanceof Deadline) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (Deadline)";
                    } else {
                        newtaskObj = new Deadline(items.get(index).getDescription(), "01/01/2001 0001");
                    }
                } else if (typeDesc.equals("fixedduration")) {
                    if (items.get(index) instanceof FixedDuration) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (FixedDuration)";
                    } else {
                        newtaskObj = new FixedDuration(items.get(index).getDescription(), 0, "min");
                    }
                } else if (typeDesc.equals("repeat")) {
                    if (items.get(index) instanceof Deadline) {
                        newtaskObj = new Repeat(items.get(index).getDescription(), items.get(index).getDateTime());
                    } else if (items.get(index) instanceof Repeat) {
                        return "     (>_<) OOPS!!! You are updating the same type of task! (Repeat)";
                    } else {
                        newtaskObj = new Repeat(items.get(index).getDescription(), "01/01/2001 0001");
                    }
                } else {
                    return "     (>_<) OOPS!!! You are entered an invalid task type!";
                }
                items.setTaskType(index, newtaskObj);
            }
            str = Ui.showUpdateGui(items, index);
        } catch (ParseException e) {
            e.printStackTrace();
            logr.log(Level.WARNING,"Error found when updating task's date", e);
            str = "     Error found when updating task's date, please use this format \"d/MM/yyyy HHmm\"";
        } catch (Exception e) {
            ui.showErrorMsg(e.getMessage());
            str = "     New error found when updating task, please fix.";
            logr.log(Level.SEVERE,"New error found when updating task", e);

        }

        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {
    }
}