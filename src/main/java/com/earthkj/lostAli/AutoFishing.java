package com.earthkj.lostAli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.ObserveEvent;
import org.sikuli.script.ObserverCallBack;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class AutoFishing {
	
	public final Region region = Region.create(900, 400, 100, 100);
	public static Pattern searchImage = new Pattern("/src/resource/yellow_black.png").similar((float)0.7).mask();
	List<Region> regions = new ArrayList<Region>();
	Random random = new Random();
	
	public AutoFishing() {
		super();
		
		// 찌 던질 좌표 100개 미리 만들어놓기
    	for(int i=0; i<100; i++) {
    		int offsetX = 1000;
    		int offsetY = 100; // 위로 던질 땐 100, 아래로 던질땐 800 쯤
    		
    		//offset ~ offset+100 사이에서 랜덤 좌표 생성
    		int randomX = offsetX + random.nextInt(100);
    		int randomY = offsetY + random.nextInt(100);
    		
    		this.regions.add(Region.create(randomX, randomY, 1, 1));
    	}
	}
	
    public void fishOnAppear() throws InterruptedException, AWTException {
    	System.out.print("Fishing....");
    	
    	//마우스 이동 + 우클릭
    	moveMouse();
    	//A 눌러서 찌 투척
    	pressKey();
    	
    	//초당 Scan하는 횟수
    	region.setWaitScanRate(20);
    	
    	//발견 시
    	region.onAppear(searchImage, new ObserverCallBack() {
    		@Override
    		public void appeared(ObserveEvent event) {
    			System.out.println("succeed : " + event.getMatch().x + ", " + event.getMatch().y);
    	    	try {
    	    		//찌 회수
    	    		pressKey();
    	    		//회수 모션동안 sleep
					Thread.sleep(6000);
					region.stopObserver();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (AWTException e) {
					e.printStackTrace();
				}
    		}
    	});
    	
    	//실패할 경우를 고려해서 30초 동안 이미지 서칭...
    	region.observe(30);
    }
    
    /*
     * 미리 만들어 놓은 랜덤 좌표 중 한 곳을 추출하여 우클릭
     */
    public void moveMouse() {
    	int randomId = random.nextInt(100);
    	try {
			region.mouseMove(regions.get(randomId));
			region.mouseDown(Button.RIGHT);
			region.mouseUp(Button.RIGHT);
		} catch (FindFailed e) {
			e.printStackTrace();
		}
    }
    
    /*
     * 낚시 키 Press 이벤트 발생
     */
    public void pressKey() throws AWTException {
    	Robot robot = new Robot();
    	robot.keyPress(KeyEvent.VK_A);
    	robot.keyRelease(KeyEvent.VK_A);
    }

}
