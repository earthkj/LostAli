package com.earthkj.lostAli;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
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
    	if(isFishingRodExist()) {
    		//do nothing...
    	}else {
    		// 장착된 낚시대가 안보인다? 두가지 케이스가 있음.
    		// 다 떨어졌거나, 캐릭터 정보를 안 띄워놨거나.
    		// 일단 999짜리 여분 장착해보고, 그래도 못 찾으면 캐릭 정보창 꺼져있다고 판단하여 false 반환.
    		
    		Pattern spare = new Pattern(resPath + "rod_spare.jpg").similar((float)0.9);
    		try {
    			// 999짜리 여분 찾아서 장착(우클릭)
				Match match = fullScreen.find(spare);
				match.rightClick();
				match.mouseMove(regions.get(0));
				System.out.println("Refill Succeed");
				Thread.sleep(1000);
				// 다시 한 번 서치
				if(!isFishingRodExist()) {
					// 리필 했는데도 안 보인다는건 캐릭터 정보창이 꺼져있다는 의미이므로 더이상 하지 않는다.
					System.out.println("Can not find Character Information Window");
					result = false;
				}
			} catch (FindFailed e) {
				System.out.println("Refill Failed : Spare not found..");
				//장착중인 낚시대도 안 보이고 + 여분조차 안보인다면 => 더 이상 낚시를 시도하지 않아야 한다.
				result = false;
			}
    	}
    	return result;
    }
    
    /**
     * 장착중인 낚시대가 다 떨어졌는지 체크 
     */
    public boolean isFishingRodExist() {
    	Pattern rodExist = new Pattern(resPath + "rod_exist.png").similar((float)0.95).mask();
    	boolean isExist = false;
    	try {
			Match match = fullScreen.find(rodExist);
			isExist = true;
		} catch (FindFailed e) {
			isExist = false;
		}
    	return isExist;
    }

}
