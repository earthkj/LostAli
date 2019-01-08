package com.earthkj.lostAli;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Date;

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
        
        /**
         * 1 : 낚시 모드
         * 2 : 가공사 - 낚시대 만들기 무한반복 모드
         */
        int mode = 1;
        
        Thread.sleep(5000);
        // 자동 낚시 무한 반복
        while(true) {
        	autoFishing = new AutoFishing();
        	switch(mode) {
        	case 1:
            	//미끼뿌리기
        		Thread.sleep(3000);
           		autoFishing.mikki();
        		break;
        	case 2:
//        		autoFishing.makeRod();
        		break;
        	default:
        		System.out.println();
        	}

        }
    }

}
