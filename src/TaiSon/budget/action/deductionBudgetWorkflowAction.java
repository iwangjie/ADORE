package TaiSon.budget.action;

import TaiSon.FinanceUsq;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/7.
 */
public class deductionBudgetWorkflowAction implements Action {
    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("����Ԥ��ۼ�deductionBudgetWorkflowAction������������");

        String requestid = info.getRequestid();

        if (!" ".equals(requestid)) {

            FinanceUsq fu = new FinanceUsq();
            Boolean result = fu.deduction(requestid);
            log.writeLog("result---------=" + result);
            if (!result) {
                log.writeLog("Ԥ��ۼ�ʧ��!--------" + result);
                return "-1";
            }
        } else {
            new BaseBean().writeLog("��������Ϣ��ȡ����!");
            return "-1";
        }

        return SUCCESS;

    }
}
