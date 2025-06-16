package com.task.tracker.app;

import javax.swing.*;
import java.awt.*;

public class CircleProgressChart extends JPanel {

    private int percent = 75; // Set your percentage here

    public CircleProgressChart(int percent) {
        this.percent = percent;
        setPreferredSize(new Dimension(150, 150));
        setBackground(Color.WHITE);
      //  setOpaque(false);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Convert to Graphics2D for better rendering
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int size = Math.min(getWidth(), getHeight()) - 20;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Draw background circle (gray or white)
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillOval(x, y, size, size);

        // Draw progress arc (green)
        g2d.setColor(Color.GREEN);
        int angle = (int) (360 * percent / 100.0);
        g2d.fillArc(x, y, size, size, 90, -angle);

        // Optional: Draw inner white circle for "donut" effect
        int thickness = size / 4;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x + thickness / 2, y + thickness / 2, size - thickness, size - thickness);

        // Optional: Draw percentage text
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String text = percent + "%";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2d.drawString(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 4);
    }
    public void setPercent(int percent) {
        this.percent = percent;
        repaint(); // Triggers a redraw with the new value
    }


}
