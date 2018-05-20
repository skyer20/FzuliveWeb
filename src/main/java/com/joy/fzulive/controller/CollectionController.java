package com.joy.fzulive.controller;

import com.joy.fzulive.UtilTool.ReturnTieziUtil;
import com.joy.fzulive.bean.Collection;
import com.joy.fzulive.bean.ReturnTiezi;
import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.CollectionRepository;
import com.joy.fzulive.dao.TieziRepository;
import com.joy.fzulive.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
public class CollectionController {
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TieziRepository tieziRepository;

    //注意如果是post接收的话，获取参数不能用@RequestParam，要用@RequestBody
    //增加一条收藏记录
    @PostMapping(value = "/AddCollection")
    public Collection addCollection(@RequestBody Map<String,Object> reqMap){
        String userId = reqMap.get("userId").toString();
        String tieziId = reqMap.get("tieziId").toString();
        Collection collection = new Collection();
        User user = userRepository.findById(Long.parseLong(userId));
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(tieziId));
        collection.setUser(user);
        collection.setTiezi(tiezi);
        //获取当前发帖时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        collection.setCollectTime(dateFormat.format(System.currentTimeMillis()));

        return collectionRepository.save(collection);
    }

    //删除一条收藏记录
    @Transactional
    @GetMapping(value = "/DeleteCollection")
    public Collection deleteCollection(@RequestParam(value = "userId") String userID,@RequestParam(value = "tieziId") String tieziID){
        User user = userRepository.findById(Long.parseLong(userID));
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(tieziID));
        Collection collection = collectionRepository.findByUserAndTiezi(user,tiezi);
        collectionRepository.delete(collection);
        return collection;
    }

    //查找是否存在收藏记录
    @GetMapping(value = "/HavaCollection")
    public boolean havaCollection(@RequestParam(value = "userId") String userID,@RequestParam(value = "tieziId") String tieziID){
        User user = userRepository.findById(Long.parseLong(userID));
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(tieziID));
        return  collectionRepository.existsByUserAndTiezi(user,tiezi);
    }

    //根据用户查找收藏记录 返回帖子格式列表
    @GetMapping(value = "/GetCollectionByUser")
    public List<ReturnTiezi> getCollectionByUser(@RequestParam(value = "userId") String userID){
        User user = userRepository.findById(Long.parseLong(userID));
        List<Collection> collections = collectionRepository.findByUser(user);
        List<Tiezi> tiezis = new ArrayList<Tiezi>();
        Iterator<Collection> collectionIterator = collections.iterator();
        while (collectionIterator.hasNext()){
            tiezis.add(collectionIterator.next().getTiezi());
        }
        Iterator<Tiezi> it = tiezis.iterator();
        return ReturnTieziUtil.getReturnTiezis(it);
    }
}
