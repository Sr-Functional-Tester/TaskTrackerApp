package com.task.tracker.app;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TimeTrackingApp {

    private JFrame frame;
    private JButton startButton, stopButton, saveButton, clearButton;
    private JTextField taskField, taskFieldPercentage, startDayField, startMonthField, startYearField, startHourField, startMinuteField, startSecondField;
    private JTextField endDayField, endMonthField, endYearField, endHourField, endMinuteField, endSecondField;
    private JLabel timeLabel,timeLabell,startDateLabel,endDateLabel, taskFieldPercentageLabel;
    private Timer timer;
    private long startTime;
    private List<TimeLog> timeLogs; // Using a map to store time logs with unique IDs
    private JTextArea timeLogsDisplay;
    private JRadioButton option1, option2;
    private JComboBox<String> amPmComboBox, amPmComboBox2;
    private Map<String, String> timeSpentMapTemp = new HashMap<String,String>();
    private static boolean progressUpdate = false;
//    public static void main(String[] args) {
    
//        EventQueue.invokeLater(() -> {
//            try {
//                TimeTrackingApp window = new TimeTrackingApp();
//                window.frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public TimeTrackingApp(String title, Map<String, String> timeSpentMap) {
    	 timeLogs = new ArrayList<>();
//    	 if(timeSpentMap != null)
//    	 {
//    	 String timeSpentstr = timeSpentMap.get("timeSpent");
//    	 if(timeSpentstr != null && !timeSpentstr.isEmpty())
//         timeLogsDisplay.setText(timeSpentstr);
//    	 }
        initialize(title, timeSpentMap);
       if(timeSpentMap!=null)
        System.out.println(title+"------"+timeSpentMap.size());
        if(timeSpentMap != null && timeSpentMap.size() != 0) {
        	 
        	timeSpentMapTemp =  new HashMap<>(timeSpentMap);
        	System.out.println(title+"----...--"+timeSpentMapTemp.size());
        for (Map.Entry<String, String> entry : timeSpentMap.entrySet()) {
			String taskTitle = entry.getKey();
			String value = entry.getValue();
			System.out.println(taskTitle+"--timetracker---"+value);
		}
        }else{
        	timeSpentMapTemp = new HashMap<String, String>();
        }
    }

    private void initialize(String title, Map<String, String> timeSpentMap) {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);  // Increased height for better layout
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // Task Name Field
        //taskField = new JTextField(title);
        taskField = new JTextField("Task Name");
        taskField.setBounds(30, 10, 200, 30);  // Set width to fit the frame
        taskField.setText(title);
        frame.getContentPane().add(taskField);
        
        taskFieldPercentageLabel = new JLabel("Task Completion Percentage (%)");
        taskFieldPercentageLabel.setBounds(280, 10, 200, 30);  // Set width to fit the frame
        //taskFieldPercentage.setText(title);
        frame.getContentPane().add(taskFieldPercentageLabel);
        // Time Logs Display Area
        timeLogsDisplay = new JTextArea();
       // timeLogsDisplay.setEditable(true);
        timeLogsDisplay.setBackground(Color.WHITE);
        timeLogsDisplay.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(timeLogsDisplay);
        scrollPane.setBounds(60, 300, 460, 79);
        frame.getContentPane().add(scrollPane);
        
//        stopButton = new JButton("Stop Timer");
//	    stopButton.setBounds(270, 70, 120, 30);
//	    stopButton.addActionListener(e -> stopTimer());
//	    frame.getContentPane().add(stopButton);
        
        taskFieldPercentage = new JTextField("eg. 75");
        taskFieldPercentage.setBounds(482, 10, 68, 30);  // Set width to fit the frame
        //taskFieldPercentage.setText(title);
        frame.getContentPane().add(taskFieldPercentage);

        // Add focus listener to taskField
        taskField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (taskField.getText().contains("Task Name")) {
					taskField.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (taskField.getText().isEmpty()) {
					taskField.setText("Task Name");
				}
			}

		});
        
        taskFieldPercentage.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (taskFieldPercentage.getText().contains("eg. 75"))
            		taskFieldPercentage.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (taskFieldPercentage.getText().isEmpty()) taskFieldPercentage.setText("eg. 75");
            }
        });
        timeLabell = new JLabel("Time: ");
	    timeLabell.setBounds(30, 70, 100, 30);
	    frame.getContentPane().add(timeLabell);
	
	    timeLabel = new JLabel("00:00:00");
	    timeLabel.setBounds(67, 70, 100, 30);
	    frame.getContentPane().add(timeLabel);
	
	    startButton = new JButton("Start Timer");
	    startButton.setBounds(130, 70, 120, 30);
	    startButton.addActionListener(e -> startTimer());
	    frame.getContentPane().add(startButton);
	
	    stopButton = new JButton("Stop Timer");
	    stopButton.setBounds(270, 70, 120, 30);
	    stopButton.addActionListener(e -> stopTimer());
	    frame.getContentPane().add(stopButton);
	
	    // Save Button
        clearButton = new JButton("clear Time Log");
        clearButton.setBounds(300, 250, 150, 30);
       // saveButton.setVisible(false);
        clearButton.addActionListener(e -> 
        { 
        	timeLogs = new ArrayList<>();
        	timeLogsDisplay.setText("");
        });
        frame.getContentPane().add(clearButton);
        // Save Button
        saveButton = new JButton("Save Time Log");
        saveButton.setBounds(147, 250, 150, 30);
       // saveButton.setVisible(false);
        saveButton.addActionListener(e -> 
        { 
        	System.out.println(option1.isSelected()+"----"+option2.isSelected());
        if(option1.isSelected()) {
        	//timeLogs = new ArrayList<>();
        	
	    	//System.out.println(progressUpdate);
	    		saveTimeLog();
	    		updateProgress();
        }
	    if(option2.isSelected()) {
	    	//timeLogs = new ArrayList<>();
	    	
	    	
	    		calculateTimeDifference();
	    	
	    		updateProgress();
	    }
	    
        });
        frame.getContentPane().add(saveButton);
	
//	    timeLogsDisplay = new JTextArea();
//	    timeLogsDisplay.setBounds(2, 300, 150, 30);
//	    frame.getContentPane().add(new JScrollPane(timeLogsDisplay));
	    frame.setVisible(true);

	 // Create two radio buttons
	    option1 = new JRadioButton();
	    option1.setBounds(10, 70, 20, 30);  // Set position of the radio button

	    option2 = new JRadioButton();
	    option2.setBounds(0, 10, 20, 30);  // Set position of the second radio button

	    // Group the radio buttons together so only one can be selected at a time
	    ButtonGroup group = new ButtonGroup();
	    group.add(option1);
	    group.add(option2);

	    option1.addActionListener(e -> {
	    	timeLogsDisplay.setText("");
	    	toggleMode();});
	    option2.addActionListener(e -> {
	    	timeLogsDisplay.setText("");
	    	toggleMode();});

	 // Task Name Field
	    //taskField.setEnabled(false);  // Initially disabled

	    // Timer related components
	    timeLabell.setEnabled(false);  // Initially disabled
	    timeLabel.setEnabled(false);  // Initially disabled
	    startButton.setEnabled(false);  // Initially disabled
	    stopButton.setEnabled(false);  // Initially disabled
	   // saveButton.setEnabled(false);  // Initially disabled
	   // timeLogsDisplay.setEnabled(false);  // Initially disabled
	    //amPmComboBox.setEnabled(false);

	    // Set the default selection (optional)
	   option2.setSelected(true); // Option 1 will be selected by default
	   // toggleMode();
	    // Add radio buttons to the frame or panel
	    frame.getContentPane().add(option1);
	   

        // Manual Date and Time Entry Group
        JPanel manualEntryPanel = new JPanel();
        manualEntryPanel.setBounds(10, 130, 600, 100);  // Increased height for better spacing
        manualEntryPanel.setLayout(null);  // No layout manager

     // Start Date and Time Fields (DD MM YYYY HH MM SS)
        startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(20, 10, 100, 30);
        manualEntryPanel.add(startDateLabel);
        manualEntryPanel.add(option2);
        // Start Date Fields (Day, Month, Year)
        startDayField = new JTextField("DD");
        startDayField.setBounds(120, 10, 50, 30);
        startDayField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startDayField.getText().contains("DD"))
            			startDayField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startDayField.getText().isEmpty()) startDayField.setText("DD");
            }
        });
        manualEntryPanel.add(startDayField);

        startMonthField = new JTextField("MM");
        startMonthField.setBounds(180, 10, 50, 30);
        startMonthField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startMonthField.getText().contains("MM"))
                startMonthField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startMonthField.getText().isEmpty()) startMonthField.setText("MM");
            }
        });
        manualEntryPanel.add(startMonthField);

        startYearField = new JTextField("YYYY");
        startYearField.setBounds(240, 10, 60, 30);
        startYearField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startYearField.getText().contains("YYYY"))
                startYearField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startYearField.getText().isEmpty()) startYearField.setText("YYYY");
            }
        });
        manualEntryPanel.add(startYearField);

        // Start Time Fields (Hour, Minute, Second)
        startHourField = new JTextField("HH");
        startHourField.setBounds(310, 10, 50, 30);
        startHourField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startHourField.getText().contains("HH"))
                startHourField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startHourField.getText().isEmpty()) startHourField.setText("HH");
            }
        });
        manualEntryPanel.add(startHourField);

        startMinuteField = new JTextField("MM");
        startMinuteField.setBounds(370, 10, 50, 30);
        startMinuteField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startMinuteField.getText().contains("MM"))
                startMinuteField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startMinuteField.getText().isEmpty()) startMinuteField.setText("MM");
            }
        });
        manualEntryPanel.add(startMinuteField);

        startSecondField = new JTextField("00");
        startSecondField.setBounds(430, 10, 50, 30);
        startSecondField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (startSecondField.getText().contains("00"))
                  startSecondField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (startSecondField.getText().isEmpty()) startSecondField.setText("00");
            }
        });
        manualEntryPanel.add(startSecondField);
        
        amPmComboBox = new JComboBox<>();
        amPmComboBox.addItem("AM");
        amPmComboBox.addItem("PM");
        amPmComboBox.setBounds(490, 10, 50, 30); // Set position and size for the dropdown

        // Add the dropdown to the frame
        manualEntryPanel.add(amPmComboBox);

        // End Date and Time Fields (DD MM YYYY HH MM SS)
        endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(20, 50, 100, 30);
        manualEntryPanel.add(endDateLabel);

        // End Date Fields (Day, Month, Year)
        endDayField = new JTextField("DD");
        endDayField.setBounds(120, 50, 50, 30);
        endDayField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endDayField.getText().contains("DD"))
                endDayField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endDayField.getText().isEmpty()) endDayField.setText("DD");
            }
        });
        manualEntryPanel.add(endDayField);

        endMonthField = new JTextField("MM");
        endMonthField.setBounds(180, 50, 50, 30);
        endMonthField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endMonthField.getText().contains("MM"))
                endMonthField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endMonthField.getText().isEmpty()) endMonthField.setText("MM");
            }
        });
        manualEntryPanel.add(endMonthField);

        endYearField = new JTextField("YYYY");
        endYearField.setBounds(240, 50, 60, 30);
        endYearField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endYearField.getText().contains("YYYY"))
                endYearField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endYearField.getText().isEmpty()) endYearField.setText("YYYY");
            }
        });
        manualEntryPanel.add(endYearField);

        // End Time Fields (Hour, Minute, Second)
        endHourField = new JTextField("HH");
        endHourField.setBounds(310, 50, 50, 30);
        endHourField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endHourField.getText().contains("HH"))
                endHourField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endHourField.getText().isEmpty()) endHourField.setText("HH");
            }
        });
        manualEntryPanel.add(endHourField);

        endMinuteField = new JTextField("MM");
        endMinuteField.setBounds(370, 50, 50, 30);
        endMinuteField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endMinuteField.getText().contains("MM"))
                endMinuteField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endMinuteField.getText().isEmpty()) endMinuteField.setText("MM");
            }
        });
        manualEntryPanel.add(endMinuteField);

        endSecondField = new JTextField("00");
        endSecondField.setBounds(430, 50, 50, 30);
        endSecondField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            	if (endSecondField.getText().contains("00"))
                endSecondField.setText("");
            }
            public void focusLost(FocusEvent e) {
                if (endSecondField.getText().isEmpty()) endSecondField.setText("00");
            }
        });
        manualEntryPanel.add(endSecondField);
        
        amPmComboBox2 = new JComboBox<>();
        amPmComboBox2.addItem("AM");
        amPmComboBox2.addItem("PM");
        amPmComboBox2.setBounds(490, 50, 50, 30); // Set position and size for the dropdown

        // Add the dropdown to the frame
        manualEntryPanel.add(amPmComboBox2);

        frame.getContentPane().add(manualEntryPanel);

        if(timeSpentMap != null && timeSpentMap.size() != 0) {
        String startDate = timeSpentMap.get("startDate");
       if(startDate != null) {
        	System.out.println("inside setup"+timeSpentMap.size());
        	 String startDay = timeSpentMap.get("startDay");
             String startMonth = timeSpentMap.get("startMonth");
             String startYear = timeSpentMap.get("startYear");
             String startHour = timeSpentMap.get("startHour");
             String startMinute = timeSpentMap.get("startMinute");
             String startAmPm = timeSpentMap.get("startAmPm");
             if(startDay != null && !startDay.isEmpty())
             startDayField.setText(startDay);
             if(startMonth != null && !startMonth.isEmpty())
             startMonthField.setText(startMonth);
             if(startYear != null && !startYear.isEmpty())
             startYearField.setText(startYear);
             if(startHour != null && !startHour.isEmpty())
             startHourField.setText(startHour);
             if(startMinute != null && !startMinute.isEmpty())
             startMinuteField.setText(startMinute);
             if(startAmPm != null && !startAmPm.isEmpty())
             amPmComboBox.setSelectedItem(startAmPm);
             
             // End Date/Time fields (DD/MM/YYYY HH:MM:SS AM/PM)
            
             String endDay = timeSpentMap.get("endDay");
             String endMonth = timeSpentMap.get("endMonth");
             String endYear = timeSpentMap.get("endYear");
             String endHour = timeSpentMap.get("endHour");
             String endMinute = timeSpentMap.get("endMinute");
            // String endSecond = timeSpentMap.get("endMonth");
             String endAmPm = timeSpentMap.get("endAmPm");
             
             if(endDay != null && !endDay.isEmpty())
             endDayField.setText(endDay);
             if(endMonth != null && !endMonth.isEmpty())
             endMonthField.setText(endMonth);
             if(endYear != null && !endYear.isEmpty())
             endYearField.setText(endYear);
             if(endHour != null && !endHour.isEmpty())
             endHourField.setText(endHour);
             if(endMinute != null && !endMinute.isEmpty())
             endMinuteField.setText(endMinute);
             if(endAmPm != null && !endAmPm.isEmpty())
             amPmComboBox2.setSelectedItem(endAmPm);
             
             String timeSpentstr = timeSpentMap.get("timeSpent");
             timeLogsDisplay.setText(timeSpentstr);
             

//             startDateLabel.setEnabled(false);
//             startDayField.setEnabled(false);
//             startMonthField.setEnabled(false);
//             startYearField.setEnabled(false);
//             startHourField.setEnabled(false);
//             startMinuteField.setEnabled(false);            
        }
        }


       // saveButton.setVisible(true);
        // Frame visibility
        frame.setVisible(true);
        frame.repaint();
       frame.revalidate();
    }
    
    private void toggleMode() {
        if (option1.isSelected()) {
            // Enable timer-related elements, disable manual entry
            startButton.setEnabled(true);
            stopButton.setEnabled(true);
            timeLabel.setEnabled(true);
            timeLabell.setEnabled(true);
            saveButton.setEnabled(true);

            // Disable manual date fields
            startDateLabel.setEnabled(false);
            startDayField.setEnabled(false);
            startMonthField.setEnabled(false);
            startYearField.setEnabled(false);
            startHourField.setEnabled(false);
            startMinuteField.setEnabled(false);
            startSecondField.setEnabled(false);
            amPmComboBox.setEnabled(false);
            amPmComboBox2.setEnabled(false);
            endDateLabel.setEnabled(false);
            endDayField.setEnabled(false);
            endMonthField.setEnabled(false);
            endYearField.setEnabled(false);
            endHourField.setEnabled(false);
            endMinuteField.setEnabled(false);
            endSecondField.setEnabled(false);
        } else if (option2.isSelected()) {
            // Disable timer-related elements, enable manual entry
            startButton.setEnabled(false);
            stopButton.setEnabled(false);
            timeLabel.setEnabled(false);
            timeLabell.setEnabled(false);
            saveButton.setEnabled(true); // Keep save button enabled

            // Enable manual date fields
            startDateLabel.setEnabled(true);
            startDayField.setEnabled(true);
            startMonthField.setEnabled(true);
            startYearField.setEnabled(true);
            startHourField.setEnabled(true);
            startMinuteField.setEnabled(true);
            startSecondField.setEnabled(true);
            amPmComboBox.setEnabled(true);
            amPmComboBox2.setEnabled(true);
            endDateLabel.setEnabled(true);
            endDayField.setEnabled(true);
            endMonthField.setEnabled(true);
            endYearField.setEnabled(true);
            endHourField.setEnabled(true);
            endMinuteField.setEnabled(true);
            endSecondField.setEnabled(true);
        }
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
        	progressUpdate = true;
            timer.cancel();
        }
    }


    private void saveManualTimeLog() {
        String taskName = taskField.getText();

        // Case for manually entered start and end date/time
        String startDate = startDayField.getText() + "/" + startMonthField.getText() + "/" + startYearField.getText();
        String startTime = startHourField.getText() + ":" + startMinuteField.getText() + ":" + startSecondField.getText();

        String endDate = endDayField.getText() + "/" + endMonthField.getText() + "/" + endYearField.getText();
        String endTime = endHourField.getText() + ":" + endMinuteField.getText() + ":" + endSecondField.getText();

        long startTimelong = System.currentTimeMillis(); // Example of time handling
        long endTimeMillis = System.currentTimeMillis();
        long duration = endTimeMillis - startTimelong;

     //   TimeLog log = new TimeLog(taskName, startDate, startTime, endDate, endTime, duration);
        int logId = timeLogs.size() + 1; // Simple incrementing ID
     //   timeLogs.put(logId, log);

        // Displaying the updated list of time logs
        //displayTimeLogs();
    }

    private void saveTimeLog() {
        String taskName = taskField.getText();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        TimeLog log = new TimeLog(taskName, startTime, endTime, duration);
        timeLogs.add(log);
        displayTimeLogs(taskName);
      //calculateTimeDifference();
        progressUpdate = false;
    }
    
    private void displayTimeLogs(String title) {
        StringBuilder sb = new StringBuilder();
        for (TimeLog log : timeLogs) {
            sb.append(log).append("\n");
        }
        timeLogsDisplay.setText(sb.toString());
       // timeSpentMap.put(title, sb.toString());
        String inputText = timeLogsDisplay.getText();
        String[] lines = inputText.split("\\n");

        List<String> timeStrings = new ArrayList<>();
        for (String line : lines) {
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                String time = parts[1].trim();
                timeStrings.add(time);
            }
        }

        String total = sumTimes(timeStrings);
        System.out.println("time spent "+total);
        LocalDate startdt = LocalDate.now();
        LocalDate enddt = LocalDate.now();
        timeSpentMapTemp.put("startDate", startdt.toString());
        timeSpentMapTemp.put("endDate", enddt.toString()
        		);
        timeSpentMapTemp.put("timeSpent", total);

        Tasktracker.setTimeSpentMap(taskField.getText(), timeSpentMapTemp);
        
        frame.repaint();
       frame.revalidate();
    }
    
    private void updateProgress() {
        if(!taskFieldPercentage.getText().isEmpty() && !taskFieldPercentage.getText().equals("eg. 75"))
            timeSpentMapTemp.put("taskCompletionPercentage", taskFieldPercentage.getText());
        else
        	timeSpentMapTemp.put("taskCompletionPercentage", "0");
        
        Tasktracker.setTimeSpentMap(taskField.getText(), timeSpentMapTemp);
       // progressUpdate = false;
    }
    private void calculateTimeDifference() {
        // Start Date/Time fields (DD/MM/YYYY HH:MM:SS AM/PM)
        String startDay = startDayField.getText();
        String startMonth = startMonthField.getText();
        String startYear = startYearField.getText();
        String startHour = startHourField.getText();
        String startMinute = startMinuteField.getText();
        String startSecond = startSecondField.getText();
        String startAmPm = (String) amPmComboBox.getSelectedItem();
        
        timeSpentMapTemp.put("startDay", startDay);
        timeSpentMapTemp.put("startMonth", startMonth);
        timeSpentMapTemp.put("startYear", startYear);
        timeSpentMapTemp.put("startHour", startHour);
        timeSpentMapTemp.put("startMinute", startMinute);
        timeSpentMapTemp.put("startAmPm", startAmPm);
    

        // End Date/Time fields (DD/MM/YYYY HH:MM:SS AM/PM)
        String endDay = endDayField.getText();
        String endMonth = endMonthField.getText();
        String endYear = endYearField.getText();
        String endHour = endHourField.getText();
        String endMinute = endMinuteField.getText();
        String endSecond = endSecondField.getText();
        String endAmPm = (String) amPmComboBox2.getSelectedItem();
        
        timeSpentMapTemp.put("endDay", endDay);
        timeSpentMapTemp.put("endMonth", endMonth);
        timeSpentMapTemp.put("endYear", endYear);
        timeSpentMapTemp.put("endHour", endHour);
        timeSpentMapTemp.put("endMinute", endMinute);
        timeSpentMapTemp.put("endAmPm", endAmPm);

        // Format to handle AM/PM (e.g., 2 PM -> 14:00)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");

        // Parse start date and time with AM/PM
        String startDateTimeStr = startDay + "/" + startMonth + "/" + startYear + " " + startHour + ":" + startMinute + ":" + startSecond + " " + startAmPm;
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, formatter);
        timeSpentMapTemp.put("startDate", startDateTime.toString());

        
        if(!endDay.equals("DD") && !endMonth.equals("MM") && !endYear.equals("YYYY") && !endHour.equals("HH") && !endMinute.equals("MM")) {
        // Parse end date and time with AM/PM
        String endDateTimeStr = endDay + "/" + endMonth + "/" + endYear + " " + endHour + ":" + endMinute + ":" + endSecond + " " + endAmPm;
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, formatter);
        timeSpentMapTemp.put("endDate", endDateTime.toString());
        // Calculate the duration between start and end times
        Duration duration = Duration.between(startDateTime, endDateTime);

        // Return the duration in milliseconds (you can change this to seconds, minutes, etc.)
        StringBuilder sb = new StringBuilder();
        
       // return duration.toMillis();
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;  // <- This line adds seconds

        // Optional singular/plural handling (currently all same)
        String daysText = (days == 1) ? "d" : "d";
        String hoursText = (hours == 1) ? "h" : "h";
        String minutesText = (minutes == 1) ? "m" : "m";
        String secondsText = (seconds == 1) ? "s" : "s";

        // Final formatted time string
        String timeSpent = String.format("%d%s %d%s %d%s %d%s",
                days, daysText,
                hours, hoursText,
                minutes, minutesText,
                seconds, secondsText);
        // Format the string
        timeLogsDisplay.setText(timeSpent);
        timeSpentMapTemp.put("timeSpent", timeSpent);
        }else {
        	timeSpentMapTemp.put("startDate", startDateTime.toString());
        }
        
        Tasktracker.setTimeSpentMap(taskField.getText(), timeSpentMapTemp);
        
      //  progressUpdate = false;
       
    }
    
    public static String sumTimes(List<String> timeStrings) {
        int totalSeconds = 0;

        for (String time : timeStrings) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);

            totalSeconds += hours * 3600 + minutes * 60 + seconds;
        }

        int days = totalSeconds / (24 * 3600);
        int remainingSeconds = totalSeconds % (24 * 3600);

        int hours = remainingSeconds / 3600;
        remainingSeconds %= 3600;

        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;

        String daysText = "d";
        String hoursText = "h";
        String minutesText = "m";
        String secondsText = "s";

        // Final output string
        return String.format("%d%s %d%s %d%s %d%s",
                days, daysText,
                hours, hoursText,
                minutes, minutesText,
                seconds, secondsText);
    }

    private String formatTime(long milliseconds) {
        long seconds = (milliseconds / 1000) % 60;
        long minutes = (milliseconds / (1000 * 60)) % 60;
        long hours = (milliseconds / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    
    // TimeLog class to represent a time log entry
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
