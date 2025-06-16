package com.task.tracker.app;

import java.io.Serializable;

public class Task implements Serializable {
	String worker;
	String priority;
	String project;

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	String title;
	String description;
	String dueDate;
	String status;

	public String getStatus() {
		return status;
	}

	Task(String worker, String title, String description, String dueDate, String status, String priority,
			String project) {
		this.worker = worker;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.status = status;
		this.priority = priority;
		this.project = project;
	}

	public String toString() {
		return title + " - " + status + " - Due: " + dueDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}
