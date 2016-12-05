package cn.basewin.unionpay.trade;

import android.text.TextUtils;

import cn.basewin.unionpay.AppConfig;
import cn.basewin.unionpay.R;
import cn.basewin.unionpay.base.BaseInputAty;
import cn.basewin.unionpay.utils.TLog;


/**
 * 作者: wdh <br>
 * 内容摘要: <br>
 * 创建时间:  2016/6/28 09:52<br>
 * 描述: 输批次  <br>
 */
public class InputBatchAty extends BaseInputAty {
    public static final int resultCode = AppConfig.RESULT_CODE_INPUT_BATCH;
    public static final String KEY_DATA = "InputBatch";


    @Override
    protected void init() {
        super.init();
        setHint(getString(R.string.Please_enter_batch));
        setLabel2("");
        setLenghtEdit(6);
        setRule(BaseInputAty.rule_number);
    }

    @Override
    protected void clickOK(String s) {
        TLog.e(this, s);
        if (!TextUtils.isEmpty(s)) {
            FlowControl.MapHelper.setBatch(s);
            startNextFlow();
        } else {
            TLog.showToast(getString(R.string.Please_enter_batch));
        }
    }

    @Override
    protected void clickunOK() {
        finish();
    }
}
