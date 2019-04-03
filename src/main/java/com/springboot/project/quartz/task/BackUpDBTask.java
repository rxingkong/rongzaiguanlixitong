package com.springboot.project.quartz.task;

import com.springboot.project.utils.InitDatabaseUtils;
import com.springboot.project.utils.Lg;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Component(value = "backUpDB")
public class BackUpDBTask {
//    public void backUp(String msg){
//        Lg.log("备份数据库"+msg);
//
//    }
//    public void backUp(){
//        Lg.log("备份数据库");
//        try {
//            Properties properties = new Properties();
//            InputStream is=BackUpDBTask.class.getClassLoader().getResourceAsStream( "backdb.properties");
//            InputStreamReader isr = new InputStreamReader(is,"utf-8");
//            properties.load(isr);
//            String command =  InitDatabaseUtils.getExportCommand(properties);
//            String fileName=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//            command=command+fileName+".sql";
//            Runtime runtime=Runtime.getRuntime();
//            runtime.exec(command);
//            Lg.log("备份数据库成功",properties.getProperty("jdbc.exportPath"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public  void backUp(String msg){
        Lg.log("备份数据库！"+ msg);
    }

    public  void backUp(){
        Lg.log("备份数据库！");
        try{
            Properties properties = new Properties();
            InputStream is = BackUpDBTask.class.getClassLoader().getResourceAsStream("backdb.properties");

            InputStreamReader isr = new InputStreamReader(is,"utf-8");

            properties.load(isr);
            String command =  InitDatabaseUtils.getExportCommand(properties);
            //mysqldump -uroot -padmin -h127.0.01 -P3306 rzdb -r /Users/xxxx/
            String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

            command = command+fileName+".sql";

            Runtime runtime = Runtime.getRuntime();

            runtime.exec(command);
            Lg.log("备份数据库成功",properties.getProperty("jdbc.exportPath"));

        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
