package com.task.tracker.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Tasktracker {

	@SuppressWarnings("unused")
	private static JFrame frame;
	static JPanel dynamicPanel = null; // New panel to hold dynamically added components
	static JScrollPane scrollPane = null;
	private static JTextArea taskField, workerFiled, projectName, dueDateField, workerFiledsa;
	private static JTextArea descriptionField;
	private static JTextField SearchBar;
	private static JComboBox<String> statusComboBox, priorityComboBox, projectNameComboBox;

	private static JButton addButton, clearButton;
	private static JButton updateButton = new JButton("Update Task");
	private static JButton selectButton, okButton, notOkButton, deSelectButton;
	private static JButton deleteButton;
	private static JPanel panel;
	private static Map<String, Task> taskMapTemp;
	private static Map<String, Set<String>> projectMapList = new HashMap<String, Set<String>>();
	private static Map<String, String> noteMap = new HashMap<String,String>();
	private static Map<String, Task> taskMap = new TreeMap<String, Task>(); // To store tasks by their title
	private static Map<String, List<JLabel>> recordJLabelMap = new TreeMap<String, List<JLabel>>(); // To store tasks by
																									// their title
	private static Set<String> projectNamesList = new HashSet<String>();
	private static Task selectedTask = null;

	private static Map<String, JCheckBox> taskCheckboxes = new TreeMap<>(); // To store checkboxes for tasks
	private static Map<String, JRadioButton> taskJRadioButtons = new TreeMap<>(); // To store checkboxes for tasks
	private static Map<String, JButton> taskJButtons = new TreeMap<>(); // To store checkboxes for tasks
	private static List<JCheckBox> jcheckBoxList = new ArrayList<JCheckBox>();
	private static List<JButton> jbuttonList = new ArrayList<JButton>();
	private static List<JLabel> jLabelList = new ArrayList<JLabel>();
	private static ButtonGroup group = new ButtonGroup();
	// private static JTextField taskField;
	private static Set<JLabel> permanentLabels = new HashSet<>();
	private static Set<JComboBox> jcomboBoxes = new HashSet<>();
	private static JCheckBox taskCheckBox1;
	static boolean selected = false;
	private static JLabel todoLabelsc, inProgressLabelsc, completedLabelsc, taskStartTimeLabell, taskStartTimeLabels, taskCompletedTimeLabell;
	private static JLabel taskStartTimeLabel, taskEmdTimeLabel, taskCompletedTimeLabel, taskCompletedTimeLabells, taskEmdTimeLabell, taskEmdTimeLabels;
	private static Map<String, String> timeSpentMap = new HashMap<String, String>();
	private static Map<String, Map<String, String>> timeSpentMapofMaps = new HashMap<String, Map<String, String>>();
	static int j = 10;
	static int scrollheight = 588;
	static Map<String, Map> mapOfAllMaps = new HashMap<String, Map>();
	private static CircleProgressChart progressChart;
	

	public static void main(String[] args) {
		try {
			
			mapOfAllMaps = FileToSaveRecord.readMap();
			if(mapOfAllMaps != null)
			taskMap = mapOfAllMaps.get("taskMap");
			else
				mapOfAllMaps = new HashMap<String, Map>();
			if(taskMap == null) taskMap = new TreeMap<String, Task>(); 
			if(mapOfAllMaps != null) timeSpentMapofMaps = mapOfAllMaps.get("timeSpentMapofMaps");
			if(timeSpentMapofMaps == null) timeSpentMapofMaps = new HashMap<String, Map<String, String>>();
			if(mapOfAllMaps != null) noteMap = mapOfAllMaps.get("noteMap");
			if(noteMap == null) noteMap = new HashMap<String,String>();
			
			if(mapOfAllMaps != null) projectMapList = mapOfAllMaps.get("projectMapList");
			if(projectMapList == null) projectMapList = new HashMap<String, Set<String>>();
			projectNamesList = projectMapList.get("projectNamesList");
			if(projectNamesList == null) projectNamesList = new HashSet<String>();
			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		// Create frame
		JFrame frame = new JFrame("Task Tracker");
		frame.setUndecorated(true); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(740, 524); // Set frame size
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBackground(Color.BLACK);
		// Create a panel to display the background image
		@SuppressWarnings("serial")
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Load and draw the background image
				ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/imageone.png")); // Path to image
				g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this); // Stretch image to fill
																								// the entire panel

				// ImageIcon backgroundImage2 = new
				ImageIcon backgroundImage2 = new ImageIcon(getClass().getResource("/taskmm.png"));
				 g.drawImage(backgroundImage2.getImage(), 4, 115, 118, 118, this); // Stretch
				// image to fill the entire panel

				// Cast to Graphics2D for more advanced drawing
//              Graphics2D g2d = (Graphics2D) g;
//
//              // Set the color of the line (e.g., red)
//              g2d.setColor(Color.WHITE);
//
//              // Set the stroke (line width), e.g., 5 pixels
//              g2d.setStroke(new BasicStroke((float) 0.2));
//
//              // Draw the horizontal line at y = 100, from x = 0 to x = getWidth()
//              g2d.drawLine(0, 232, getWidth(), 232);

			}
		};
		
		 final Point[] mouseClickPoint = {null};
		// Mouse listener for dragging
        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseClickPoint[0] = e.getPoint(); // Save initial click point
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currentScreenLocation = e.getLocationOnScreen();
                frame.setLocation(
                    currentScreenLocation.x - mouseClickPoint[0].x,
                    currentScreenLocation.y - mouseClickPoint[0].y
                );
            }
        });
		
     // Add minimize button
//        JButton minimizeBtn = new JButton("_");
//        styleButton(minimizeBtn);
//        minimizeBtn.setBounds(628, 28, 120, 20);
//        minimizeBtn.addActionListener(e -> frame.setState(Frame.ICONIFIED));
//        panel.add(minimizeBtn);

        updateProjectDropdown(panel);
        
        // Add close button
        JButton closeBtn = new JButton("Close");
        //styleButton(closeBtn);
        closeBtn.setBackground(Color.WHITE);
        closeBtn.setForeground(Color.BLACK);
        closeBtn.setBounds(668, 0, 67, 28);
        closeBtn.addActionListener(e -> System.exit(0));
        panel.add(closeBtn);
             
		progressChart = new CircleProgressChart(0); // Set % here
		progressChart.setBounds(570, 107, 102, 102);
	    panel.add(progressChart);
	    panel.revalidate();
		panel.repaint();

		dynamicPanel = new JPanel();
		dynamicPanel.setBackground(Color.DARK_GRAY);
		dynamicPanel.setForeground(Color.DARK_GRAY);
		dynamicPanel.setLayout(null); // Use null layout for custom positioning
		dynamicPanel.setBounds(0, 277, 777, 540); // Size and position for the panel
		// dynamicPanel.setOpaque(false); // Set transparent background
		scrollPane = new JScrollPane(dynamicPanel); // Use dynamicPanel in the scroll pane
		scrollPane.setBounds(0, 277, 777, 540); // Set size for the scroll pane
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		dynamicPanel.setFocusable(true);
		
		dynamicPanel.requestFocusInWindow();
		
		dynamicPanel.addMouseListener(new MouseAdapter() {
	            public void mousePressed(MouseEvent e) {
	            	dynamicPanel.setFocusable(true);
	        		
	        		dynamicPanel.requestFocusInWindow();
	    			int panelHeight = dynamicPanel.getComponentCount() * 34;
	    			dynamicPanel.setPreferredSize(new java.awt.Dimension(240, panelHeight));
	    			dynamicPanel.revalidate();
					dynamicPanel.repaint();
	    			scrollPane.revalidate();
	    			scrollPane.repaint();
	            }
	        });
		Color lightBrown = Color.decode("#896129"); 
		JButton scrollToTopButton = new JButton("⏫");
		scrollToTopButton.setBackground(Color.GREEN);
		scrollToTopButton.setForeground(lightBrown);
		scrollToTopButton.setBounds(648, 220, 44, 27);
		
		JButton scrollUpButton = new JButton("⬆️");
		scrollUpButton.setBackground(lightBrown);
		scrollUpButton.setForeground(Color.WHITE);
		scrollUpButton.setBounds(648, 248, 42, 24);
		scrollUpButton.addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        scrollSmoothly(scrollPane.getVerticalScrollBar(), -10); // Scroll up slowly
		    }
		});

		// Button to scroll down
		JButton scrollDownButton = new JButton("⬇️");
		scrollDownButton.setBackground(Color.GREEN);
		scrollDownButton.setForeground(lightBrown);
		scrollDownButton.setBounds(692, 248, 42, 24);
		scrollDownButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        scrollSmoothly(scrollPane.getVerticalScrollBar(), 10); // Scroll down slowly
		    }
		});
        
		scrollToTopButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		        verticalScrollBar.setValue(verticalScrollBar.getMinimum()); // Scroll to top
		    }
		});
		panel.add(scrollToTopButton);
		panel.add(scrollUpButton);
		panel.add(scrollDownButton);
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
        .addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    JScrollBar vBar = scrollPane.getVerticalScrollBar();
                    if (e.getKeyCode() == KeyEvent.VK_UP) {
                        vBar.setValue(vBar.getValue() - 34);
                        return true; // consumed
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        vBar.setValue(vBar.getValue() + 34);
                        return true; // consumed
                    }
                }
                return false; // pass through
            }
        });


        
		panel.setBackground(Color.BLACK);
		panel.add(scrollPane);

//        frame.setBackground(Color.BLACK);
//        System.out.println("scroll size "+j);
//        panel.setPreferredSize(new Dimension(700, 588)); 
//        JScrollPane scrollPane = new JScrollPane(panel);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  // Never show horizontal scrollbar        scrollPane.setBounds(0, 0, 700, 480);
//        scrollPane.setEnabled(true);
		// scrollPane.setBounds(0, 287, 700, 480);
		frame.setContentPane(panel);
		frame.setBackground(Color.BLACK);
		panel.setLayout(null); // Use null layout to manually position components
		// Create a JScrollPane and wrap your panel inside it

		panel.setOpaque(false);

		workerFiledsa = new JTextArea("Worker Name");
		workerFiledsa.setBounds(240, 2, 123, 27);
		workerFiledsa.setVisible(false);
		panel.add(workerFiledsa);
		
		workerFiled = new JTextArea("Worker Name");
		workerFiled.setBounds(240, 9, 123, 27);
		panel.add(workerFiled);

		projectName = new JTextArea("Project Name");
		projectName.setBounds(364, 9, 123, 27);
		panel.add(projectName);
		// workerFiled.setForeground(Color.GRAY);

		taskField = new JTextArea("Task Title");
		taskField.setBounds(240, 39, 247, 27);
		panel.add(taskField);

		descriptionField = new JTextArea("Task Description");
		descriptionField.setBounds(240, 69, 247, 70);
		panel.add(descriptionField);

		dueDateField = new JTextArea("Due Date (YYYY-MM-DD)");
		dueDateField.setBounds(240, 142, 150, 27);
		panel.add(dueDateField);

		String[] statuses = { "To Do", "In Progress", "Completed" };
		statusComboBox = new JComboBox<>(statuses);
		statusComboBox.setBounds(391, 142, 96, 27);
		panel.add(statusComboBox);
		jcomboBoxes.add(statusComboBox);

		String[] priority = { "Low", "Medium", "High" };
		priorityComboBox = new JComboBox<>(priority);
		priorityComboBox.setBounds(240, 172, 75, 38);
		panel.add(priorityComboBox);
		jcomboBoxes.add(priorityComboBox);

		// System.out.println(projectNamesList.get(0));

		SearchBar = new JTextField("Search Bar");
		SearchBar.setBounds(2, 235, 177, 20);
		panel.add(SearchBar);
		
		JLabel todoLabel = new JLabel("To Do");
		todoLabel.setBounds(28, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		todoLabel.setForeground(Color.WHITE);
		
		panel.add(todoLabel);
		permanentLabels.add(todoLabel);
		
		JLabel todoLabels = new JLabel(":");
		todoLabels.setBounds(117, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		todoLabels.setForeground(Color.WHITE);
		panel.add(todoLabels);
		permanentLabels.add(todoLabels);
		
		todoLabelsc = new JLabel("");
		todoLabelsc.setBounds(138, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		todoLabelsc.setForeground(Color.WHITE);
		todoLabelsc.setBackground(Color.RED);
		panel.add(todoLabelsc);
		permanentLabels.add(todoLabelsc);
		
		JLabel inProgressLabel = new JLabel("In Progress");
		inProgressLabel.setBounds(28, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		inProgressLabel.setForeground(Color.WHITE);
		panel.add(inProgressLabel);
		permanentLabels.add(inProgressLabel);
		
		JLabel inProgressLabels = new JLabel(":");
		inProgressLabels.setBounds(117, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		inProgressLabels.setForeground(Color.WHITE);
		panel.add(inProgressLabels);
		permanentLabels.add(inProgressLabels);
		
		inProgressLabelsc = new JLabel("");
		inProgressLabelsc.setBounds(138, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		inProgressLabelsc.setForeground(Color.WHITE);
		panel.add(inProgressLabelsc);
		permanentLabels.add(inProgressLabelsc);
		
		JLabel completedLabel = new JLabel("Completed");
		completedLabel.setBounds(28, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		completedLabel.setForeground(Color.WHITE);
		panel.add(completedLabel);
		permanentLabels.add(completedLabel);
		
		JLabel completedLabels = new JLabel(":");
		completedLabels.setBounds(117, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		completedLabels.setForeground(Color.WHITE);
		panel.add(completedLabels);
		permanentLabels.add(completedLabels);
		
		completedLabelsc = new JLabel("");
		completedLabelsc.setBounds(138, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		completedLabelsc.setForeground(Color.WHITE);
		panel.add(completedLabelsc);
		permanentLabels.add(completedLabelsc);
		
		//============================= private static JLabel taskStartTimeLabel, taskEmdTimeLabel, taskCompletedTimeLabel;
		
		taskStartTimeLabell = new JLabel("Task Start Time");
		taskStartTimeLabell.setBounds(528, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskStartTimeLabell.setForeground(Color.WHITE);
		
		panel.add(taskStartTimeLabell);
		permanentLabels.add(taskStartTimeLabell);
		
		taskStartTimeLabels = new JLabel(":");
		taskStartTimeLabels.setBounds(627, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskStartTimeLabels.setForeground(Color.WHITE);
		panel.add(taskStartTimeLabels);
		permanentLabels.add(taskStartTimeLabels);
		
		taskStartTimeLabel = new JLabel("");
		taskStartTimeLabel.setBounds(638, 28, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskStartTimeLabel.setForeground(Color.WHITE);
		taskStartTimeLabel.setBackground(Color.RED);
		panel.add(taskStartTimeLabel);
		permanentLabels.add(taskStartTimeLabel);
		
		taskEmdTimeLabell = new JLabel("Task End Time");
		taskEmdTimeLabell.setBounds(528, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskEmdTimeLabell.setForeground(Color.WHITE);
		panel.add(taskEmdTimeLabell);
		permanentLabels.add(taskEmdTimeLabell);
		
		taskEmdTimeLabels = new JLabel(":");
		taskEmdTimeLabels.setBounds(627, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskEmdTimeLabels.setForeground(Color.WHITE);
		panel.add(taskEmdTimeLabels);
		permanentLabels.add(taskEmdTimeLabels);
		
		taskEmdTimeLabel = new JLabel("");
		taskEmdTimeLabel.setBounds(638, 48, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskEmdTimeLabel.setForeground(Color.WHITE);
		panel.add(taskEmdTimeLabel);
		permanentLabels.add(taskEmdTimeLabel);
		
		taskCompletedTimeLabell = new JLabel("Time Spent");
		taskCompletedTimeLabell.setBounds(528, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskCompletedTimeLabell.setForeground(Color.WHITE);
		panel.add(taskCompletedTimeLabell);
		permanentLabels.add(taskCompletedTimeLabell);
		
		taskCompletedTimeLabells = new JLabel(":");
		taskCompletedTimeLabells.setBounds(627, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskCompletedTimeLabells.setForeground(Color.WHITE);
		panel.add(taskCompletedTimeLabells);
		permanentLabels.add(taskCompletedTimeLabells);
		
		taskCompletedTimeLabel = new JLabel("");
		taskCompletedTimeLabel.setBounds(638, 68, 120, 20);
		// taskLabel.setFont(Font.B)t
		taskCompletedTimeLabel.setForeground(Color.WHITE);
		panel.add(taskCompletedTimeLabel);
		permanentLabels.add(taskCompletedTimeLabel);
		
		taskStartTimeLabel.setVisible(false);
		taskEmdTimeLabel.setVisible(false);
		taskCompletedTimeLabel.setVisible(false);
		
		taskStartTimeLabell.setVisible(false);
		taskEmdTimeLabell.setVisible(false);
		taskCompletedTimeLabell.setVisible(false);
		
		taskStartTimeLabels.setVisible(false);
		taskEmdTimeLabels.setVisible(false);
		taskCompletedTimeLabells.setVisible(false);
		progressChart.setVisible(false); // New percentage		

		panel.setFocusable(true);
		
		displayTaskStatusCounts();
		
		SearchBar.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (SearchBar.getText().contains("Search Bar")) {
					SearchBar.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (SearchBar.getText().isEmpty()) {
					SearchBar.setText("Search Bar");
				}
			}

		});
		
		
		workerFiled.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (workerFiled.getText().equals("Worker Name")) {
                	workerFiled.setText("");  // Clear the default text
                }
            }
        });
		
		
		
		
		
		workerFiled.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (workerFiled.getText().contains("Worker Name")) {
					workerFiled.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (workerFiled.getText().isEmpty()) {
					workerFiled.setText("Worker Name");
				}
			}

		});

		workerFiled.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (workerFiled.getText().contains("Worker Name")) {
					workerFiled.setText(""); // Clear the text field
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (workerFiled.getText().isEmpty()) {
					workerFiled.setText("Worker Name");
				}
			}
		});
		
		projectName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (projectName.getText().equals("Project Name")) {
                	projectName.setText("");  // Clear the default text
                }
            }
        });

		projectName.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (projectName.getText().contains("Project Name")) {
					projectName.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (projectName.getText().isEmpty()) {
					projectName.setText("Project Name");
				}
			}

		});
		
		taskField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (taskField.getText().equals("Task Title")) {
                	taskField.setText("");  // Clear the default text
                }
            }
        });

		taskField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (taskField.getText().contains("Task Title")) {
					taskField.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (taskField.getText().isEmpty()) {
					taskField.setText("Task Title");
				}
			}

		});

		descriptionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (descriptionField.getText().equals("Task Description")) {
                	descriptionField.setText("");  // Clear the default text
                }
            }
        });
		
		descriptionField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (descriptionField.getText().contains("Task Description")) {
					descriptionField.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (descriptionField.getText().isEmpty()) {
					descriptionField.setText("Task Description");
				}
			}

		});

		dueDateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Remove default text as soon as any key is pressed
                if (dueDateField.getText().equals("Due Date (YYYY-MM-DD)")) {
                	dueDateField.setText("");  // Clear the default text
                }
            }
        });
		
		dueDateField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (dueDateField.getText().contains("Due Date (YYYY-MM-DD)")) {
					dueDateField.setText(""); // Clear the text field
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				if (dueDateField.getText().isEmpty()) {
					dueDateField.setText("Due Date (YYYY-MM-DD)");
				}
			}

		});

		statusComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// Ensure the selected item is a String before calling contains
				Object selectedItem = statusComboBox.getSelectedItem();
				if (selectedItem instanceof String && ((String) selectedItem).contains("Due Date (YYYY-MM-DD)")) {
					statusComboBox.setSelectedItem(null); // Clear the combo box selection
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				// Check if no item is selected, then set the placeholder
				if (statusComboBox.getSelectedItem() == null) {
					statusComboBox.setSelectedItem("Due Date (YYYY-MM-DD)"); // Set placeholder text as selected item
				}
			}
		});



		okButton = new JButton("OK");
		okButton.setBackground(Color.GREEN);
		okButton.setHorizontalAlignment(SwingConstants.LEFT);

		// Optionally, set padding to add some space between text and button edges
		okButton.setMargin(new Insets(0, 10, 0, 0));
		okButton.setBounds(180, 235, 44, 20);
		panel.add(okButton);

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchQuery = SearchBar.getText();
				projectNameComboBox.setSelectedItem("Project");
				if (!searchQuery.isEmpty() && !searchQuery.equals("Search Bar")) {
					for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
						JCheckBox checkBox = entry.getValue();
						checkBox.setSelected(true);
					}
					deleteSelectedTasks(dynamicPanel);
					loadRecords(dynamicPanel, searchQuery, ""); // Load tasks based on search query
					// projectNameComboBox.setSelectedItem("Project");
					notOkButton.setVisible(true);
					okButton.setVisible(false);
					panel.revalidate();
					panel.repaint();

				}
			}
		});

		notOkButton = new JButton("✖️");
		notOkButton.setBackground(Color.GREEN);
		notOkButton.setBounds(180, 235, 44, 20);
		notOkButton.setVisible(false);
		notOkButton.addActionListener(e -> {
			String searchQuery = SearchBar.getText();
			if (!searchQuery.isEmpty() && !searchQuery.equals("Search Bar")) {
				for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
					JCheckBox checkBox = entry.getValue();
					checkBox.setSelected(true);
				}
				deleteSelectedTasks(dynamicPanel);
				projectNameComboBox.setSelectedItem("Project");
				loadRecords(dynamicPanel, "", "");
				SearchBar.setText("Search Bar");
				okButton.setVisible(true);
				notOkButton.setVisible(false);
			}
		});
		panel.add(notOkButton);

//        JLabel titileHeadLine = new JLabel("__________________________________________________________________________________________________________________________");
//        titileHeadLine.setBounds(8, 228, 3400, 20);
//       // taskLabel.setFont(Font.B)t
//        titileHeadLine.setForeground(Color.WHITE);
//        panel.add(titileHeadLine);
//        permanentLabels.add(titileHeadLine);

		taskCheckBox1 = new JCheckBox();
		taskCheckBox1.setBounds(2, 258, 20, 20); // Position checkbox
		taskCheckBox1.setVisible(true);
		taskCheckBox1.setEnabled(true);
		taskCheckBox1.setForeground(Color.BLUE);
		taskCheckBox1.setBackground(Color.BLUE);
		taskCheckBox1.setVisible(false);
		panel.add(taskCheckBox1);

		taskCheckBox1.addActionListener(y -> {
//        	for (JCheckBox checkBox : taskCheckboxes.values()) {
//        		String taskTitle 
//        		if(!checkBox.isSelected())	
//        			checkBox.setSelected(true);
//        		else
//        			checkBox.setSelected(false);
//            }
			String selectedProject = (String) projectNameComboBox.getSelectedItem();
			System.out.println(selectedProject);
			// Iterate through all the checkboxes
			for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
				JCheckBox checkBox = entry.getValue();

				// If the checkbox is selected
				// if (checkBox.isSelected()) {
				String taskTitle = entry.getKey(); // Get the task title from the checkbox's key
				Task task = taskMap.get(taskTitle); // Fetch the Task object from the taskMap using the taskTitle

				// Check if the task is associated with the selected project
				if (task != null && task.getProject().equals(selectedProject)) {
					checkBox.setSelected(true);
					System.out.println(selectedProject + "ffdg");
				} else if (selectedProject.equals("Project")) {
					checkBox.setSelected(true);
				} else {
					// If the task is not associated with the selected project, you may want to
					// unselect it
					checkBox.setSelected(false); // Deselect the checkbox if it's not part of the selected project
					System.out.println(selectedProject + "ffdg");
				}
				// }
			}

		});

		JLabel nameLine = new JLabel("Worker");
		nameLine.setBounds(28, 258, 60, 20);
		// taskLabel.setFont(Font.B)t
		nameLine.setForeground(Color.WHITE);
		panel.add(nameLine);
		permanentLabels.add(nameLine);

		JLabel titileLine = new JLabel("Title");
		titileLine.setBounds(162, 258, 200, 20);
		// taskLabel.setFont(Font.B)t
		titileLine.setForeground(Color.WHITE);
		panel.add(titileLine);
		permanentLabels.add(titileLine);

		JLabel dueDateLine = new JLabel("Due Date");
		dueDateLine.setBounds(362, 258, 150, 20);
		// taskLabel.setFont(Font.B)t
		dueDateLine.setForeground(Color.WHITE);
		panel.add(dueDateLine);
		permanentLabels.add(dueDateLine);

		JLabel statusLine = new JLabel("Status");
		statusLine.setBounds(468, 258, 90, 20);
		// taskLabel.setFont(Font.B)t
		statusLine.setForeground(Color.WHITE);
		panel.add(statusLine);
		permanentLabels.add(statusLine);

		JLabel priorityLine = new JLabel("Priority");
		priorityLine.setBounds(541, 258, 90, 20);
		// taskLabel.setFont(Font.B)t
		priorityLine.setForeground(Color.WHITE);
		panel.add(priorityLine);
		permanentLabels.add(priorityLine);

		JLabel titileHeadLine2 = new JLabel(
				"__________________________________________________________________________________________________________________________");
		titileHeadLine2.setBounds(8, 262, 3400, 20);
		// taskLabel.setFont(Font.B)t
		titileHeadLine2.setForeground(Color.WHITE);
		panel.add(titileHeadLine2);
		permanentLabels.add(titileHeadLine2);

		updateButton.setVisible(false);
		updateButton.setEnabled(false);

		addButton = new JButton("Add");
		addButton.setBackground(Color.WHITE);
		addButton.setBounds(317, 172, 58, 38);
		addButton.setForeground(Color.BLACK);
		
		clearButton = new JButton("Clear");
		clearButton.setBackground(Color.WHITE);
		clearButton.setBounds(376, 172, 68, 38);
		clearButton.setForeground(Color.BLACK);
		panel.add(clearButton);
		
		clearButton.addActionListener(e -> {
			workerFiled.setText("Worker Name");
			projectName.setText("Project Name");
			taskField.setText("Task Title");
			descriptionField.setText("Task Description");
			dueDateField.setText("Due Date (YYYY-MM-DD)");
			statusComboBox.setSelectedItem((String) (String) "To Do");
			priorityComboBox.setSelectedItem((String) "Low");
		});
		if(taskMap != null) {
		//System.out.println("size of task mp "+taskMap.size());
		taskMapTemp = new TreeMap<>(taskMap);
		loadRecords(dynamicPanel, "", "");
		}
		List<JLabel> listJLabel = new ArrayList<JLabel>();
		addButton.addActionListener(e -> {
			String workerName = workerFiled.getText();
			String taskTitle = taskField.getText();
			String description = descriptionField.getText();
			String dueDate = dueDateField.getText();
			String status = (String) statusComboBox.getSelectedItem();
			String taskpriority = (String) priorityComboBox.getSelectedItem();
			String project = projectName.getText();
//			if(workerName.equals("Worker Name"))
//				workerName="";

			if(description.equals("Task Description"))
				description="";
			if(dueDate.equals("Due Date (YYYY-MM-DD)"))
				dueDate="";
				
				
			// System.out.println("update"+taskMap.size());
			projectNamesList.add("Project");
			projectNamesList.add(projectName.getText());
			// System.out.println("list size"+projectNamesList.size());
			String[] projectNames = projectNamesList.toArray(new String[0]);
			for (Component comp : panel.getComponents()) {
				if (comp instanceof JComboBox) {
					JComboBox compobject = (JComboBox) comp;
					if (!jcomboBoxes.contains(compobject)) {
						panel.remove(compobject);
					}
				}
			}
			projectNameComboBox = new JComboBox<>(projectNames);
			// projectNameComboBox.setVisible(false);
			projectNameComboBox.setSelectedItem("Project");
			projectNameComboBox.setBounds(240, 235, 150, 20);
			panel.add(projectNameComboBox);

			projectNameComboBox.addActionListener(r -> {
				String selectedProject = projectNameComboBox.getSelectedItem().toString(); // Get the selected item as a
																							// string

				// Set all checkboxes to selected (assuming you want all to be selected)
				for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
					JCheckBox checkBox = entry.getValue();
					checkBox.setSelected(true);
				}

				// Check if the selected project is not the default "Project"
				if (!selectedProject.equals("Project")) {
					// When the selected project is valid (not "Project")
					deleteSelectedTasks(dynamicPanel); // Delete previously selected tasks (if necessary)
					loadRecords(dynamicPanel, "", selectedProject); // Load records for the selected project
				} else {
					// If "Project" (default) is selected, reset the task display
					deleteSelectedTasks(dynamicPanel); // Delete previously selected tasks
					loadRecords(dynamicPanel, "", ""); // Load all records (or reset the view)
				}
			});

			panel.revalidate();
			panel.repaint();
			// projectNameComboBox.setVisible(true);
			// If task exists, update it instead of adding a new one
			if (taskMap!=null && taskMap.containsKey(taskTitle)) {
				Task task = taskMap.get(taskTitle);
				task.setStatus(status); // Update task status
				task.setTitle(taskTitle);
				task.setDescription(description);
				task.setDueDate(dueDate);
				task.setPriority(taskpriority);
				task.setWorker(workerName);
				task.setProject(project);
				// Update task in taskMap
				taskMap.put(taskTitle, task);

				// Update the task in the UI
				updateTaskLabel(task, dynamicPanel);

				// Set the updated values back to the fields
				workerFiled.setText(workerName);
				projectName.setText(project);
				taskField.setText(taskTitle);
				descriptionField.setText(description);
				dueDateField.setText(dueDate);
				statusComboBox.setSelectedItem(status);
				priorityComboBox.setSelectedItem(taskpriority);
				
				// Repaint panel to reflect the updated task
				dynamicPanel.revalidate();
				dynamicPanel.repaint();
			} else {
				Task newTask = new Task(workerName, taskTitle, description, dueDate, status, taskpriority, project);
				if(taskMap != null)
					taskMap.put(taskTitle, newTask);

				// Create checkbox for task
				JCheckBox taskCheckBox = new JCheckBox();
				taskCheckBox.setBounds(2, j, 20, 20); // Position checkbox
				taskCheckBox.setVisible(false);
				taskCheckBox.setEnabled(false);
				taskCheckBox.setForeground(Color.RED);
				taskCheckBox.setBackground(Color.RED);
				dynamicPanel.add(taskCheckBox);

				// Store the checkbox
				taskCheckboxes.put(taskTitle, taskCheckBox);

				JLabel workerLabelName = new JLabel(workerName);
				workerLabelName.setBounds(28, j, 124, 20);
				workerLabelName.setForeground(Color.WHITE);
				dynamicPanel.add(workerLabelName);

				// Create the task label
				JLabel taskLabel = new JLabel(taskTitle);
				taskLabel.setBounds(162, j, 200, 20);
				taskLabel.setForeground(Color.WHITE);
				dynamicPanel.add(taskLabel);

				// Other components like status label, due date label, radio buttons
				JLabel taskLabelDueDate = new JLabel(dueDate);
				taskLabelDueDate.setBounds(362, j, 150, 20);
				taskLabelDueDate.setForeground(Color.WHITE);
				dynamicPanel.add(taskLabelDueDate);

				JLabel taskLabelStatus = new JLabel(status);
				taskLabelStatus.setBounds(468, j, 90, 20);
				taskLabelStatus.setForeground(Color.WHITE);
				dynamicPanel.add(taskLabelStatus);

				JLabel taskPriority = new JLabel(taskpriority);
				taskPriority.setBounds(541, j, 66, 20);
				taskPriority.setForeground(Color.WHITE);
				dynamicPanel.add(taskPriority);

				JRadioButton jradioButton = new JRadioButton();
				jradioButton.setBounds(2, j, 18, 17);
				dynamicPanel.add(jradioButton);

				group.add(jradioButton);
				taskJRadioButtons.put(taskTitle, jradioButton);
				recordJLabelMap.put(taskTitle,
						new ArrayList<>(List.of(taskLabel, taskLabelDueDate, taskLabelStatus, taskPriority, workerLabelName)));

				// Create the "Time Tracking" button
				JButton timeTrackingButton = new JButton("T");
				Font f = new Font("", 0, 0);

				timeTrackingButton.setBounds(600, j, 42, 20);
				timeTrackingButton.setBackground(Color.BLUE);
				timeTrackingButton.setForeground(Color.WHITE);
				timeTrackingButton.addActionListener(k -> {
					jradioButton.setSelected(true);
					if(timeSpentMapofMaps == null) {
						Map<String, String> timeSpentMapTemp = new HashMap<>();
						timeSpentMapofMaps.put(newTask.title, timeSpentMapTemp);
					}
					openPopupFrame(newTask.title, timeSpentMapofMaps.get(newTask.title));
					displayTaskSpecificStatusDetails(newTask.title, panel);
					});
				dynamicPanel.add(timeTrackingButton);

				taskJButtons.put(taskTitle, timeTrackingButton);
				// Create the "Notes" button
				JButton notesButton = new JButton("N");
				notesButton.setBackground(Color.GREEN);
				notesButton.setForeground(Color.BLACK);
				notesButton.setBounds(644, j, 43, 20);
				notesButton.addActionListener(l -> {
					jradioButton.setSelected(true);
					if(noteMap != null)
					openNotesFrame(newTask.title, noteMap.get(newTask.title));});
				    //displayTaskSpecificStatusDetails(newTask.title, panel);
				    dynamicPanel.add(notesButton);

				// Action Listener for the radio button
				jradioButton.addActionListener(r -> {
					if(jradioButton.isSelected()) {
					workerLabelName.setText(newTask.worker);
					workerLabelName.setForeground(Color.GREEN);
					taskLabel.setText(newTask.title);
					taskLabel.setForeground(Color.GREEN);
					taskLabelDueDate.setText(newTask.dueDate);
					taskLabelDueDate.setForeground(Color.GREEN);
					taskLabelStatus.setText(newTask.status);
					taskLabelStatus.setForeground(Color.GREEN);
					//taskPriority.set
					taskPriority.setForeground(Color.GREEN);
					
					workerFiled.setText(newTask.worker);					
					projectName.setText(newTask.project);
					priorityComboBox.setSelectedItem(newTask.priority);					
					taskField.setText(newTask.title);
					descriptionField.setText(newTask.description);
					dueDateField.setText(newTask.dueDate);
					statusComboBox.setSelectedItem(newTask.status);
					
					
					taskStartTimeLabel.setVisible(false);
					taskEmdTimeLabel.setVisible(false);
					taskCompletedTimeLabel.setVisible(false);
					
					taskStartTimeLabell.setVisible(false);
					taskEmdTimeLabell.setVisible(false);
					taskCompletedTimeLabell.setVisible(false);
					
					taskStartTimeLabels.setVisible(false);
					taskEmdTimeLabels.setVisible(false);
					taskCompletedTimeLabells.setVisible(false);
					progressChart.setVisible(false); // New percentage	
					displayTaskSpecificStatusDetails(newTask.title, panel);
					panel.revalidate();
					panel.repaint();
					}else {
						workerLabelName.setText(newTask.worker);
						workerLabelName.setForeground(Color.WHITE);
						taskLabel.setText(newTask.title);
						taskLabel.setForeground(Color.GREEN);
						taskLabelDueDate.setText(newTask.dueDate);
						taskLabelDueDate.setForeground(Color.GREEN);
						taskLabelStatus.setText(newTask.status);
						taskLabelStatus.setForeground(Color.GREEN);
						//taskPriority.set
						taskPriority.setForeground(Color.GREEN);
					}
				});

				// Update panel with new task
				j = j + 28;
				dynamicPanel.revalidate();
				dynamicPanel.repaint();
			}
			if(taskMap != null)
				taskMapTemp = new TreeMap<>(taskMap);
			try {
				if(mapOfAllMaps != null)
				mapOfAllMaps.put("taskMap", taskMap);
				projectMapList.put("projectNamesList", projectNamesList);
				if(mapOfAllMaps != null)
				mapOfAllMaps.put("projectMapList", projectMapList);
				FileToSaveRecord.saveMap(mapOfAllMaps);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//			workerFiled.setText("Worker Name");
//			projectName.setText("Project Name");
//			taskField.setText("Task Title");
//			descriptionField.setText("Task Description");
//			dueDateField.setText("Due Date (YYYY-MM-DD)");
//			statusComboBox.setSelectedItem((String) (String) "To Do");
//			priorityComboBox.setSelectedItem((String) "Low");
			displayTaskStatusCounts();
			int panelHeight = dynamicPanel.getComponentCount() * 34;
			dynamicPanel.setPreferredSize(new java.awt.Dimension(240, panelHeight));
			scrollPane.revalidate();
			scrollPane.repaint();
		});

		panel.add(addButton);

		// Select button to toggle checkboxes
		selectButton = new JButton("✔️");
		selectButton.setBackground(lightBrown);
		selectButton.setForeground(Color.WHITE);
		selectButton.setBounds(692, 220, 44, 27);
		selectButton.addActionListener(e -> {
			toggleCheckboxes();
			deSelectButton.setVisible(true);
			selectButton.setVisible(false);
			taskCheckBox1.setVisible(true);
		});
		panel.add(selectButton);

		deSelectButton = new JButton("✖️");
		deSelectButton.setBackground(lightBrown);
		deSelectButton.setForeground(Color.WHITE);
		deSelectButton.setBounds(692, 220, 44, 27);
		// deSelectButton.setVisible(false);
		deSelectButton.addActionListener(e -> {
			selectButton.setVisible(true);
			deSelectButton.setVisible(false);
			taskCheckBox1.setVisible(false);
			taskCheckBox1.setSelected(false);
			deToggleCheckboxes();
		});
		panel.add(deSelectButton);

		// Delete button to delete selected tasks
		deleteButton = new JButton();
		deleteButton.setBackground(Color.WHITE);
		try {
			Image img = ImageIO.read(Tasktracker.class.getResource("/trash.png"));
			deleteButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		deleteButton.setBounds(444, 172, 42, 38);
		deleteButton.addActionListener(e -> {
			// projectNameComboBox.setSelectedItem("Project");
			for (Component comp : panel.getComponents()) {
				if (comp instanceof JComboBox) {
					JComboBox compobject = (JComboBox) comp;
					if (!jcomboBoxes.contains(compobject)) {
						panel.remove(compobject);
					}
				}
			}

			for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
				JCheckBox checkBox = entry.getValue();
				if (checkBox.isSelected()) {
					String taskTitle = entry.getKey();

					// String taskTitle = taskField.getText();
					Task selectedTask = taskMap.get(taskTitle);

					if (selectedTask != null) {
						String project = selectedTask.getProject();
						int i = 0;
						for (Map.Entry<String, Task> entryc : taskMap.entrySet()) {
							Task task = entryc.getValue();
							if (task.getProject().equals(project)) {
								i++;
							}

						}
						if (project != null && !project.isEmpty() && projectNamesList.contains(project)) {
							projectNamesList.remove(project);
						}
					}

				}
			}

			for (Map.Entry<String, JRadioButton> entry : taskJRadioButtons.entrySet()) {
				JRadioButton radiobutton = entry.getValue();
				if (radiobutton.isSelected()) {
					String taskTitle = taskField.getText();
					Task selectedTask = taskMap.get(taskTitle);

					if (selectedTask != null) {
						String project = selectedTask.getProject();
						int i = 0;
						for (Map.Entry<String, Task> entryr : taskMap.entrySet()) {
							Task task = entryr.getValue();
							if (task.getProject().equals(project)) {
								i++;
							}

						}
						if (project != null && !project.isEmpty() && projectNamesList.contains(project) && i == 1) {
							projectNamesList.remove(project);
						}
					}

				}
			}

			panel.revalidate();
			panel.repaint();

			String[] projectNames = projectNamesList.toArray(new String[0]);

			projectNameComboBox = new JComboBox<>(projectNames);
			projectNameComboBox.setVisible(true);
			projectNameComboBox.setSelectedItem("Project");
			projectNameComboBox.setBounds(240, 235, 150, 20);
			panel.add(projectNameComboBox);

			projectNameComboBox.addActionListener(r -> {
				for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
					JCheckBox checkBox = entry.getValue();
					checkBox.setSelected(true);
				}
				if (!projectNameComboBox.getSelectedItem().toString().equals("Project")) {
					deleteSelectedTasks(dynamicPanel);
					loadRecords(dynamicPanel, "", projectNameComboBox.getSelectedItem().toString());
				} else {
					deleteSelectedTasks(dynamicPanel);
					// taskMapTemp = new TreeMap<>(taskMap);
					loadRecords(dynamicPanel, "", "");
				}
			});

			deleteSelectedTasks(dynamicPanel);

			if (projectNamesList.contains("Project") && projectNamesList.size() == 1) {
				panel.remove(projectNameComboBox);
				panel.revalidate();
				panel.repaint();

			}
System.out.println(taskMap.size());
try {
	mapOfAllMaps.put("taskMap",taskMap);
	projectMapList.put("projectNamesList", projectNamesList);
	mapOfAllMaps.put("projectMapList", projectMapList);
	FileToSaveRecord.saveMap(mapOfAllMaps);
	
	//FileToSaveRecord.saveMap(taskMap);
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
		});
		panel.add(deleteButton);
		//updateProjectDropdown();
		frame.setVisible(true);
		// JScrollBar verticalScrollBar = ((JScrollPane)
		// panel.getParent().getParent()).getVerticalScrollBar();
		// System.out.println(verticalScrollBar.getMaximum());
	}

	// Helper to style buttons
    private static void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(40, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Optional hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.RED);
            }

            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });
    }
	// Toggle the checkboxes enabled/disabled
	private static void toggleCheckboxes() {
		for (JCheckBox checkBox : taskCheckboxes.values()) {
			checkBox.setEnabled(true); // Toggle checkbox state
			checkBox.setVisible(true);
		}
	}

	private static void deToggleCheckboxes() {
		for (JCheckBox checkBox : taskCheckboxes.values()) {
			checkBox.setSelected(false);
			checkBox.setEnabled(false); // Toggle checkbox state
			checkBox.setVisible(false);
		}
	}

	private static void loadRecords(JPanel panel, String searchQuery, String projectDropdown) {
		// if(searchQuery!=null && !searchQuery.isEmpty())
		// j=287;
		// else
		j = 10;
		// taskMap = new HashMap<>(taskMapTemp);
		// searchQuery = searchQuery.trim();

		// Create a new taskMap to hold the matched tasks
		Map<String, Task> filteredTaskMap = new TreeMap<>();
		// Map<String, Task> taskMapTemp = new HashMap<>();

		// Check if the search query is empty
		// True if search query is empty, false otherwise

		boolean isProjectEmpty = projectDropdown.isEmpty();
////    	    System.out.println(filteredTaskMap.size());
////    	    // If the search query is not empty, filter the tasks based on the query
//    	    if (!isProjectEmpty && !projectDropdown.equals("Project")) {
//    	    	taskMapTemp.forEach((taskTitle, task) -> {
//    	    		
//    	    		boolean projectSelect = task.project.equals(projectDropdown);
//    	    		
//					if(projectSelect) {
//						System.out.println(task.project+"---"+projectNameComboBox.getSelectedItem().toString());
//						filteredTaskMap.put(taskTitle, task);
//                        System.out.println(filteredTaskMap.size());
//						panel.revalidate();
//						panel.repaint();		
//				    }
//					
//    	        });
//    	    } 

		boolean isSearchEmpty = searchQuery.isEmpty();
		if (!isSearchEmpty || !isProjectEmpty) {
			taskMapTemp.forEach((taskTitle, task) -> {
				if (!isSearchEmpty) {
					boolean matchesSearchQuery = task.worker.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.title.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.dueDate.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.status.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.priority.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.project.toLowerCase().contains(searchQuery.toLowerCase());

					if (matchesSearchQuery) {
						// Add matched tasks to the filtered map

						filteredTaskMap.put(taskTitle, task);

						panel.revalidate();
						panel.repaint();
					}
				}

				if (!isProjectEmpty) {
					boolean projectSelect = task.project.equals(projectDropdown);

					if (projectSelect) {
						// System.out.println(task.project+"---"+projectNameComboBox.getSelectedItem().toString());
						filteredTaskMap.put(taskTitle, task);
						// System.out.println(filteredTaskMap.size());
						panel.revalidate();
						panel.repaint();
					}
				}

				if (!isSearchEmpty && !isProjectEmpty) {
					// Map<String, Task> filteredTaskMaptemp = new TreeMap<>();

					boolean matchesSearchQuery = task.worker.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.title.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.dueDate.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.status.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.priority.toLowerCase().contains(searchQuery.toLowerCase())
							|| task.project.contains(searchQuery.toLowerCase());

					if (matchesSearchQuery) {
						// Add matched tasks to the filtered map

						filteredTaskMap.put(taskTitle, task);
						// filteredTaskMap = new TreeMap<>(filteredTaskMaptemp);
						panel.revalidate();
						panel.repaint();
					}

				}

			});
		} else {
			// taskMapTemp = new TreeMap<>(taskMap);
			filteredTaskMap.putAll(taskMapTemp);
			panel.revalidate();
			panel.repaint();
		}

		// String[] projectNames= projectNamesList.toArray(new String[0]);

//      		projectNameComboBox = new JComboBox<>(projectNames);
//      		//projectNameComboBox.setVisible(false);
//      		projectNameComboBox.setSelectedItem("Project");
//            projectNameComboBox.setBounds(240, 235, 150, 20);
//            panel.add(projectNameComboBox);
//            
//            projectNameComboBox.addActionListener(r -> {
//            	for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
//                    JCheckBox checkBox = entry.getValue();
//                    checkBox.setSelected(true);
//            	}
//            	if(!projectNameComboBox.getSelectedItem().toString().equals("Project")) {
//                deleteSelectedTasks(dynamicPanel);
//                loadRecords(dynamicPanel, "", projectNameComboBox.getSelectedItem().toString());
//            	}else {
//            		deleteSelectedTasks(dynamicPanel);
//            		//taskMapTemp = new TreeMap<>(taskMap);
//            		loadRecords(dynamicPanel, "", "");
//            	}
//            });

		// System.out.println(filteredTaskMap.size());
		filteredTaskMap.toString();
		filteredTaskMap.forEach((taskTitle, newTask) -> {
			// Create checkbox for task
			JCheckBox taskCheckBox = new JCheckBox();
			taskCheckBox.setBounds(2, j, 20, 20); // Position checkbox
			taskCheckBox.setVisible(false);
			taskCheckBox.setEnabled(false);
			taskCheckBox.setForeground(Color.RED);
			taskCheckBox.setBackground(Color.RED);
			panel.add(taskCheckBox);

			// Store the checkbox
			taskCheckboxes.put(taskTitle, taskCheckBox);

			JLabel workerLabelName = new JLabel(newTask.worker);
			workerLabelName.setBounds(28, j, 124, 20);
			workerLabelName.setForeground(Color.WHITE);
			panel.add(workerLabelName);

			// Create the task label
			JLabel taskLabel = new JLabel(taskTitle);
			taskLabel.setBounds(162, j, 200, 20);
			taskLabel.setForeground(Color.WHITE);
			panel.add(taskLabel);

			// Other components like status label, due date label, radio buttons
			JLabel taskLabelDueDate = new JLabel(newTask.dueDate);
			taskLabelDueDate.setBounds(362, j, 150, 20);
			taskLabelDueDate.setForeground(Color.WHITE);
			panel.add(taskLabelDueDate);

			JLabel taskLabelStatus = new JLabel(newTask.status);
			taskLabelStatus.setBounds(468, j, 90, 20);
			taskLabelStatus.setForeground(Color.WHITE);
			panel.add(taskLabelStatus);

			JLabel taskPriority = new JLabel(newTask.priority);
			taskPriority.setBounds(541, j, 66, 20);
			taskPriority.setForeground(Color.WHITE);
			panel.add(taskPriority);

			JRadioButton jradioButton = new JRadioButton();
			jradioButton.setBounds(2, j, 18, 17);
			panel.add(jradioButton);

			group.add(jradioButton);
			taskJRadioButtons.put(taskTitle, jradioButton);
			recordJLabelMap.put(taskTitle,
					new ArrayList<>(List.of(taskLabel, taskLabelDueDate, taskLabelStatus, taskPriority, workerLabelName)));

			// Create the "Time Tracking" button
			JButton timeTrackingButton = new JButton("T");
			timeTrackingButton.setBounds(600, j, 42, 20);
			timeTrackingButton.setBackground(Color.BLUE);
			timeTrackingButton.setForeground(Color.WHITE);
			timeTrackingButton.addActionListener(k -> {
				//System.out.println(newTask.title+"-inoke--"+timeSpentMapofMaps.get(newTask.title).size());
				jradioButton.setSelected(true);
				if(timeSpentMapofMaps == null) {
					Map<String, String> timeSpentMapTemp = new HashMap<>();
					timeSpentMapofMaps.put(newTask.title, timeSpentMapTemp);
				}
				openPopupFrame(newTask.title, timeSpentMapofMaps.get(newTask.title));
				displayTaskSpecificStatusDetails(newTask.title, panel);
				//panel.revalidate();
				//panel.repaint();
			});
			panel.add(timeTrackingButton);
			taskJButtons.put(taskTitle, timeTrackingButton);
			// Create the "Notes" button
			JButton notesButton = new JButton("N");
			notesButton.setBackground(Color.GREEN);
			notesButton.setForeground(Color.BLACK);
			notesButton.setBounds(644, j, 43, 20);
			notesButton.addActionListener(l ->{ 
				jradioButton.setSelected(true);
				if(noteMap != null)
				openNotesFrame(newTask.title, noteMap.get(newTask.title));});
			   //displayTaskSpecificStatusDetails(newTask.title, panel);
			panel.add(notesButton);

			// Action Listener for the radio button
			jradioButton.addActionListener(r -> {

					workerLabelName.setText(newTask.worker);
					workerLabelName.setForeground(Color.GREEN);
					taskLabel.setText(newTask.title);
					taskLabel.setForeground(Color.GREEN);
					taskLabelDueDate.setText(newTask.dueDate);
					taskLabelDueDate.setForeground(Color.GREEN);
					taskLabelStatus.setText(newTask.status);
					taskLabelStatus.setForeground(Color.GREEN);
					//taskPriority.set
					taskPriority.setForeground(Color.GREEN);
					
					workerFiled.setText(newTask.worker);					
					projectName.setText(newTask.project);
					priorityComboBox.setSelectedItem(newTask.priority);					
					taskField.setText(newTask.title);
					descriptionField.setText(newTask.description);
					dueDateField.setText(newTask.dueDate);
					statusComboBox.setSelectedItem(newTask.status);
					
					
					taskStartTimeLabel.setVisible(false);
					taskEmdTimeLabel.setVisible(false);
					taskCompletedTimeLabel.setVisible(false);
					
					taskStartTimeLabell.setVisible(false);
					taskEmdTimeLabell.setVisible(false);
					taskCompletedTimeLabell.setVisible(false);
					
					taskStartTimeLabels.setVisible(false);
					taskEmdTimeLabels.setVisible(false);
					taskCompletedTimeLabells.setVisible(false);
					progressChart.setVisible(false); // New percentage	
					displayTaskSpecificStatusDetails(newTask.title, panel);
					
//					for (Map.Entry<String, JRadioButton> entry : taskJRadioButtons.entrySet()) {
//						//JRadioButton jRadioButton = entry.getValue();
//						if(!jradioButton.isSelected()) {
//						workerLabelName.setForeground(Color.WHITE);
//						//taskLabel.setText(newTask.title);
//						taskLabel.setForeground(Color.WHITE);
//						//taskLabelDueDate.setText(newTask.dueDate);
//						taskLabelDueDate.setForeground(Color.WHITE);
//						//taskLabelStatus.setText(newTask.status);
//						taskLabelStatus.setForeground(Color.WHITE);
//						//taskPriority.set
//						taskPriority.setForeground(Color.WHITE);
//						}
//					}
					
					panel.revalidate();
					panel.repaint();
					
			});

			// Update panel with new task
			j = j + 28;
			panel.revalidate();
			panel.repaint();

			// }

		});
				
		taskMap = new TreeMap<>(taskMapTemp);
		taskMap.toString();
		try {
			if(mapOfAllMaps != null)
			mapOfAllMaps.put("taskMap",taskMap);
			projectMapList.put("projectNamesList", projectNamesList);
			if(mapOfAllMaps != null)
			mapOfAllMaps.put("projectMapList", projectMapList);
			FileToSaveRecord.saveMap(mapOfAllMaps);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static void updateProjectDropdown(JPanel panel) {
//		projectNamesList.add("Project Name");
//		projectNamesList.add("a");
//		projectNamesList.add("Project");
//		
//		for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
//			JCheckBox checkBox = entry.getValue();
//			if (checkBox.isSelected()) {
//				String taskTitle = entry.getKey();
//
//				// String taskTitle = taskField.getText();
//				Task selectedTask = taskMap.get(taskTitle);
//
//				if (selectedTask != null) {
//					String project = selectedTask.getProject();
//					int i = 0;
//					for (Map.Entry<String, Task> entryc : taskMap.entrySet()) {
//						Task task = entryc.getValue();
//						if (task.getProject().equals(project)) {
//							i++;
//						}
//
//					}
//					if (project != null && !project.isEmpty() && projectNamesList.contains(project)) {
//						projectNamesList.remove(project);
//					}
//				}
//
//			}
//		}
//
//		for (Map.Entry<String, JRadioButton> entry : taskJRadioButtons.entrySet()) {
//			JRadioButton radiobutton = entry.getValue();
//			if (radiobutton.isSelected()) {
//				String taskTitle = taskField.getText();
//				Task selectedTask = taskMap.get(taskTitle);
//
//				if (selectedTask != null) {
//					String project = selectedTask.getProject();
//					int i = 0;
//					for (Map.Entry<String, Task> entryr : taskMap.entrySet()) {
//						Task task = entryr.getValue();
//						if (task.getProject().equals(project)) {
//							i++;
//						}
//
//					}
//					if (project != null && !project.isEmpty() && projectNamesList.contains(project) && i == 1) {
//						projectNamesList.remove(project);
//					}
//				}
//
//			}
//		}

	//	panel.revalidate();
	//	panel.repaint();
		// Convert the updated projectNamesList to an array
		String[] projectNames = projectNamesList.toArray(new String[0]);
		
		// Remove the old JComboBox if it exists
		if (projectNameComboBox != null) {
			panel.remove(projectNameComboBox);
		}

		// Create a new JComboBox with the updated projectNames
		projectNameComboBox = new JComboBox<>(projectNames);
		projectNameComboBox.setVisible(true);
		// Set the default "Project" option and other properties
		projectNameComboBox.setSelectedItem("Project");
		projectNameComboBox.setBounds(240, 235, 150, 20);
		panel.add(projectNameComboBox);

		// Revalidate and repaint the panel to update the UI
		panel.revalidate();
		panel.repaint();

		// Add ActionListener to handle selection changes in the dropdown
		projectNameComboBox.addActionListener(r -> {
			for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
				JCheckBox checkBox = entry.getValue();
				checkBox.setSelected(true); // Keep all checkboxes selected
			}

			String selectedProject = projectNameComboBox.getSelectedItem().toString();

			if (!selectedProject.equals("Project")) {
				deleteSelectedTasks(dynamicPanel); // Delete selected tasks
				loadRecords(dynamicPanel, "", selectedProject); // Load records for the selected project
			} else {
				deleteSelectedTasks(dynamicPanel); // Delete selected tasks
				loadRecords(dynamicPanel, "", ""); // Load all records if no project is selected
			}
		});
	}

	private static void deleteSelectedTasks(JPanel panel) {
		taskMap.toString();
		// removeProject();
		// selected=false;
		for (Map.Entry<String, JCheckBox> entry : taskCheckboxes.entrySet()) {
			JCheckBox checkBox = entry.getValue();
			if (checkBox.isSelected()) {
				String taskTitle = entry.getKey();

				// Remove the task from taskMap
				taskMap.remove(taskTitle);

			}
		}
		//
		for (Map.Entry<String, JRadioButton> entry : taskJRadioButtons.entrySet()) {
			JRadioButton jRadioButton = entry.getValue();
			if (jRadioButton.isSelected()) {
				String taskTitle = entry.getKey();
				taskMap.remove(taskTitle); // Remove task from map
				// taskJRadioButtons.remove(taskTitle);
				// selected = true;

			}
		}

		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel compobject = (JLabel) comp;
				if (!permanentLabels.contains(compobject)) {
					panel.remove(compobject);
				}
			}
			if (comp instanceof JRadioButton) {
				JRadioButton compobject = (JRadioButton) comp;
				taskField.setText("Task Title");
				descriptionField.setText("Task Description");
				dueDateField.setText("Due Date (YYYY-MM-DD)");
				statusComboBox.setSelectedItem((String) (String) "To Do");
				panel.remove(compobject);

			}
			if (comp instanceof JCheckBox) {
				JCheckBox compobject = (JCheckBox) comp;
				panel.remove(compobject);
			}

			if (comp instanceof JButton) {
				JButton compobject = (JButton) comp;

				if (compobject.getText().equals("Add Task") || compobject.getText().equals("Title")
						|| compobject.getText().equals("Update Task") || compobject.getText().equals("Due Date")
						|| compobject.getText().equals("Status") || compobject.getText().contains("_____________")
						|| (compobject.getBackground().equals(Color.WHITE)
								&& !compobject.getText().equals("Add Task"))) {
					continue;
				} else {
					panel.remove(compobject);
					// System.out.println(compobject.getText());
				}
			}

		}

		if (taskMap.size() != 0) {
			taskMapTemp = new TreeMap<>(taskMap);
			loadRecords(panel, "", "");
		} else {
			j = 10;
			panel.revalidate();
			panel.repaint();
		}
		selectButton.setVisible(true);
		deSelectButton.setVisible(false);
		taskCheckBox1.setVisible(false);
		taskCheckBox1.setSelected(false);
		deToggleCheckboxes();
		// projectNameComboBox.setSelectedItem("Project");
		displayTaskStatusCounts();
		
		taskStartTimeLabel.setVisible(false);
		taskEmdTimeLabel.setVisible(false);
		taskCompletedTimeLabel.setVisible(false);
		
		taskStartTimeLabell.setVisible(false);
		taskEmdTimeLabell.setVisible(false);
		taskCompletedTimeLabell.setVisible(false);
		
		taskStartTimeLabels.setVisible(false);
		taskEmdTimeLabels.setVisible(false);
		taskCompletedTimeLabells.setVisible(false);
		progressChart.setVisible(false); // New percentage	
		
		panel.revalidate();
		panel.repaint();

	}

	private static void removeProject() {
		String taskTitle = taskField.getText(); // You can use the task title or some identifier
		Task selectedTask = taskMap.get(taskTitle); // Get the Task object by title

		if (selectedTask != null) {
			String project = selectedTask.getProject(); // Get the project name from the Task object

			// Check if the project is in the list and remove it
			if (project != null && !project.isEmpty() && projectNamesList.contains(project)) {
				projectNamesList.remove(project);

				// Update the combo box with the new list of project names
				String[] projectNames = projectNamesList.toArray(new String[0]);
				projectNameComboBox.setModel(new DefaultComboBoxModel<>(projectNames));

				// Optionally, reset the combo box to a default value like "Project"
				projectNameComboBox.setSelectedItem("Project");

				// Optionally, you can also delete tasks related to that project
				// deleteSelectedTasks(dynamicPanel); // Clear tasks related to the deleted
				// project
				// loadRecords(dynamicPanel, "", ""); // Reload records with no project filter
			}
		}

	}

	private static void updateTaskLabel(Task task, JPanel panel) {
		// Loop through the components in the panel to find labels related to the task
		for (Map.Entry<String, List<JLabel>> entry : recordJLabelMap.entrySet()) {
			String taskTitle = entry.getKey();
			List<JLabel> taskLabels = entry.getValue();

			// If the task title matches, update the task's label and status label
			if (taskTitle.equals(task.getTitle())) {
				// Update task label with the new title (if necessary)
				for (JLabel label : taskLabels) {
					if (label.getText().equals(taskTitle)) {
						label.setText(task.getTitle());
					}
					if (label.getText().equals(task.getDueDate())) {
						label.setText(task.getDueDate());
					}
					if (label.getText().equals(task.getStatus())) {
						label.setText(task.getStatus());
					}
					if (label.getText().equals(task.getPriority())) {
						label.setText(task.getPriority());
					}
					if (label.getText().equals(task.getWorker())) {
						label.setText(task.getWorker());
					}
				}

				// Update the status label as well
				JLabel taskLabelStatus = taskLabels.get(2); // Assuming the 3rd label is the status
				taskLabelStatus.setText(task.getStatus());

				JLabel taskLabelStatus1 = taskLabels.get(1); // Assuming the 3rd label is the status
				taskLabelStatus1.setText(task.getDueDate());

				JLabel taskLabelPriority = taskLabels.get(3); // Assuming the 3rd label is the status
				taskLabelPriority.setText(task.getPriority());
				
				JLabel taskWorkerName = taskLabels.get(4); // Assuming the 3rd label is the status
				taskWorkerName.setText(task.getWorker());

				if (isOnlyOneTaskDisplayed(panel)) {
					deleteSelectedTasks(panel);
					loadRecords(panel, "", "");
				}
				displayTaskStatusCounts();
				// Revalidate and repaint to apply chan
				panel.revalidate();
				panel.repaint();
				break;
			}
		}
	}

	private static boolean isOnlyOneTaskDisplayed(JPanel panel) {
		// Initialize counters for task-related components
		int taskLabelCount = 0;
		int taskCheckBoxCount = 0;
		int taskRadioButtonCount = 0;

		// Iterate through the panel components
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JLabel) {
				JLabel label = (JLabel) comp;
				// Check if the JLabel is related to a task (e.g., task title label)
				if (label.getText() != null && !label.getText().isEmpty()) {
					taskLabelCount++;
				}
			}
			if (comp instanceof JCheckBox) {
				taskCheckBoxCount++;
			}
			if (comp instanceof JRadioButton) {
				taskRadioButtonCount++;
			}
		}

		// Check if there's exactly one task-related component set (1 label, 1 checkbox,
		// and 1 radio button)
		return taskLabelCount == 1 && taskCheckBoxCount == 1 && taskRadioButtonCount == 1;
	}

	private static void openPopupFrame(String title, Map<String, String> timeSpentMapTemp) {
		// Open time tracking frame
		//Map<String, String> timeSpentMap = timeSpentMapTemp.get(title);
    	
		taskStartTimeLabel.setVisible(false);
		taskEmdTimeLabel.setVisible(false);
		taskCompletedTimeLabel.setVisible(false);
		
		taskStartTimeLabell.setVisible(false);
		taskEmdTimeLabell.setVisible(false);
		taskCompletedTimeLabell.setVisible(false);
		
		taskStartTimeLabels.setVisible(false);
		taskEmdTimeLabels.setVisible(false);
		taskCompletedTimeLabells.setVisible(false);
		progressChart.setVisible(false); // New percentage	
		TimeTrackingApp popup = new TimeTrackingApp(title, timeSpentMapTemp);
		
		//timeSpentMap = popup.getTimeSpentMap();
		
		
	}
	
    public static void setTimeSpentMap(String taskName, Map<String, String> timeSpentMapTemp){
    	//return timeSpentMapTemp;
    	//timeSpentMap = new HashMap<>(timeSpentMapTemp);
    	timeSpentMapofMaps.put(taskName, timeSpentMapTemp);
    	mapOfAllMaps.put("timeSpentMapofMaps",timeSpentMapofMaps);
    	try {
			FileToSaveRecord.saveMap(mapOfAllMaps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for (Map.Entry<String, String> entry : timeSpentMap.entrySet()) {
			String taskTitle = entry.getKey();
			String value = entry.getValue();
			System.out.println(taskTitle+"------"+value);
		}
    	displayTaskSpecificStatusDetails(taskName, panel);
//		panel.revalidate();
//		panel.repaint();
    }
    
    public static void setNoteMap(String taskName, String note){
    	//return timeSpentMapTemp;
    	//timeSpentMap = new HashMap<>(timeSpentMapTemp);
    	noteMap.put(taskName, note);
    	mapOfAllMaps.put("noteMap",noteMap);
    	try {
			FileToSaveRecord.saveMap(mapOfAllMaps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	for (Map.Entry<String, String> entry : timeSpentMap.entrySet()) {
//			String taskTitle = entry.getKey();
//			String value = entry.getValue();
//			System.out.println(taskTitle+"------"+value);
//		}
    //	displayTaskSpecificStatusDetails(taskName, panel);
//		panel.revalidate();
//		panel.repaint();
    }
    
	private static void openNotesFrame(String noteTitle, String noteDescription) {
		// Open note-taking frame
		taskStartTimeLabel.setVisible(false);
		taskEmdTimeLabel.setVisible(false);
		taskCompletedTimeLabel.setVisible(false);
		
		taskStartTimeLabell.setVisible(false);
		taskEmdTimeLabell.setVisible(false);
		taskCompletedTimeLabell.setVisible(false);
		
		taskStartTimeLabels.setVisible(false);
		taskEmdTimeLabels.setVisible(false);
		taskCompletedTimeLabells.setVisible(false);
		progressChart.setPercent(0);
		progressChart.setVisible(false); // New percentage	
		
		NoteTakingApp popup = new NoteTakingApp(noteTitle, noteDescription);
	}
	
	// Method to display the number of tasks in each status category
	private static void displayTaskStatusCounts() {
	    // Initialize counters for each status
	    int toDoCount = 0;
	    int inProgressCount = 0;
	    int completedCount = 0;

	    
	    // Iterate through the taskMap to count the tasks based on their status
	    if(taskMap != null) {
	    for (Task task : taskMap.values()) {
	        String status = task.getStatus(); // Assuming Task has a getStatus() method

	        // Increment the appropriate counter based on the task's status
	        if ("To Do".equals(status)) {
	            toDoCount++;
	        } else if ("In Progress".equals(status)) {
	            inProgressCount++;
	        } else if ("Completed".equals(status)) {
	            completedCount++;
	        }
	    }
	    }
	    // Display the counts (you can print to the console or display it in your UI)
	    //todoLabelsc.set todoLabelsc, inProgressLabelsc, completedLabelsc;
	    todoLabelsc.setText(String.valueOf(toDoCount));
	    inProgressLabelsc.setText(String.valueOf(inProgressCount));
	    completedLabelsc.setText(String.valueOf(completedCount));
	    System.out.println("To Do: " + toDoCount);
	    System.out.println("In Progress: " + inProgressCount);
	    System.out.println("Completed: " + completedCount);
	}

	
	// Method to display the number of tasks in each status category
	private static void displayTaskSpecificStatusDetails(String taskName, JPanel panel) {

	    Map<String, String> timeStatMap = timeSpentMapofMaps.get(taskName);

		if (timeStatMap != null && timeStatMap.size() != 0) {
			taskStartTimeLabel.setText(timeStatMap.get("startDate"));
			taskEmdTimeLabel.setText(timeStatMap.get("endDate"));
			taskCompletedTimeLabel.setText(timeStatMap.get("timeSpent"));
			if(timeStatMap.get("taskCompletionPercentage") != null)
			progressChart.setPercent(Integer.parseInt(timeStatMap.get("taskCompletionPercentage"))); // New percentage
			
			taskStartTimeLabel.setVisible(true);
			taskEmdTimeLabel.setVisible(true);
			taskCompletedTimeLabel.setVisible(true);
			
			taskStartTimeLabell.setVisible(true);
			taskEmdTimeLabell.setVisible(true);
			taskCompletedTimeLabell.setVisible(true);
			
			taskStartTimeLabels.setVisible(true);
			taskEmdTimeLabels.setVisible(true);
			taskCompletedTimeLabells.setVisible(true);
			progressChart.setVisible(true); // New percentage		

		}else {
			taskStartTimeLabel.setVisible(false);
			taskEmdTimeLabel.setVisible(false);
			taskCompletedTimeLabel.setVisible(false);
			
			taskStartTimeLabell.setVisible(false);
			taskEmdTimeLabell.setVisible(false);
			taskCompletedTimeLabell.setVisible(false);
			
			taskStartTimeLabels.setVisible(false);
			taskEmdTimeLabels.setVisible(false);
			taskCompletedTimeLabells.setVisible(false);
			progressChart.setVisible(false); // New percentage	
		}
		//panel.revalidate();
		//panel.repaint();
	}
	
	// Method to handle smooth scrolling
	private static void scrollSmoothly(final JScrollBar vBar, final int increment) {
	    new Thread(() -> {
	        int current = vBar.getValue();
	        int target = current + increment;

	        // Ensure we don't go below the minimum or beyond the maximum value
	        target = Math.min(Math.max(target, vBar.getMinimum()), vBar.getMaximum());

	        while (current != target) {
	            // Gradually move toward the target value
	            current += increment;
	            current = Math.min(Math.max(current, vBar.getMinimum()), vBar.getMaximum()); // Clamp within bounds

	            final int value = current;
	            SwingUtilities.invokeLater(() -> vBar.setValue(value));

	            try {
	                Thread.sleep(15); // Medium speed (can adjust this to change speed)
	            } catch (InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }).start();
	}

	// Task class to represent each task
	
}
