package utils;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    /**
     * 文件上传
     * @param file
     * @param request
     * @param fileAddress
     * @return  返回文件地址
     */
    public static String upLoadFile(CommonsMultipartFile file, HttpServletRequest request, String fileAddress,String name) {
        String fileName = "";
        if (null == file) {
            return fileName;
        } else {
            ServletContext servletContext = request.getSession().getServletContext();
            //上传位置
            String path = servletContext.getRealPath(File.separator + fileAddress) + File.separator;   //设定文件保存的目录
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            if (!file.isEmpty()) {
                // 获取图片的文件名
                fileName = file.getOriginalFilename();
                // 获取图片的扩展名
                String extensionName = fileName
                        .substring(fileName.lastIndexOf(".") + 1);
                // 新的图片文件名 = 获取时间戳+"."图片扩展名
                fileName = name
                        + "." + extensionName;
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    fos = new FileOutputStream(path + fileName);
                    is = file.getInputStream();
                    byte[] b = new byte[1024 * 1024];
                    int len;
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    fileName = "";
                } catch (IOException e) {
                    e.printStackTrace();
                    fileName = "";
                } finally {
                    if (null != fos) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != is) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return (File.separator + fileAddress + File.separator+fileName);
    }


    /**
     * 上传多个文件
     * @param files
     * @param request
     * @param fileAddress
     * @return
     */
    public static List<String> upLoadFiles(CommonsMultipartFile[] files, HttpServletRequest request, String fileAddress){
         List<String> fileAddressList=new ArrayList<>();
        if(null==files||0>=files.length){
            return null;
        }else {
            for (int i = 0; i < files.length; i++) {
                fileAddressList.add(upLoadFile(files[i],request,fileAddress,"picture_"+System.currentTimeMillis()));
            }

            return fileAddressList;
        }
    }
}
