package com.gaorui.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;



import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class quartz extends QuartzJobBean {

	
      
	    public void executeAction(){  
	    	 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		       System.out.println("定时任务"+myFmt.format(new Date().getTime()));
	    }

		@Override
		protected void executeInternal(JobExecutionContext arg0)
				throws JobExecutionException {
		
			
		}  
	  
	   

}
