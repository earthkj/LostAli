package com.earthkj.lostAli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.ObserveEvent;
import org.sikuli.script.ObserverCallBack;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

public class AutoFishing {
	
	public final Region region = Region.create(900, 400, 100, 100);
	public final Region fullScreen = Region.create(0, 0, 1920, 1080);
	public static String resPath = "/src/resource/";
	public static Pattern searchImage = new Pattern(resPath + "yellow_black.png").similar((float)0.7).mask();
	List<Region> regions = new ArrayList<Region>();
	Random random = new Random();
	
	public AutoFishing() {
		super();
		
		// 찌 던질 좌표 100개 미리 만들어놓기
    	for(int i=0; i<100; i++) {
    		int offsetX = 1000;
    		int offsetY = 800; // 위로 던질 땐 100, 아래로 던질땐 800 쯤
    		
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
    	//TODO 생활 스킬탭이 활성화 되어있는지 확인하는 처리 추가
    	pressKey();
    	
    	//초당 Scan하는 횟수
    	region.setWaitScanRate(10);
    	
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
    
    /**
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
    
    /**
     * 낚시 키 Press 이벤트 발생
     */
    public void pressKey() throws AWTException {
    	Robot robot = new Robot();
    	robot.keyPress(KeyEvent.VK_A);
    	robot.keyRelease(KeyEvent.VK_A);
    }

    /**
     * 낚시대 리필
     * @throws InterruptedException 
     */
    public boolean refillFishingRod() throws InterruptedException {
    	boolean result = true;
    	// 
    	if(isEquipmentWindowExist()) {
    		// 장비창이 있는데, 낚시대가 비어있을 경우
    		if(isRodEmpty()) {
    			Pattern spare = new Pattern(resPath + "rod_spare.jpg").similar((float)0.9);
        		try {
        			// 999짜리 여분 찾아서 장착(우클릭)
    				Match match = fullScreen.find(spare);
    				match.rightClick();
    				match.mouseMove(regions.get(0));
    				System.out.println("낚시대 리필 완료");
    				Thread.sleep(1000);
    			} catch (FindFailed e) {
    				System.out.println("여분의 낚시대가 없습니다");
    				//여분이 없다면 더 이상 낚시를 시도하지 않아야 한다.
    				result = false;
    			}
    		}else {
    			// 낚시대가 있다면 아무것도 할 필요는 없다....
    		}
    	}else {
    		System.out.println("장비창(P)이 활성화 되어있지 않습니다.");
			result = false;
    	}
    	return result;
    }
    
    /**
     * 장비창이 열려있는지 확인 
     */
    public boolean isEquipmentWindowExist() {
    	Pattern rodExist = new Pattern(resPath + "rod_exist.png").similar((float)0.95).mask();
    	Pattern rodEquipment = new Pattern(resPath + "rod_equipment.jpg").similar((float)0.95);
    	
    	boolean isExist = false;
    	try {
			Match match = fullScreen.find(rodEquipment);
			isExist = true;
		} catch (FindFailed e) {
			isExist = false;
		}
    	return isExist;
    }
    
    /**
     * 장비창에 낚시대가 비어있는지 확인
     * @return
     */
    public boolean isRodEmpty() {
    	Pattern rodEmpty = new Pattern(resPath + "rod_empty.jpg").similar((float)0.9);
    	boolean isEmpty = false;
    	try {
    		//낚시대 장비칸이 비어있는게 발견되면 => true
			Match match = fullScreen.find(rodEmpty);
			isEmpty = true;
		} catch (FindFailed e) {
			isEmpty = false;
		}
    	return isEmpty;
    }
    
    public void makeRod() throws FindFailed, InterruptedException, AWTException {
    	Pattern make_plus = new Pattern(resPath + "make_plus.png").similar((float)0.95).mask();
    	Pattern make_create = new Pattern(resPath + "make_create.png").similar((float)0.95).mask();
    	Pattern make_receive = new Pattern(resPath + "make_receive.png").similar((float)0.9).mask();
    	
    	//수량 10개로 증가
    	Match btnPlus = fullScreen.find(make_plus);
    	for(int i=0; i<9; i++) {
    		btnPlus.click();
    	}
    	
    	//의뢰하기
    	Match btnCreate = fullScreen.find(make_create);
    	btnCreate.click();
    	Thread.sleep(500);
    	
    	//확인 (enter)
    	Robot robot = new Robot();
    	robot.keyPress(KeyEvent.VK_ENTER);
    	robot.keyRelease(KeyEvent.VK_ENTER);
    	
    	//20초 기다리기
    	Thread.sleep(20000);
    	
    	//받기
    	fullScreen.onAppear(make_receive, new ObserverCallBack() {
    		@Override
    		public void appeared(ObserveEvent event) {
	    		event.getMatch().click();
	    		fullScreen.stopObserver();
    	    		
    		}
    	});
    	fullScreen.observe(5);
    	
    	Thread.sleep(1000);
    }

}
