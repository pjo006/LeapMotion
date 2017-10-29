/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leapmotion;

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;
import com.leapmotion.leap.Gesture.Type;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


class CustomListener extends Listener {
    
    public Robot robot;
    
    public void onConnect(Controller c) {
        c.enableGesture(Gesture.Type.TYPE_CIRCLE);
        c.enableGesture(Gesture.Type.TYPE_SWIPE);
        c.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        c.config().setFloat("Gesture.Swipe.MinVelocity", 10.0f);
        c.config().setFloat("Gesture.Swipe.MinLength", 150.0f);
        c.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
        c.config().save();
    }
    
    public void onFrame(Controller c) {
        try { robot = new Robot(); } catch (Exception e) {}
        Frame frame = c.frame();
        for (Gesture g:frame.gestures()) {
            if (g.type() == Type.TYPE_SWIPE && g.state() == State.STATE_START) {
            		SwipeGesture swipe = new SwipeGesture(g);
            		Vector swipeDirection = swipe.direction();
            		//System.out.format("%f %f\n", swipeDirection.getX(),swipeDirection.getY());
            		if (Math.abs(swipeDirection.getY()) > Math.abs(swipeDirection.getX())) {
            			if(swipeDirection.getY()<-0.2) {
                			robot.keyPress(KeyEvent.VK_DOWN);
                			try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                			robot.keyRelease(KeyEvent.VK_DOWN);
                			
                		} else if(swipeDirection.getY()>0.2) {
                			robot.keyPress(KeyEvent.VK_UP);
                			try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                			robot.keyRelease(KeyEvent.VK_UP);
                		}
            		} else {
	            		if(swipeDirection.getX()<-0.2) {
	            			robot.keyPress(KeyEvent.VK_LEFT);
	            			try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            			robot.keyRelease(KeyEvent.VK_LEFT);
	            		} else if(swipeDirection.getX()>0.2) {
	            			robot.keyPress(KeyEvent.VK_RIGHT);
	            			try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	            			robot.keyRelease(KeyEvent.VK_RIGHT);
	            		}
            		}
            		try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
}
/**
 *
 * @author as062
 */
public class LeapMotion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CustomListener l = new CustomListener();
        Controller c = new Controller();
        c.addListener(l);
        
        try {
                System.in.read(); }
        catch (Exception e) {
            
        }
        
        c.removeListener(l);
    }
    
}