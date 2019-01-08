package com.earthkj.lostAli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class AutoFishing {
	
    public void mikki() throws InterruptedException, AWTException {
    	//덫 설치
    	System.out.println("설치");
    	pressKey(KeyEvent.VK_D);
    	//액션 대기
    	Thread.sleep(10*1000);
    	//5분 대기
    	Thread.sleep(300*1000);
    	//덫 회수
    	System.out.println("회수");
    	pressKey(KeyEvent.VK_G);
    	Thread.sleep(10000);
    }
    
    /**
     * 낚시 키 Press 이벤트 발생
     */
    public void pressKey(int keyEvent) throws AWTException {
    	Robot robot = new Robot();
    	robot.keyPress(keyEvent);
    	robot.keyRelease(keyEvent);
    }

}
