package com.v.im.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.v.im.user.entity.ImChatGroup;
import com.v.im.user.entity.ImUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 乐天
 * @since 2018-10-07
 */
@Component
@Qualifier("imUserMapper")
public interface ImUserMapper extends BaseMapper<ImUser> {

    /**
     * 根据用户id 获取群组
     * @param userId id
     * @return List<ImGroup>
     */
    List<ImChatGroup> getUserGroups(String userId);


    /**
     * 获取群组的用户
     * @param chatId 群组id
     * @return 用户List
     */
    List<ImUser> getChatUserList(String chatId);

    /**
     * 根据部门分组获取人员数
     * @return map
     */
    List<Map> groupByDept();
}
