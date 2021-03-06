package controller;

import dto.JsonResponseDto;
import dto.PictureDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.IPictureService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private IPictureService iPictureService;

    /**
     * 根据发布图片id获取图片列表
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_pictures_by_postId", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureList(int postId) {
        return iPictureService.queryPostPictureByPostId(postId);
    }

    /**
     * 后台更改图片信息
     * @param pictureDtos
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post_picture", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto updatePicture(@RequestBody List<PictureDto> pictureDtos){
        return iPictureService.updatePostPictureInfo(pictureDtos);
    }


    /**
     * 客户端更改或发布图片
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post_picture_android", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto updatePictureByAndroid(int postId,@RequestParam(value = "pictures", required = false) CommonsMultipartFile[] files,HttpServletRequest request){
        return iPictureService.postPictures(postId,files,request);
    }




    /**
     * 删除图片
     * @param pictureId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete_picture", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto deletePicture(int pictureId,HttpServletRequest request){
        return iPictureService.deletePicture(pictureId,request);
    }



    /**
     * 后台预添加图片
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/pre_post_picture", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto updateAvatar(@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request) {
        return iPictureService.prePostPicture(file,request);
    }
}
