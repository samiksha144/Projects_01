import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToDoApp extends JFrame {

    private DefaultListModel<String> tasksListModel;
    private JList<String> tasksList;
    private JTextField taskInputField;
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;

    public ToDoApp() {
        setTitle("To-Do List");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        tasksListModel = new DefaultListModel<>();
        tasksList = new JList<>(tasksListModel);
        taskInputField = new JTextField(20);
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        updateButton = new JButton("Update");

        // Add components to content pane
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        container.add(new JScrollPane(tasksList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.add(taskInputField);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(updateButton);

        container.add(inputPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTask();
            }
        });
    }

    private void addTask() {
        String task = taskInputField.getText().trim();
        if (!task.isEmpty()) {
            tasksListModel.addElement(task);
            taskInputField.setText("");
        }
    }

    private void removeTask() {
        int selectedIndex = tasksList.getSelectedIndex();
        if (selectedIndex != -1) {
            tasksListModel.remove(selectedIndex);
        }
    }

    private void updateTask() {
        int selectedIndex = tasksList.getSelectedIndex();
        if (selectedIndex != -1) {
            String updatedTask = taskInputField.getText().trim();
            if (!updatedTask.isEmpty()) {
                tasksListModel.setElementAt(updatedTask, selectedIndex);
                taskInputField.setText("");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoApp().setVisible(true);
            }
        });
    }
}
