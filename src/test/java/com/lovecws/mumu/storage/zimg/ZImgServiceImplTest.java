package com.lovecws.mumu.storage.zimg;

import com.lovecws.mumu.storage.zimg.service.ZImgService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * Created by Administrator on 2017/3/7.
 */
public class ZImgServiceImplTest {
    public static void main(String[] args){
        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring-storage-zimg.xml");
        applicationContext.start();
        ZImgService zImgService=applicationContext.getBean(ZImgService.class);
        int hashCode=zImgService.hashCode();
        System.out.print(hashCode);

        String url=zImgService.upload(new File("D:\\徐细慧\\xiaomoSite\\images\\4.png"));
        System.out.print(url);
        applicationContext.stop();
    }
}
