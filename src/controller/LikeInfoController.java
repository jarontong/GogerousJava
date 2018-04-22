package controller;

import dto.JsonResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ILikeInfoService;

@Controller
@RequestMapping("/like_info")
public class LikeInfoController {

    @Autowired
    ILikeInfoService iLikeInfoService;


    @RequestMapping(value = "/add_like", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto addLike(int userId,int postId){
        return iLikeInfoService.addLike(userId,postId);
    }


    @RequestMapping(value = "/delete_like", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public JsonResponseDto deleteLike(int userId,int postId){
        return iLikeInfoService.deleteLike(userId,postId);
    }
}
