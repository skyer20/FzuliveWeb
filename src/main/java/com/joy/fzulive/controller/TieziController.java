package com.joy.fzulive.controller;


import com.joy.fzulive.UtilTool.FileUtil;
import com.joy.fzulive.UtilTool.ReturnTieziUtil;
import com.joy.fzulive.UtilTool.UserTieziUtil;
import com.joy.fzulive.bean.ReturnTiezi;
import com.joy.fzulive.bean.Tiezi;
import com.joy.fzulive.bean.User;
import com.joy.fzulive.dao.TieziRepository;
import com.joy.fzulive.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class TieziController {
    @Autowired
    TieziRepository tieziRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * 增加一条发帖记录
     * @param reqMap
     * @return
     */
    @PostMapping(value = "/Release")
    public Tiezi addOneTiezi(@RequestBody Map<String,Object> reqMap){
        //获取发帖人id
        String userId = reqMap.get("userId").toString();
        User user =  userRepository.findById(Long.parseLong(userId));
        //System.out.println("User:"+user);

        Tiezi tiezi = new Tiezi();
        tiezi.setTextMes(reqMap.get("textContent").toString());
        tiezi.setType(reqMap.get("releasetype").toString());
        tiezi.setTel(reqMap.get("tel").toString());
        tiezi.setQq(reqMap.get("qq").toString());
        tiezi.setName(reqMap.get("name").toString());
        tiezi.setPosition(reqMap.get("position").toString());
        tiezi.setEventTime(reqMap.get("time").toString());
        tiezi.setImgMes("");
        //获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        tiezi.setDisplayTime(dateFormat.format(System.currentTimeMillis()));

        //将tiezi对象加到user对象中，将user设置到帖子对象中
        user.getTieziSet().add(tiezi);
        tiezi.setUser(user);

        //不能使用toString方法，否则会出现无限调用对方的死循环
        //System.out.println("Tiezi:"+tiezi);
        return tieziRepository.save(tiezi);
    }

    /**
     * 图片上传
     * @param file
     * @param request
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping(value = "/UploadPicture")
    public String uploadPicture(@RequestParam("pictureFile" ) MultipartFile file, HttpServletRequest request){
        //获取帖子id
        String TieziId = request.getParameter("id");


        //所存图片名字的格式：当前毫秒数+文件大小.png
        String fileName = String.valueOf(System.currentTimeMillis())+file.getSize()+".png";


        //图片所存的地方，其格式为"E:/software/FzuliveResrouce/TieziPicture"+"/帖子id"
        String filePath = "E:/software/FzuliveResrouce/TieziPicture";
        //String filePath = request.getSession().getServletContext().getRealPath("/TieziPicture/test/");
        filePath = filePath+"/"+TieziId+"/";


        System.out.println("file.size:"+file.getSize());
        System.out.println("filePath:"+filePath);
        System.out.println("fileName:"+fileName);

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }

        //将能访问到所存图片的url地址存到对应表中
        //格式为  /帖子id/图片名字;
        String url = "/"+TieziId+"/"+fileName+";";
        Tiezi tiezi = tieziRepository.findById(Long.parseLong(TieziId));
        url = url+tiezi.getImgMes();
        tiezi.setImgMes(url);
        tieziRepository.save(tiezi);
        return "success";
    }

    /**
     * 获取最发帖信息（分页查询）
     * @param page 页数
     * @param size 每页记录数
     * @return
     */
    @GetMapping(value = "/GetRecentTiezi")
    public List<ReturnTiezi> getRecentTiezi(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Tiezi> pages = tieziRepository.findBy(pageable);
        Iterator<Tiezi> it = pages.iterator();
        return ReturnTieziUtil.getReturnTiezis(it);
    }

    /**
     * 获取同一分类的帖子信息
     * @param type 分类的类名
     * @return 返回前端要求的帖子格式的List对象
     */
    @GetMapping(value = "/GetBBSByType")
    public List<ReturnTiezi> getBBSByType(@RequestParam(value = "bbsType") String type){
        //System.out.println(type);
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Tiezi> tiezis = tieziRepository.findByType(type,sort);
        Iterator<Tiezi> it = tiezis.iterator();
        return ReturnTieziUtil.getReturnTiezis(it);
        //return null;
    }

    /**
     * 获取用户所发的帖子
     * @param userId
     * @return
     */
    @GetMapping(value = "/GetMyBBSs")
    public List<ReturnTiezi> getMyBBSs(@RequestParam(value = "userId") String userId){
        User user = userRepository.findById(Long.parseLong(userId));
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Tiezi> tiezis = tieziRepository.findByUser(user,sort);
        Iterator<Tiezi> it = tiezis.iterator();
        return ReturnTieziUtil.getReturnTiezis(it);
    }

    /**
     * 根据类型和内容进行搜索
     * @param type 类型
     * @param content  内容
     * @return
     */
    @GetMapping(value = "/GetSearchBBS")
    public List<ReturnTiezi> getSearchBBS(@RequestParam(value = "searchType") String type,@RequestParam(value = "content") String content){
        //System.out.println("type:"+type+";content:"+content);
        List<Tiezi> tiezis = new ArrayList<Tiezi>();
        List<User> users;
        switch (type){
            case "微信名":
                users = userRepository.findUserByWxname(content);
                Sort sort = new Sort(Sort.Direction.DESC,"id");
                tiezis = UserTieziUtil.getTiezisByUsers(users,sort,tieziRepository);
                break;
            case "帖子内容":
                String text = '%'+content+'%';
                tiezis = tieziRepository.searchTieziByLike(text);
                break;
            case "联系人":
                tiezis = tieziRepository.findByName(content);
                break;
            case "QQ":
                tiezis = tieziRepository.findByQq(content);
                break;
            case "手机号":
                tiezis = tieziRepository.findByTel(content);
                break;
        }
        return ReturnTieziUtil.getReturnTiezis(tiezis.iterator());
    }
}
