package com.joy.fzulive.UtilTool;

import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.TieziRepository;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserTieziUtil {

    public static List<Tiezi> getTiezisByUsers(List<User> users, Sort sort, TieziRepository tieziRepository){
        List<Tiezi> tiezis = new ArrayList<Tiezi>();
        Iterator<User> userIterator = users.iterator();
        while (userIterator.hasNext()){
            tiezis.addAll(getTiezisByUser(userIterator.next(),sort,tieziRepository));
        }
        return tiezis;
    }
    public  static List<Tiezi> getTiezisByUser(User user,Sort sort,TieziRepository tieziRepository){
        List<Tiezi> tiezis =tieziRepository.findByUser(user,sort);
        return tiezis;
    }
}
