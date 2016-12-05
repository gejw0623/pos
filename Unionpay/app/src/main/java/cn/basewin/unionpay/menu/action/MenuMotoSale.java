package cn.basewin.unionpay.menu.action;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputCVN2Aty;
import cn.basewin.unionpay.trade.InputCardExpDateAty;
import cn.basewin.unionpay.trade.InputCardNumberAty;
import cn.basewin.unionpay.trade.InputID6Aty;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.trade.InputNameAty;
import cn.basewin.unionpay.trade.InputPhoneNoAty;
import cn.basewin.unionpay.trade.NetUploadSignaWaitAty;
import cn.basewin.unionpay.trade.NetWaitAty;
import cn.basewin.unionpay.trade.PrintWaitAty;
import cn.basewin.unionpay.trade.SignatureAty;
import cn.basewin.unionpay.utils.TLog;

/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/7/21 16:05<br>
 * 描述: 订购消费 <br>
 */
@AnnotationMenu(action = ActionConstant.ACTION_MOTO_SALE)
public class MenuMotoSale extends MenuAction {
    @Override
    public String getResName() {
        return TLog.getString(ActionConstant.getAction(ActionConstant.ACTION_MOTO_SALE));
    }

    @Override
    public int getResIcon() {
        return R.drawable.moto_state;
    }

    @Override
    public Runnable getRun() {
        return new Runnable() {
            @Override
            public void run() {
                FlowControl flowControl = new FlowControl();
                flowControl.begin(InputMoneyAty.class)
                        .next(InputCardNumberAty.class)
                        .next(InputCardExpDateAty.class)
                        .next(InputCVN2Aty.class)
                        .next(InputNameAty.class)
                        .next(InputPhoneNoAty.class)
                        .next(InputID6Aty.class)
                        .next(NetWaitAty.class)
                        .next(SignatureAty.class)
                        .next(NetUploadSignaWaitAty.class)
                        .next(PrintWaitAty.class)
                        .start(getContext(), ActionConstant.ACTION_MOTO_SALE);
            }
        };
    }
}
