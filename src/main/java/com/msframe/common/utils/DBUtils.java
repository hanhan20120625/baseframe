package com.msframe.common.utils;

import org.apache.shiro.session.Session;

public class DBUtils {
	private static SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
	
	public static  void init(long datacenterId,long workerId){
		idWorker.setDatacenterId(datacenterId);
		idWorker.setWorkerId(workerId);
	}
	
	public static long generateBigIntId(Session session) {
		return idWorker.nextId();
	}
	
	public static String generateUUID(Session session) {
		return IdGen.uuid();
	}
	public static String generateUUID() {
		return IdGen.uuid();
	}
}
