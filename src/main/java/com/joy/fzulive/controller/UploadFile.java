package com.joy.fzulive.controller;

import com.joy.fzulive.UtilTool.FileUtil;
import com.joy.fzulive.bean.Coursefile;
import com.joy.fzulive.bean.ReturnCourse;
import com.joy.fzulive.dao.CoursefileRepository;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
public class UploadFile {
    @Autowired
    CoursefileRepository coursefileRepository;

    //文件上传
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @PostMapping(value = "/upload.do")
    public Coursefile uploadFile(@RequestParam("courseFile" ) MultipartFile file,@RequestParam("courseName") String courseName,@RequestParam("college") String college,@RequestParam("major") String major,@RequestParam("teatherName") String teatherName){
//        System.out.println("fileSize:"+file.getSize());
//        System.out.println("fileType:"+file.getContentType());
//        System.out.println("fileName:"+file.getName());

        //图片所存的地方，其格式为"E:/software/FzuliveResrouce/CourseRes"+"/学院/专业/课程名/任课老师/"
        String filePath = "E:/software/FzuliveResrouce/CourseRes";
        filePath = filePath+"/"+college+"/"+major+"/"+courseName+"/"+teatherName+"/";

        //文件名，其格式为"课程名_任课老师_flag."+"pdf"/"ppt"   flag为这是同一课程和任课老师的课件的第几份课件
        String flag = String.valueOf(coursefileRepository.countByCollegeAndAndMajorAndAndCourseAndAndTeather(college,major,courseName,teatherName)+1);
        String fileName = courseName+"_"+teatherName+"_"+flag+".";
        String fileType = "";

        if(file.getContentType().equals("application/vnd.ms-powerpoint")){
            fileName = fileName +"ppt";
            fileType = "ppt";
        }else  if(file.getContentType().equals("application/pdf")){
            fileName = fileName + "pdf";
            fileType = "pdf";
        }else {
            System.out.println("上传的格式不支持");
        }

        try {
            FileUtil.uploadFile(file.getBytes(),filePath,fileName);
        }catch (Exception e){
            e.printStackTrace();
        }

        //将上传文件的各种信息存到数据库
        Coursefile coursefile = new Coursefile();
        coursefile.setFlag(Integer.parseInt(flag));
        coursefile.setFileName(fileName);
        coursefile.setPath(filePath);
        coursefile.setCollege(college);
        coursefile.setMajor(major);
        coursefile.setCourse(courseName);
        coursefile.setTeather(teatherName);
        coursefile.setType(fileType);
        //获取当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        coursefile.setTime(dateFormat.format(System.currentTimeMillis()));

        return coursefileRepository.save(coursefile);
    }

    /**
     * 获取最近上传的课件信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/GetRecentCourse")
    public List<Coursefile> getRecentCourse(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<Coursefile> coursefilePage = coursefileRepository.findBy(pageable);
        List<Coursefile> coursefileList = new ArrayList<Coursefile>();
        Iterator<Coursefile> it = coursefilePage.iterator();
        while (it.hasNext()){
            coursefileList.add(it.next());
        }
        return coursefileList;
    }

    @GetMapping(value = "/GetCourseType")
    public List<ReturnCourse> getCourseType(){
        //获取所有flag为1的对象，每有一个flag为1的对象，表示有一个相同课程、任课老师的文件夹
        List<Coursefile> coursefileList = coursefileRepository.findByFlag(1);
        Iterator<Coursefile> it = coursefileList.iterator();

        //准备返回的数据
        List<ReturnCourse> returnCourses = new ArrayList<ReturnCourse>();
        while (it.hasNext()){
            Coursefile coursefile = it.next();
            ReturnCourse returnCourse = new ReturnCourse();
            returnCourse.setCollege(coursefile.getCollege());
            returnCourse.setMajor(coursefile.getMajor());
            returnCourse.setCourse(coursefile.getCourse());
            returnCourse.setTeather(coursefile.getTeather());
            returnCourse.setPath(coursefile.getPath());
            returnCourses.add(returnCourse);
        }
        return returnCourses;
    }

    //根据路径获取同一文件夹下的所有文件信息
    @GetMapping(value = "/GetFileByPath")
    public List<Coursefile> getFileByPath(@RequestParam("path") String path){
        return coursefileRepository.findByPath(path);
    }

    //根据传进来的文件id获取路径，并下载
    @GetMapping(value = "/DownloadFile/{fileId}")
    public String downloadFile(@PathVariable("fileId") String fileId, HttpServletResponse response){
        //System.out.println(fileId);
        Coursefile coursefile = coursefileRepository.findById(Long.parseLong(fileId));
        String fileName = coursefile.getFileName();
        String filePath = coursefile.getPath();
        String fileType = coursefile.getType();
        File file = new File(filePath,fileName);
        if(file.exists()){
            //System.out.println("hava");
            if (fileType.equals("ppt")){
                response.setContentType("application/vnd.ms-powerpoint");// 设置返回格式为ppt
            }else if (fileType.equals("pdf")){
                response.setContentType("application/pdf");// 设置返回格式为pdf
            }else{
                System.out.println("不支持该格式");
            }

            response.addHeader("Content-Disposition",
                    "attachment;fileName=" +  fileName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return "success";
    }
}
