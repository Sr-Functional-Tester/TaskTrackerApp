package com.task.tracker.app;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

public class NoteTakingApp {

    private JFrame frame;
    private JTextArea noteArea;
    private JTextField titleField;
    private JButton saveButton, loadButton;
    // Map to store note title and content
    private Map<String, String> notesMap;

    public static void main(String[] args) {
        try {
           // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  // Set Windows L&F
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    public NoteTakingApp(String noteTitle, String noteDescription) {
        initialize(noteTitle, noteDescription);
    }

    private void initialize(String noteTitle, String noteDescription) {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.getContentPane().setLayout(null);

        // Initialize the notesMap to store the notes
        notesMap = new HashMap<>();
        frame.setFocusable(true);

        titleField = new JTextField("Note Title");
        if(noteTitle != null)
        titleField.setText(noteTitle);
        titleField.setEditable(false);
        titleField.setBounds(162, 8, 300, 30);
        frame.getContentPane().add(titleField);

        noteArea = new JTextArea();
        noteArea.setBorder(new LineBorder(Color.BLACK));
        if(noteDescription != null)
        noteArea.setText(noteDescription);
        JScrollPane scrollPane = new JScrollPane(noteArea);
        scrollPane.setBounds(10, 45, 575, 300);  // Set the same bounds as JTextArea
        frame.getContentPane().add(scrollPane);

        saveButton = new JButton("Save Note");
        saveButton.addActionListener(e -> saveNote());
        saveButton.setBounds(225, 350, 125, 44);
        frame.getContentPane().add(saveButton);

        titleField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (titleField.getText().equals("Note Title")) {
                    titleField.setText("");  // Clear the default text
                }
            }
        });

        titleField.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (titleField.getText().contains("Note Title")) {
                    titleField.setText(""); // Clear the text field
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (titleField.getText().isEmpty()) {
                    titleField.setText("Note Title");
                }
            }

        });

        titleField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (titleField.getText().contains("Note Title")) {
                    titleField.setText(""); // Clear the text field
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (titleField.getText().isEmpty()) {
                    titleField.setText("Note Title");
                }
            }
        });

        frame.setVisible(true);
    }

    private void saveNote() {
        String title = titleField.getText();
        String content = noteArea.getText();
        //StringBuilder sb = new StringBuilder();
        // Check if title is valid
        if (title.isEmpty() || title.equals("Note Title")) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid title.");
            return;
        }
        
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Note content cannot be empty or blank.");
            return;
        }

        // Add the note to the Map
     
        notesMap.put(title, content);
        JOptionPane.showMessageDialog(frame, "Note saved successfully!");
        
        Tasktracker.setNoteMap(title, content);
       
        // Save the note to a text file
        
    }
}
