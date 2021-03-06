package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputManagerPWDAty;
import cn.basewin.unionpay.trade.InputPWDAty;
import cn.basewin.unionpay.trade.InputTraceAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.ui.CheckInfoAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 预约消费撤销 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_RESERVATION_VOID)
public class MenuReservationVoid extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_RESERVATION_VOID));
    }

    @Override
    public int getResIcon() {
        return R.drawable.main_cancel_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                TLog.pos(TAG, "MenuVoid");
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputManagerPWDAty.class)
                        .next(InputTraceAty.class)
                        .next(CheckInfoAty.class)
                        .next(InputPWDAty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_RESERVATION_VOID);
            }
        };
    }
}
