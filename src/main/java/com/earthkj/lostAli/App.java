package com.earthkj.lostAli;

import java.awt.AWTException;
import java.io.IOException;

/**
 * 메인 함수
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, AWTException
    {
        System.out.println( "=========START=============" );
        
        AutoFishing autoFishing = null;

        // 자동 낚시 무한 반복
        while(true) {
        	autoFishing = new AutoFishing();
        	//낚시도구가 하나라도 남아있을 경우에만 낚시를 수행
        	if(!autoFishing.refillFishingRod()) {
        		Thread.sleep(5000);
        		continue;
        	}
       		autoFishing.fishOnAppear();
        }
    }

}
