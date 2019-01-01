package com.earthkj.lostAli;

import java.awt.AWTException;
import java.io.IOException;

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
        int mode = 2;

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
           		autoFishing.fishOnAppear();
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
