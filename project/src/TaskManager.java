import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskManager extends JFrame {
    private final JPanel taskPanel;
    private JTextField taskInput;
    private JButton addButton;
    private ArrayList<JLabel> tasks;

    public TaskManager() {
        super("Менеджер задач");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null); // Центрировать окно
        setLayout(new BorderLayout(10, 10));

        Color background = new Color(245, 245, 250);
        Color cardColor = new Color(230, 235, 255);
        Color hoverColor = new Color(210, 220, 255);
        Color doneColor = new Color(180, 240, 180);

        getContentPane().setBackground(background);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(235, 235, 245));

        JLabel title = new JLabel("Добавить задачу:");
        title.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        taskInput = new JTextField(20);
        taskInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        addButton = new JButton("Добавить");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setBackground(new Color(120, 140, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        controlPanel.add(title);
        controlPanel.add(taskInput);
        controlPanel.add(addButton);
        add(controlPanel, BorderLayout.NORTH);

        taskPanel = new JPanel();
        taskPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        taskPanel.setBackground(background);

        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        tasks = new ArrayList<>();

        addButton.addActionListener(e -> addTask(cardColor, hoverColor, doneColor));

        taskInput.addActionListener(e -> addTask(cardColor, hoverColor, doneColor));

        setVisible(true);
    }

    private void addTask(Color cardColor, Color hoverColor, Color doneColor) {
        String text = taskInput.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Введите название задачи!");
            return;
        }

        JLabel taskLabel = new JLabel(text);
        taskLabel.setOpaque(true);
        taskLabel.setBackground(cardColor);
        taskLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        taskLabel.setPreferredSize(new Dimension(200, 35));

        taskLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!taskLabel.getBackground().equals(doneColor))
                    taskLabel.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!taskLabel.getBackground().equals(doneColor))
                    taskLabel.setBackground(cardColor);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                taskLabel.setBackground(doneColor);
                taskLabel.setText(text);
                JOptionPane.showMessageDialog(null,
                        "Задача \"" + text + "\" выполнена!",
                        "Готово",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        tasks.add(taskLabel);
        taskPanel.add(taskLabel);
        taskPanel.revalidate();
        taskPanel.repaint();
        taskInput.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManager::new);
    }
}
