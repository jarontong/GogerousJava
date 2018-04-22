package mapper;

import dto.LikeDto;

import java.util.List;
import java.util.Map;

public interface ILikeInfoMapper {

    int addLike(Map<String,Integer> map);

    int deleteLike(Map<String,Integer> map);

    List<LikeDto> queryUserLike(int userId);
}
