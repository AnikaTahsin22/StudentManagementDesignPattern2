package org.example;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentManagerUI extends JFrame {
    private JTextField nameField, banglaField, englishField, mathField;
    private JTextArea outputArea;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private StudentManager manager = new StudentManager();
    private final String FILE_NAME = "students.json";

    public StudentManagerUI() {
        setTitle("📚 Student Manager");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setSystemLookAndFeel();

       
        JTabbedPane tabbedPane = new JTabbedPane();

     
        JPanel addStudentPanel = createAddStudentPanel();

        
        JPanel recordsPanel = createRecordsPanel();

        tabbedPane.addTab("➕ Add Student", addStudentPanel);
        tabbedPane.addTab("📋 Student Records", recordsPanel);

        add(tabbedPane);
    }

    private JPanel createAddStudentPanel() {
        JPanel panel = new GradientPanel(new Color(240, 248, 255), new Color(200, 230, 255));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        Font labelFont = new Font("Segoe UI Semibold", Font.PLAIN, 15);

        JLabel nameLabel = createLabel("Name:", labelFont);
        JLabel banglaLabel = createLabel("Bangla:", labelFont);
        JLabel englishLabel = createLabel("English:", labelFont);
        JLabel mathLabel = createLabel("Math:", labelFont);

        nameField = new JTextField();
        banglaField = new JTextField();
        englishField = new JTextField();
        mathField = new JTextField();

    
        Dimension inputSize = new Dimension(400, 35);
        nameField.setPreferredSize(inputSize);
        banglaField.setPreferredSize(inputSize);
        englishField.setPreferredSize(inputSize);
        mathField.setPreferredSize(inputSize);

        addPlaceholder(nameField, "Enter name");
        addPlaceholder(banglaField, "Bangla marks");
        addPlaceholder(englishField, "English marks");
        addPlaceholder(mathField, "Math marks");

        JButton addButton = createFancyButton("➕ Add Student", new Color(72, 201, 176));
        addButton.addActionListener(this::addStudent);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(nameLabel)
                                        .addComponent(banglaLabel)
                                        .addComponent(englishLabel)
                                        .addComponent(mathLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameField)
                                        .addComponent(banglaField)
                                        .addComponent(englishField)
                                        .addComponent(mathField)))
                        .addComponent(addButton, GroupLayout.Alignment.CENTER)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(banglaLabel)
                                .addComponent(banglaField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(englishLabel)
                                .addComponent(englishField))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(mathLabel)
                                .addComponent(mathField))
                        .addGap(25)
                        .addComponent(addButton)
        );

        return panel;
    }


    private JPanel createRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columns = {"Name", "Bangla", "English", "Math", "Total", "Average", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(28);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane tableScroll = new JScrollPane(studentTable);
        tableScroll.setBorder(createRoundedTitledBorder("📋 Student Records"));

    
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(225, 235, 250));

        JButton highestButton = createFancyButton("📊 Highest Marks", new Color(100, 149, 237));
        JButton topperButton = createFancyButton("🏆 Show Topper", new Color(100, 149, 237));
        JButton saveButton = createFancyButton("💾 Save", new Color(100, 149, 237));
        JButton loadButton = createFancyButton("📂 Load", new Color(100, 149, 237));

        highestButton.addActionListener(this::showHighest);
        topperButton.addActionListener(this::showTopper);
        saveButton.addActionListener(e -> saveStudents());
        loadButton.addActionListener(e -> loadStudents());

        buttonPanel.add(highestButton);
        buttonPanel.add(topperButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

     
        outputArea = new JTextArea(5, 50);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(createRoundedTitledBorder("📄 Output Log"));

        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setPreferredSize(new Dimension(950, 130));

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        panel.add(outputScroll, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }

    private JButton createFancyButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);    // changed to black text
        button.setBackground(baseColor);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new RoundedBorder(15));
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(baseColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private Border createRoundedTitledBorder(String title) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(new Color(100, 149, 237), 2, true),
                        title,
                        TitledBorder.LEADING,
                        TitledBorder.TOP,
                        new Font("Segoe UI Semibold", Font.BOLD, 14),
                        new Color(30, 60, 120)
                ),
                new EmptyBorder(8, 8, 8, 8)
        );
    }

    private void addStudent(ActionEvent e) {
        String name = nameField.getText().trim();
        try {
            int bangla = Integer.parseInt(banglaField.getText().trim());
            int english = Integer.parseInt(englishField.getText().trim());
            int math = Integer.parseInt(mathField.getText().trim());

            manager.addStudent(name, bangla, english, math);
            outputArea.append("✅ Added student: " + name + "\n");
            updateTable();

           
            nameField.setText("");
            banglaField.setText("");
            englishField.setText("");
            mathField.setText("");

            addPlaceholder(nameField, "Enter name");
            addPlaceholder(banglaField, "Bangla marks");
            addPlaceholder(englishField, "English marks");
            addPlaceholder(mathField, "Math marks");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❗ Please enter valid numeric marks.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showHighest(ActionEvent e) {
        outputArea.append("📊 " + manager.getHighestMarks() + "\n");
    }

    private void showTopper(ActionEvent e) {
        outputArea.append("🏆 " + manager.getTopper() + "\n");
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        for (Student s : manager.getAllStudents()) {
            tableModel.addRow(new Object[]{
                    s.getName(), s.getBangla(), s.getEnglish(), s.getMath(),
                    s.totalMarks(), s.averageMarks(), s.getGrade()
            });
        }
    }

    private void saveStudents() {
        manager.saveToFile(FILE_NAME);
        outputArea.append("💾 Saved to " + FILE_NAME + "\n");
    }

    private void loadStudents() {
        manager.loadFromFile(FILE_NAME);
        updateTable();
        outputArea.append("📂 Loaded from " + FILE_NAME + "\n");
    }

   
    static class RoundedBorder extends AbstractBorder {
        private final int radius;
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = radius / 2;
            return insets;
        }
    }

 
    static class GradientPanel extends JPanel {
        private final Color startColor;
        private final Color endColor;

        public GradientPanel(Color start, Color end) {
            this.startColor = start;
            this.endColor = end;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, startColor, 0, h, endColor);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagerUI().setVisible(true));
    }
}
