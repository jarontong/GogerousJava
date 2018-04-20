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
     * 根据页数获取发布列表
     * @param page
     * @param size
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_picture_list_by_page", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureListByPage(int page,int size) {
        PageDto pageDto=new PageDto();
        pageDto.setStart((page-1)*size);
        pageDto.setCount(size);
        int total=iPostPictureInfoService.queryPostPictureCount();
        pageDto.caculateLast(total);
        System.out.println("发布图片列表数量为:"+total);
        return iPostPictureInfoService.queryPostPictureListByPage(pageDto);
    }


    /**
     * 根据id获取信息
     * @param postPictureId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_post_pictrue_by_id", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JsonResponseDto getPostPictureById(int postPictureId) {
        return iPostPictureInfoService.queryPostPictureById(postPictureId);
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
     * 更新套图发布信息
     * @param postPictureInfoDto
     * @return
     */
    @RequestMapping(value = "/update_post_picture_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  updatePostPictureInfo(@RequestBody PostPictureInfoDto postPictureInfoDto){
        return iPostPictureInfoService.upDatePostPictureInfo(postPictureInfoDto);
    }



    /**
     * 删除套图发布信息
     * @param postPictureId
     * @return
     */
    @RequestMapping(value = "/delete_post_picture_info", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto  deleteUser(int postPictureId){
        return iPostPictureInfoService.deletePostPicture(postPictureId);
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

}
