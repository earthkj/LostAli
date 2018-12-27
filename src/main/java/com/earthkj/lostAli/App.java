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
        	autoFishing.fishOnAppear();
        }
    }

}
