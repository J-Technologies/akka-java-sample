package com.vdzon.remotecar.visuals;

import com.vdzon.remotecar.data.ArcSegment;
import com.vdzon.remotecar.data.RouteSegment;
import com.vdzon.remotecar.data.StraightSegment;
import com.vdzon.remotecar.messages.Img;
import com.vdzon.remotecar.messages.RoutePlan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VisualForm extends JDialog {
    private JPanel contentPane;
    private RoutePlan routePlan = null;

    public VisualForm() {
        setContentPane(contentPane);
        setSize(new Dimension(700, 700));
        setMinimumSize(new Dimension(700, 700));
        setPreferredSize(new Dimension(700, 700));
        setModal(false);
    }

    public void paintWebcamImage(Img img) {
        Graphics2D g2d = (Graphics2D) contentPane.getGraphics();
        g2d.drawImage(img.getImage(), 0, 0, null);
        drawSegments(g2d);
    }

    public void updateRoutePlan(RoutePlan routePlan) {
        this.routePlan = routePlan;
    }

    private void drawSegments(Graphics2D g2d) {
        if (routePlan == null) return;
        g2d.setColor(Color.DARK_GRAY);
        for (RouteSegment segment : routePlan.getRoute()) {
            if (segment instanceof ArcSegment) {
                ArcSegment arcSegment = (ArcSegment) segment;
                int left = (int) arcSegment.getCenterPoint().getX() - (int) arcSegment.getRadius();
                int top = (int) arcSegment.getCenterPoint().getY() - (int) arcSegment.getRadius();
                g2d.drawOval(left, top, (int) arcSegment.getRadius() * 2, (int) arcSegment.getRadius() * 2);
            }
            if (segment instanceof StraightSegment) {
                StraightSegment straightSegment = (StraightSegment) segment;
                g2d.drawLine((int) straightSegment.getStartPoint().getX(), (int) straightSegment.getStartPoint().getY(), (int) straightSegment.getEndPoint().getX(), (int) straightSegment.getEndPoint().getY());
            }
        }
    }

}
