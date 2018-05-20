package com.joy.fzulive.UtilTool;

import com.joy.fzulive.bean.ReturnTiezi;
import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReturnTieziUtil {
    /**
     * 将tiezi的迭代器传入，返回传到前端的数据格式
     * @param it 帖子的迭代器
     * @return
     */
    public static List<ReturnTiezi> getReturnTiezis(Iterator<Tiezi> it){
        //给帖子的图片加上这个地址
        String startUrl = "http://localhost:8080/FzuLive";
        String[] imgUrls;
        List<ReturnTiezi> returnTiezis = new ArrayList<ReturnTiezi>();
        while(it.hasNext()){
            Tiezi t = (Tiezi)it.next();
            ReturnTiezi tiezi = new ReturnTiezi();
            tiezi.setId(t.getId());
            tiezi.setName(t.getName());
            tiezi.setTel(t.getTel());
            tiezi.setQq(t.getQq());
            tiezi.setType(t.getType());
            tiezi.setPosition(t.getPosition());
            tiezi.setEventTime(t.getEventTime());
            tiezi.setTextMes(t.getTextMes());
            tiezi.setViewNum(t.getViewNum());
            tiezi.setGoodNum(t.getGoodNum());
            tiezi.setDisplayTime(t.getDisplayTime());
            tiezi.setUser_id(((User)t.getUser()).getId());
            //准备图片url格式
            imgUrls = (t.getImgMes()).split(";");
            for(int i = 0;i<imgUrls.length;i++){
                imgUrls[i]=startUrl+imgUrls[i];
            }
            tiezi.setImgMes(imgUrls);

            tiezi.setUserTX(((User)t.getUser()).getWxtxaddress());
            tiezi.setUserName(((User)t.getUser()).getWxname());
            returnTiezis.add(tiezi);
        }
        return  returnTiezis;
    }
}
