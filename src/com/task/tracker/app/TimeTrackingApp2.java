package com.task.tracker.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTrackingApp2{

    private JFrame frame;
    private JButton startButton, stopButton, saveButton;
    private JTextField taskField;
    private JLabel timeLabel;
    private Timer timer;
    private long startTime;
    private List<TimeLog> timeLogs;
    private JTextArea timeLogsDisplay;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TimeTrackingApp2 window = new TimeTrackingApp2("");
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TimeTrackingApp2(String titile) {
        timeLogs = new ArrayList<>();
        initialize(titile);
    }

    private void initialize(String titile) {
	    frame = new JFrame();
	    frame.setBounds(100, 100, 450, 300);
	   // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(new FlowLayout());
	    System.out.println(titile);
	    if(titile != null)
	    	taskField = new JTextField(titile);
	    else
	    	taskField = new JTextField("Task Name");
	    frame.getContentPane().add(taskField);
	    taskField.setColumns(20);
	
	    timeLabel = new JLabel("Time: 00:00:00");
	    frame.getContentPane().add(timeLabel);
	
	    startButton = new JButton("Start Timer");
	    startButton.addActionListener(e -> startTimer());
	    frame.getContentPane().add(startButton);
	
	    stopButton = new JButton("Stop Timer");
	    stopButton.addActionListener(e -> stopTimer());
	    frame.getContentPane().add(stopButton);
	
	    saveButton = new JButton("Save Time Log");
	    saveButton.addActionListener(e -> saveTimeLog());
	    frame.getContentPane().add(saveButton);
	
	    timeLogsDisplay = new JTextArea(10, 30);
	    frame.getContentPane().add(new JScrollPane(timeLogsDisplay));
	    frame.setVisible(true);
	}

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                timeLabel.setText(formatTime(elapsedTime));
            }
        }, 0, 1000); // Update every second
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void saveTimeLog() {
        String taskName = taskField.getText();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        TimeLog log = new TimeLog(taskName, startTime, endTime, duration);
        timeLogs.add(log);
        displayTimeLogs();
    }

    private void displayTimeLogs() {
        StringBuilder sb = new StringBuilder();
        for (TimeLog log : timeLogs) {
            sb.append(log).append("\n");
        }
        timeLogsDisplay.setText(sb.toString());
    }

    private String formatTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    // TimeLog class to represent a time log entry
    class TimeLog {
        String taskName;
        long startTime;
        long endTime;
        long duration;

        TimeLog(String taskName, long startTime, long endTime, long duration) {
            this.taskName = taskName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return taskName + " | " + formatTime(duration);
        }
    }
}
