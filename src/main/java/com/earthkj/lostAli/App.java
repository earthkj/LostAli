package com.earthkj.lostAli;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Date;

import org.sikuli.script.FindFailed;

/**
 * 메인 함수
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, AWTException, FindFailed
    {
        System.out.println( "=========START=============" );
        
        AutoFishing autoFishing = null;
        
        /**
         * 1 : 낚시 모드
         * 2 : 가공사 - 낚시대 만들기 무한반복 모드
         */
        int mode = 1;
        
        long lastMikkiTime = 0;
        
        // 자동 낚시 무한 반복
        while(true) {
        	autoFishing = new AutoFishing();
        	
        	switch(mode) {
        	case 1:
            	//낚시도구가 하나라도 남아있을 경우에만 낚시를 수행
            	if(!autoFishing.refillFishingRod()) {
            		Thread.sleep(5000);
            		continue;
            	}
            	
            	//미끼뿌리기
//            	long currentTime = (new Date().getTime())/1000;
//            	if(lastMikkiTime == 0 || lastMikkiTime < currentTime - 75) {
//            		lastMikkiTime = currentTime;
//            		autoFishing.mikki();
//        		}

            	//통발
            	autoFishing.tongbal();
            	//찌낚시
           		autoFishing.fishOnAppear();
           		//투망
           		autoFishing.toomang();
        		break;
        	case 2:
        		autoFishing.makeRod();
        		break;
        	default:
        		System.out.println();
        	}

        }
    }

}
