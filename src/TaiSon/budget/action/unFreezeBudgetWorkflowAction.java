package TaiSon.budget.action;

import TaiSon.FinanceUsq;
import weaver.general.BaseBean;
import weaver.interfaces.workflow.action.Action;
import weaver.soa.workflow.request.RequestInfo;

/**
 * Created by adore on 16/7/7.
 */
public class unFreezeBudgetWorkflowAction implements Action {

    public String execute(RequestInfo info) {

        BaseBean log = new BaseBean();

        log.writeLog("进入预算冻结释放unFreezeBudgetWorkflowAction——————");

        String requestid = info.getRequestid();

        if (!" ".equals(requestid)) {

            FinanceUsq fu = new FinanceUsq();
            Boolean result = fu.unFreeze(requestid);
            log.writeLog("result---------=" + result);
            if (!result) {
                log.writeLog("预算冻结释放失败!--------" + result);
                return "-1";
            }
        } else {
            new BaseBean().writeLog("工作流信息获取错误!");
            return "-1";
        }

        return SUCCESS;

    }
}
