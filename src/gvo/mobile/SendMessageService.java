package gvo.mobile;

import weaver.sms.SmsService;

public class SendMessageService implements SmsService{

	public boolean sendSMS(String smsId, String number, String msg) {
		
		String url = "http://3tong.net/http/sms/Submit";
		String xml = "";
		String account = "dh21000";
        String password = "dh21000.com";
        String msgid = "";
        String phones = number;
        String content = msg;
        String subcode = "";
        String sendtime = "";
        String sign = "";
		
        SendResult sr = new SendResult();
        sr.DocXml(account, password, msgid, phones, content, subcode, sendtime, sign);
        
        String desc = sr.post(url, xml);
		return desc.contains("提交成功");
	}

}
