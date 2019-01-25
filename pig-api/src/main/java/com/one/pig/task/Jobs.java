package com.one.pig.task;

import org.springframework.stereotype.Component;


@Component
public class Jobs {
    public final static long ONE_Minute = 60 * 1000;

    // @Scheduled(cron = "0/1 * * * * ?")
    // public void cronJob() {
    //     System.out.println("aaaaa");
    // }
}
