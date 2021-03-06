package cn.basewin.unionpay.network;

import android.util.Log;

import com.basewin.packet8583.factory.Iso8583Manager;
import com.basewin.utils.BytesUtil;

import java.util.Map;

import cn.basewin.unionpay.ActionConstant;
import cn.basewin.unionpay.entity.Card;
import cn.basewin.unionpay.network.remote.AnnotationNet;
import cn.basewin.unionpay.network.remote.ReverseTradeMessage;
import cn.basewin.unionpay.setting.SettingConstant;
import cn.basewin.unionpay.trade.FlowControl;
import cn.basewin.unionpay.trade.InputMoneyAty;
import cn.basewin.unionpay.utils.BCDHelper;
import cn.basewin.unionpay.utils.PosUtil;
import cn.basewin.unionpay.utils.TLog;
import cn.basewin.unionpay.utils.TradeEncUtil;

/**
 * 作   者：hanlei
 * 创建时间：2016/8/3 16:15
 * 描   述：手机芯片预授权完成（通知）报文
 */
@AnnotationNet(action = ActionConstant.ACTION_UPCARD_AUTH_SETTLEMENT)
public class NetUpcardAuthSettlement extends ReverseTradeMessage {
    private static final String TAG = NetUpcardAuthSettlement.class.getName();

    @Override
    protected Iso8583Manager getEncryption(Map<String, Object> map, Iso8583Manager iso) throws Exception {
        TLog.pos(TAG, "Enter 8583 packing of " + TAG);
        Iso8583Manager packManager = iso;
        Card card = FlowControl.MapHelper.getCard();
        packManager.setBit("msgid", "0220");
        if (card != null) {
            packManager.setBit(2, card.getPan());
        }
        packManager.setBit(3, "000000");
        String s = PosUtil.yuanTo12((String) map.get(InputMoneyAty.KEY_MONEY));
        packManager.setBit(4, s);
        if (card.getExpDate().length() > 0) {
            packManager.setBit(14, card.getExpDate());
        }
        String _22 = "96" + PosUtil._22(card).substring(2, 3);
        packManager.setBit(22, _22);

        packManager.setBit(25, "06");

        packManager.setBit(38, FlowControl.MapHelper.getAuthCode());

        if (!card.isIC()) {
            isoSetTrack3(packManager, card.getTrack3ToD());
        } else {
            packManager.setBit(2, card.getPan());
            packManager.setBit(23, card.get23());
        }

        isoSetTrack2(packManager, card.getTrack2ToD());
        packManager.setBit(49, "156");
        packManager.setBit(53, "0610000000000000");
        String batchAuto = SettingConstant.getBatch();
        FlowControl.MapHelper.setBatch(batchAuto);
        packManager.setBit(60, "24" + batchAuto + "000" + "6" + "0");
        packManager.setBit(61, "000000" + "000000" + FlowControl.MapHelper.getDate());
        packManager.setBit(64, "0000000000000000");
        byte[] macInput = iso.getMacData("msgid", "63");
        String mac = TradeEncUtil.getMacECB(macInput);
        Log.d(TAG, "macInput=" + BytesUtil.bytes2HexString(macInput));
        Log.d(TAG, "macInput.length=" + macInput.length);
        Log.d(TAG, "mac=" + mac);
        packManager.setBinaryBit(64, BCDHelper.stringToBcd(mac));
        return packManager;
    }
}
