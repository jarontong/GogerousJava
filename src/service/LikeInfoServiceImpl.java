package service;

import dto.JsonResponseDto;
import dto.PostPictureInfoDto;
import mapper.ILikeInfoMapper;
import mapper.IPostPictureInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeInfoServiceImpl implements ILikeInfoService{

    @Autowired
    ILikeInfoMapper iLikeInfoMapper;

    @Autowired
    IPostPictureInfoMapper iPostPictureInfoMapper;


    @Override
    public JsonResponseDto addLike(int userId, int postId) {
        Map<String,Integer> map=new HashMap<>();
        map.put("userId",userId);
        map.put("postId",postId);
        int index=iLikeInfoMapper.addLike(map);
        if(0>=index){
            return new JsonResponseDto<>(Constants.STATUE_FAIL,"添加喜欢失败","");
        }else {
            PostPictureInfoDto postPictureInfoDto=iPostPictureInfoMapper.queryPostPictureById(postId);
            postPictureInfoDto.setLikeNum(postPictureInfoDto.getLikeNum()+1);
            iPostPictureInfoMapper.updatePostPictureInfo(postPictureInfoDto);
            return new JsonResponseDto<>(Constants.STATUE_OK,"添加喜欢成功","");
        }

    }

    @Override
    public JsonResponseDto deleteLike(int userId, int postId) {
        Map<String,Integer> map=new HashMap<>();
        map.put("userId",userId);
        map.put("postId",postId);
        int index=iLikeInfoMapper.deleteLike(map);
        if(0>=index){
            return new JsonResponseDto<>(Constants.STATUE_FAIL,"取消喜欢失败","");
        }else {
            PostPictureInfoDto postPictureInfoDto=iPostPictureInfoMapper.queryPostPictureById(postId);
            if(postPictureInfoDto.getLikeNum()>0){
                postPictureInfoDto.setLikeNum(postPictureInfoDto.getLikeNum()-1);
                iPostPictureInfoMapper.updatePostPictureInfo(postPictureInfoDto);
            }
            return new JsonResponseDto<>(Constants.STATUE_OK,"取消喜欢成功","");
        }
    }


}
