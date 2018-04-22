package controller;

import dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import service.IPostPictureInfoService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post_picture_info")
public class PostPictureInfoController {

    @Autowired
    IPostPictureInfoService iPostPictureInfoService;

    /**
     * 获取发布图片列表，全部
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_picture_list", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureList() {
        return iPostPictureInfoService.queryPostPictureList();
    }

    /**
     * 后台根据页数获取发布列表
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_picture_list_by_page", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureListByPage(int page,int size) {
        return getPostListByPage(page, size, -1);
    }


    /**
     * 后台根据页数获取发布列表
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_picture_list_by_page_android", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureListByPageAndroid(int page,int size,int userId) {
        return getPostListByPage(page, size, userId);
    }

    private JsonResponseDto getPostListByPage(int page, int size, int userId) {
        PageDto pageDto=new PageDto();
        pageDto.setStart((page-1)*size);
        pageDto.setCount(size);
        int total=iPostPictureInfoService.queryPostPictureCount();
        pageDto.caculateLast(total);
        System.out.println("发布图片列表数量为:"+total);
        return iPostPictureInfoService.queryPostPictureListByPage(pageDto,userId);
    }


    /**
     * 根据发布id获取信息
     * @param postPictureId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_pictrue_by_id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureById(int postPictureId) {
        return iPostPictureInfoService.queryPostPictureById(postPictureId);
    }


    /**
     * 根据用户id获取信息,可以判断用户是否已喜欢
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_pictrue_by_userId", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureByUserId(int userId) {
        return iPostPictureInfoService.queryPostPictureByUserId(userId);
    }


    /**
     * 根据类型获取信息,可以判断用户是否已喜欢
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_pictrue_by_type", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureByType(int userId,int type) {
        return iPostPictureInfoService.queryPostPictureByType(userId,type);
    }



    /**
     * 发布套图
     * @param postPictureInfoDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto postPicture(@RequestBody PostPictureInfoDto postPictureInfoDto) {
        return iPostPictureInfoService.postPicture(postPictureInfoDto);
    }

    /**
     * 发布套图
     * @param postPictureInfoDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/post_has_file", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResponseDto postPictureHasFile( PostPictureInfoDto postPictureInfoDto,@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request) {
        return iPostPictureInfoService.postPictureHasCover(postPictureInfoDto,file,request);
    }




    /**
     * 后台更新套图发布信息
     * @param postPictureInfoDto
     * @return
     */
    @RequestMapping(value = "/update_post_picture_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  updatePostPictureInfo(@RequestBody PostPictureInfoDto postPictureInfoDto){
        System.out.println("*******************"+postPictureInfoDto.toString());
        return iPostPictureInfoService.upDatePostPictureInfo(postPictureInfoDto);
    }

    /**
     * 更新封面
     * @param
     * @return
     */
    @RequestMapping(value = "/update_post_picture_cover", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  updatePostPictureCover(int postId,@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request ){
        return iPostPictureInfoService.updatePostPictureCover(postId,file,request);
    }





    /**
     * 删除套图发布信息
     * @param postPictureId
     * @return
     */
    @RequestMapping(value = "/delete_post_picture_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  deleteUser(int postPictureId,HttpServletRequest request){
        return iPostPictureInfoService.deletePostPicture(postPictureId,request);
    }


    /**
     * 预更新套图封面
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value = "/pre_upadate_cover", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto<String> updateAvatar(@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request) {
        return iPostPictureInfoService.preUpdateCover(file,request);
    }


    /**
     * 增加浏览数
     * @param postPictureId
     * @return
     */
    @RequestMapping(value = "/add_view_num", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  addViewNum(int postPictureId){
        return iPostPictureInfoService.addView(postPictureId);
    }

    /**
     * 获取用户喜欢的列表
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_list_like", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureLike(int userId) {
        return iPostPictureInfoService.queryPostPictureLike(userId);
    }
}
