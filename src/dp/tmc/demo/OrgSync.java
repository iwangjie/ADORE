package dp.tmc.demo;

import dp.tmc.org.*;
import weaver.conn.RecordSetDataSource;
import weaver.general.Util;

/**
 * Created by adore on 2016/11/29.
 * ������֯�ṹͬ��
 */
public class OrgSync {
    public static void main(String[] args) {
        // ִ����
        HrmOrgAction hoa = new HrmOrgAction();
        RecordSetDataSource rsds = new RecordSetDataSource("HR");
        String sql = "";
        sql = " select companycode,companyname,companydesc,supercode,subcompanyname,showorder,createtime from DP_company ";
        rsds.executeSql(sql);
        while (rsds.next()) {
            String companycode = Util.null2String(rsds.getString("companycode"));
            String companyname = Util.null2String(rsds.getString("companyname"));
            String companydesc = Util.null2String(rsds.getString("companydesc"));
            String supercode = Util.null2String(rsds.getString("supercode"));
            //String subcompanyname = Util.null2String(rsds.getString("subcompanyname"));
            int showorder = rsds.getInt("showorder");
            String createtime = Util.null2String(rsds.getString("createtime"));
            /**
             �����ֲ�ͬ��
             */
            // �ֲ���
            HrmSubCompanyBean hsb = new HrmSubCompanyBean();
            hsb.setSubCompanyCode(companycode);
            hsb.setSubCompanyName(companyname);
            hsb.setSubCompanyDesc(companydesc);
            // �ϼ��Ĳ�����ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hsb.setIdOrCode(1);
            hsb.setSuperID("");
            hsb.setSuperCode(supercode);
            //�����ֶ�
            hsb.setOrderBy(showorder);
            // ״̬    0����  1��װ
            hsb.setStatus(0);
            // �Զ�������    ����:tt1��tt2���Զ�����ֶ���     TEst1��TEst2 ���Զ����ֶε�ֵ       HrmSubcompanyDefined��¼
            //hsb.addCusMap("tt1", "TEst1");
            //hsb.addCusMap("tt2", "TEst2");
            // ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��
            ReturnInfo result = hoa.operSubCompany(hsb);
            //
            if (result.isTure()) {
                System.out.println("ִ�гɹ����������ݣ�" + result.getRemark());
            } else {
                System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��" + result.getRemark());
            }
        }

        sql = " select departmentcode,departmentname,departmentmark,supdepcode,companycode,showorder,createtime from DP_Department ";
        rsds.executeSql(sql);
        while (rsds.next()) {
            String departmentcode = Util.null2String(rsds.getString("departmentcode"));
            String departmentname = Util.null2String(rsds.getString("departmentname"));
            String departmentmark = Util.null2String(rsds.getString("departmentmark"));
            String supdepcode = Util.null2String(rsds.getString("supdepcode"));
            String companycode = Util.null2String(rsds.getString("companycode"));
            int showorder = rsds.getInt("showorder");
            String createtime = Util.null2String(rsds.getString("createtime"));
            /**
             ��������ͬ��
             */
            // ������
            HrmDepartmentBean hdb = new HrmDepartmentBean();
            hdb.setDepartmentcode(departmentcode);
            hdb.setDepartmentname(departmentname);
            hdb.setDepartmentark(departmentmark);
            // �ֲ��Ļ�ȡ������ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hdb.setComIdOrCode(1);
            hdb.setSubcompanyid1("");
            hdb.setSubcompanyCode(companycode);
            // �ϼ��Ĳ�����ʽ     0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hdb.setIdOrCode(1);
            hdb.setSuperID("");
            hdb.setSuperCode(supdepcode);
            //�����ֶ�
            hdb.setOrderBy(showorder);
            // ״̬    0����  1��װ
            hdb.setStatus(0);
            // �Զ�������    ����:t1��t2���Զ�����ֶ���     123��456 ���Զ����ֶε�ֵ       HrmDepartmentDefined��¼
            //hdb.addCusMap("t1", "123");
            //hdb.addCusMap("t2", "456");
            // ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��
            ReturnInfo result = hoa.operDept(hdb);
            if (result.isTure()) {
                System.out.println("ִ�гɹ����������ݣ�" + result.getRemark());
            } else {
                System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��" + result.getRemark());
            }
        }
        sql = " select jobtitlecode,jobtitlename,jobtitlemark,jobActivities,createtime from DP_JobTitles2 ";
        rsds.executeSql(sql);
        while (rsds.next()) {
            String jobtitlecode = Util.null2String(rsds.getString("jobtitlecode"));
            String jobtitlename = Util.null2String(rsds.getString("jobtitlename"));
            String jobtitlemark = Util.null2String(rsds.getString("jobtitlemark"));
            String jobActivities = Util.null2String(rsds.getString("jobActivities"));
            String createtime = Util.null2String(rsds.getString("createtime"));
            /**
             ������λͬ��
             */
            // ��λ��
            HrmJobTitleBean hjt = new HrmJobTitleBean();
            hjt.setJobtitlecode(jobtitlecode);
            hjt.setJobtitlename(jobtitlename);
            hjt.setJobtitlemark(jobtitlemark);
            hjt.setJobtitleremark(jobtitlemark);
            // ��������  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hjt.setDeptIdOrCode(1);
            hjt.setJobdepartmentid("");
            hjt.setJobdepartmentCode("");
            hjt.setSuperJobCode("");
            // ְλ ֱ��ͨ���ֶ�ȥ��ѯ��û�о���ӣ��о�ֱ�ӻ�ȡ
            hjt.setJobactivityName("������Ա");
            // ְλ ֱ��ͨ���ֶ�ȥ��ѯ��û�о���ӣ��о�ֱ�ӻ�ȡ
            hjt.setJobGroupName("����������");
            // ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��
            ReturnInfo result = hoa.operJobtitle(hjt);
            if (result.isTure()) {
                System.out.println("ִ�гɹ����������ݣ�" + result.getRemark());
            } else {
                System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��" + result.getRemark());
            }
        }

        sql = " select workcode,name,loginid,mangercode,mobile,seclevel,telephone,email,certificatenum,sex,departmentcode,jobtitlecode"
            +" ,birthday,maritalstatus,nativeplace,educationlevel,status,extphone,residentpostcode,tempresidentnumber,fax,folk,location,workroom"
            +" ,createtime from DP_HrmResource ";
        rsds.executeSql(sql);
        while (rsds.next()) {
            String workcode = Util.null2String(rsds.getString("workcode"));
            String name = Util.null2String(rsds.getString("name"));
            String loginid = Util.null2String(rsds.getString("loginid"));
            String mobile = Util.null2String(rsds.getString("mobile"));
            int seclevel = rsds.getInt("seclevel");
            String mangercode = Util.null2String(rsds.getString("mangercode"));
            String telephone = Util.null2String(rsds.getString("telephone"));
            String email = Util.null2String(rsds.getString("email"));
            String certificatenum = Util.null2String(rsds.getString("certificatenum"));
            String sex = Util.null2String(rsds.getString("sex"));
            String departmentcode = Util.null2String(rsds.getString("departmentcode"));
            String jobtitlecode = Util.null2String(rsds.getString("jobtitlecode"));
            String birthday = Util.null2String(rsds.getString("birthday"));
            String maritalstatus = Util.null2String(rsds.getString("maritalstatus"));
            String nativeplace = Util.null2String(rsds.getString("nativeplace"));
            String educationlevel = Util.null2String(rsds.getString("educationlevel"));
            String status = Util.null2String(rsds.getString("status"));
            String extphone = Util.null2String(rsds.getString("extphone"));
            String residentpostcode = Util.null2String(rsds.getString("residentpostcode"));
            String tempresidentnumber = Util.null2String(rsds.getString("tempresidentnumber"));
            String fax = Util.null2String(rsds.getString("fax"));
            String folk = Util.null2String(rsds.getString("folk"));
            String location = Util.null2String(rsds.getString("location"));
            String workroom = Util.null2String(rsds.getString("workroom"));
            String createtime = Util.null2String(rsds.getString("createtime"));
            /**
             ������Աͬ��
             */
            // ��Ա��Ϣ��
            HrmResourceBean hrb = new HrmResourceBean();
            hrb.setWorkcode(workcode);
            hrb.setLoginid(loginid);
            hrb.setLastname(name);
            hrb.setPassword("1234");
            // �����ֲ�   ��������Ӧ�ķֲ�   ʡ��
            // ��������  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hrb.setDeptIdOrCode(1);
            hrb.setDepartmentid("");
            hrb.setDepartmentCode(departmentcode);
            // ������λ  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ
            hrb.setJobIdOrCode(1);
            hrb.setJobtitle("");
            hrb.setJobtitleCode(jobtitlecode);
            // �ϼ��쵼  0 ��ͨ��id��ȡ  1��ͨ��code��ȡ      2��ͨ����λ��ȡ
            hrb.setManagerIdOrCode(0);
            hrb.setManagerid("");
            hrb.setManagerCode(mangercode);
            hrb.setSeclevel(seclevel);
            hrb.setBirthday(birthday);
            hrb.setCertificatenum(certificatenum);
            hrb.setMobile(mobile);
            hrb.setEducationlevel(educationlevel);
            hrb.setTelephone(telephone);
            hrb.setEmail(email);
            hrb.setSex(sex);
            hrb.setMaritalstatus(maritalstatus);
            hrb.setNationality(nativeplace);
            hrb.setStatus(status);
            hrb.setExtphone(extphone);
            hrb.setRegresidentplace(residentpostcode);
            hrb.setTempresidentnumber(tempresidentnumber);
            hrb.setFax(fax);
            hrb.setFolk(folk);
            hrb.setLocationid(location);
            hrb.setWorkroom(workroom);
            hrb.addNotUpdate("seclevel");
            hrb.addNotUpdate("managerid");
            hrb.addNotUpdate("password");
            hrb.getNotUpdate();

            /**
             *  ��Ȼ�����»��� ����������  ��HrmResourceBean�У�����setֵ
             */

            // ִ�н��  ����ֱ�Ӵ�ӡresult �鿴ֱ�ӽ��
            ReturnInfo result = hoa.operResource(hrb);
            if (result.isTure()) {
                System.out.println("ִ�гɹ����������ݣ�" + result.getRemark());
            } else {
                System.out.println("ִ��ʧ�ܣ�ʧ����ϸ��" + result.getRemark());
            }
        }
    }
}
