package duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import duke.task.Deadline;
import duke.task.Task;
import duke.task.Todo;

public class Storage {
    protected File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    public ArrayList<Task> load(Ui ui) {
        initialiseFile(ui);
        ArrayList<Task> taskList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                Task task = Parser.toTask(scanner.nextLine());
                taskList.add(task);
            }
            scanner.close();
        } catch (IOException exception) {
            ui.say(exception.getMessage(), true, true);
        }

        return taskList;
    }

    public void initialiseFile(Ui ui) {
        File directory = new File(this.file.getParent());
        if (!directory.exists()) {
            System.out.println("directory created");
            directory.mkdir();
        }

        try {
            if (!file.exists()) {
                System.out.println("file created");
                file.createNewFile();
            }
        } catch (IOException exception) {
            ui.say(exception.getMessage(), true, true);
        }
    }

    public void writeFile(TaskList list, Ui ui) {
        initialiseFile(ui);

        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < list.size(); i++) {
                Task task = list.get(i);
                if (task instanceof Todo) {
                    writer.write(task.getType() + " | " + task.getStatusInt() + " | " + task.getDescription() + "\n");
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    writer.write(task.getType() + " | " + task.getStatusInt() + " | " + task.getDescription()
                            + " | " + deadline.getBy().format(DateTimeFormatter.ofPattern("MMM d yyyy")) + "\n");
                }
            }
            writer.close();
        } catch (IOException exception) {
            ui.say(exception.getMessage(), true, true);
        }
    }
}
