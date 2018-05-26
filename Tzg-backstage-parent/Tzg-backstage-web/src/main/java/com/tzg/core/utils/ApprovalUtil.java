package com.tzg.core.utils;

public class ApprovalUtil {
	
	/**
	 * 上一个流程
	 * @param currentFlow
	 * @param approvalFlow
	 * @return
	 */
	public static String getLastFlow(String currentFlow,String approvalFlow){
		if(approvalFlow == null){
			return null;
		}
		String[] flows = approvalFlow.split(";");
		//如果为空获取最后一个
		if(currentFlow == null){
			return flows[flows.length-1];
		}
		for(int i=0;i<flows.length;i++){
			if(currentFlow.equals(flows[i])){
				if(i!=0){
					return flows[i-1];
				}else{ //已经是第一个时返回第一个
					return flows[i];
				}
			}
		}
		return null;
	}
	
	/**
	 * 倒数第二个流程
	 * @param currentFlow
	 * @param approvalFlow
	 * @return
	 */
	public static String getTheLastButOne(String approvalFlow){
		if(approvalFlow == null){
			return null;
		}
		String[] flows = approvalFlow.split(";");
		if(flows.length>0){
			if(flows.length<2){
				return  flows[0];
			}else{
				return  flows[flows.length-2];
			}
		}else{
			return null;
		}
		
	}
	
	/**
	 * 下一个流程
	 * @param currentFlow
	 * @param approvalFlow
	 * @return
	 */
	public static String getNextFlow(String currentFlow,String approvalFlow){
		if(approvalFlow == null){
			return null;
		}
		String[] flows = approvalFlow.split(";");
		//如果为空获取第一个
		if(currentFlow == null){
			return flows[0];
		}
		for(int i=0;i<flows.length;i++){
			if(currentFlow.equals(flows[i])){
				if((i+1)!=flows.length){
					return flows[i+1];
				}else{//已经是最后一个时返回最后一个
					return flows[i];
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(getLastFlow("3","2;3;4"));
		System.out.println(getTheLastButOne("3"));
	}
}
