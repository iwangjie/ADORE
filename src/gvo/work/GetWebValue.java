package gvo.work;

import java.io.IOException;
import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import gvo.WebServiceStub;
import gvo.WebServiceStub.ArrayOfLeaveDaysBalance;
import gvo.WebServiceStub.CalculateLeaveValue;
import gvo.WebServiceStub.CalculateOTValue;
import gvo.WebServiceStub.CalculateValue;
import gvo.WebServiceStub.ClassOTType;
import gvo.WebServiceStub.GetClassAndOTType;
import gvo.WebServiceStub.GetLeaveDaysBalance;
import gvo.WebServiceStub.LeaveDaysBalance;
import gvo.log.GvoLog;

public class GetWebValue {

	GvoLog log = new GvoLog();
	//获取休假值
	public double getValue(String employeeCode,String startDate,String endDate,String startTime,String endTime,String leaveTypeCode) throws IOException{
		double c_value = 0;
		
		WebServiceStub wss = new WebServiceStub();
		
		CalculateLeaveValue calculateLeaveValue = new CalculateLeaveValue();
		calculateLeaveValue.setEmployeeCode(employeeCode);
		calculateLeaveValue.setStartDate(startDate);
		calculateLeaveValue.setEndDate(endDate);
		calculateLeaveValue.setStartTime(startTime);
		calculateLeaveValue.setEndTime(endTime);
		calculateLeaveValue.setLeaveTypeCode(leaveTypeCode);
		
		CalculateValue cv = wss.CalculateLeaveValue(calculateLeaveValue).getCalculateLeaveValueResult();
		c_value = cv.getValue();
		String message = cv.getMessage();
		System.out.println("message = " + message);
		
		log.log(employeeCode, "H", "获取休假值(CalculateLeaveValue)", "", "Y", message);
		
		return c_value;
	}
	//获取休假值 用于Action
	public double getValueAction(String employeeCode,String startDate,String endDate,String startTime,String endTime,String leaveTypeCode){
		double c_value = 0;
		
		try {
			WebServiceStub wss = new WebServiceStub();
			
			CalculateLeaveValue calculateLeaveValue = new CalculateLeaveValue();
			calculateLeaveValue.setEmployeeCode(employeeCode);
			calculateLeaveValue.setStartDate(startDate);
			calculateLeaveValue.setEndDate(endDate);
			calculateLeaveValue.setStartTime(startTime);
			calculateLeaveValue.setEndTime(endTime);
			calculateLeaveValue.setLeaveTypeCode(leaveTypeCode);
			
			CalculateValue cv = wss.CalculateLeaveValue(calculateLeaveValue).getCalculateLeaveValueResult();
			c_value = cv.getValue();
			String message = cv.getMessage();
			System.out.println("message = " + message);
			
			log.log(employeeCode, "H", "获取休假值(CalculateLeaveValue)", "Action", "Y", message);
		} catch (RemoteException e) {
			log.log(employeeCode, "H", "获取休假值(CalculateLeaveValue)Action", e.getMessage(), "N", "");
		}
		
		return c_value;
	}
	
	//计算加班值
	public double getOTValue(String employeeCode,String shiftDate,String startDate,String endDate,String startTime,String endTime) throws IOException{
		double c_value = 0;
		
		WebServiceStub wss = new WebServiceStub();
		
		CalculateOTValue calculateOTValue = new CalculateOTValue();
		
		calculateOTValue.setEmployeeCode(employeeCode);
		calculateOTValue.setStartDate(startDate);
		calculateOTValue.setEndDate(endDate);
		calculateOTValue.setStartTime(startTime);
		calculateOTValue.setEndTime(endTime);
		calculateOTValue.setShiftDate(shiftDate);
		
		CalculateValue cv = wss.CalculateOTValue(calculateOTValue).getCalculateOTValueResult();
		c_value = cv.getValue();
		String message = cv.getMessage();
		System.out.println("message = " + message);
		log.log(employeeCode, "H", "计算加班值(CalculateOTValue)", "", "Y", message);
		return c_value;
	}
	
	//获取可用休假信息
	public StringBuffer getInfo(String employeeCode) throws IOException{
		
		WebServiceStub wss = new WebServiceStub();
		
		StringBuffer buffer = new StringBuffer();
		
		GetLeaveDaysBalance getLeaveDaysBalance = new GetLeaveDaysBalance();
		getLeaveDaysBalance.setEmployeeCode(employeeCode);
		ArrayOfLeaveDaysBalance aob = wss.GetLeaveDaysBalance(getLeaveDaysBalance).getGetLeaveDaysBalanceResult();
		LeaveDaysBalance[] ldbs =  aob.getLeaveDaysBalance();
		buffer.append("{");
		for(int i = 0;i<ldbs.length;i++){
			LeaveDaysBalance ldb = ldbs[i];
			if(i !=0) buffer.append(",");
			String code = ldb.getLeaveTypeCode();
			int m = 1;
			if("L_002".equals(code)){
				m = 2;
			}
			
			buffer.append("typeCode");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(code);buffer.append("'");
			buffer.append(",");
			buffer.append("typeName");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getLeaveTypeName());buffer.append("'");
			buffer.append(",");
			buffer.append("totalValue");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getTotalValue());buffer.append("'");
			buffer.append(",");
			buffer.append("forwardVaule");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getCarryForwardValue());buffer.append("'");
			buffer.append(",");
			buffer.append("userValue");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getUsedValue());buffer.append("'");
			buffer.append(",");
			buffer.append("expiedValue");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getExpiedValue());buffer.append("'");
			buffer.append(",");
			buffer.append("labelVale");buffer.append(m);buffer.append(":");buffer.append("'");buffer.append(ldb.getAvailableValue());buffer.append("'");
//			buffer.append(",");
		}
		buffer.append("}");
		return buffer;
	}
	
	//获取班次和加班类型
		public StringBuffer getOTType(String employeeCode,String shiftDate) throws IOException{
			
			WebServiceStub wss = new WebServiceStub();
			
			StringBuffer buffer = new StringBuffer();
			
			GetClassAndOTType getClassAndOTType = new GetClassAndOTType();
			getClassAndOTType.setEmployeeCode(employeeCode);
			getClassAndOTType.setShiftDate(shiftDate);
			
			ClassOTType cot = wss.GetClassAndOTType(getClassAndOTType).getGetClassAndOTTypeResult();
			buffer.append("{");
				buffer.append("ClassName");buffer.append(":");buffer.append("'");buffer.append(cot.getClassName());buffer.append("'");
				buffer.append(",");
				buffer.append("ClassCode");buffer.append(":");buffer.append("'");buffer.append(cot.getClassCode());buffer.append("'");
				buffer.append(",");
				buffer.append("OTTypeName");buffer.append(":");buffer.append("'");buffer.append(cot.getOTTypeName());buffer.append("'");
				buffer.append(",");
				buffer.append("OTTypeCode");buffer.append(":");buffer.append("'");buffer.append(cot.getOTTypeCode());buffer.append("'");
			buffer.append("}");
			return buffer;
		}
	
	public static void main(String[] args) {
		
		double v_1 = 0;
		GetWebValue gw = new GetWebValue();
		try {
			
			
			
			v_1 = gw.getValue("B0699", "2015/02/14", "2015/02/25", "08:20", "17:20", "L_001");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(v_1);
	}
}
