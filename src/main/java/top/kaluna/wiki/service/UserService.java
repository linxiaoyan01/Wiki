package top.kaluna.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import top.kaluna.wiki.domain.User;
import top.kaluna.wiki.domain.UserExample;
import top.kaluna.wiki.exception.BusinessException;
import top.kaluna.wiki.exception.BusinessExceptionCode;
import top.kaluna.wiki.mapper.UserMapper;
import top.kaluna.wiki.req.UserQueryReq;
import top.kaluna.wiki.req.UserResetPasswordReq;
import top.kaluna.wiki.req.UserSaveReq;
import top.kaluna.wiki.resp.PageResp;
import top.kaluna.wiki.resp.UserQueryResp;
import top.kaluna.wiki.util.CopyUtil;
import top.kaluna.wiki.util.SnowFlake;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:36
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public PageResp<UserQueryResp> list(UserQueryReq userQueryReq){
        UserExample userExample = new UserExample();
        final UserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(userQueryReq.getLoginName())){
            criteria.andLoginNameEqualTo(userQueryReq.getLoginName());
        }

        //两个请求参数
        PageHelper.startPage(userQueryReq.getPage(), userQueryReq.getSize());
        final List<User> users = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

        final List<UserQueryResp> respsList = CopyUtil.copyList(users, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respsList);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            User userDB = selectByLoginName(req.getLoginName());
            if (ObjectUtils.isEmpty(userDB)) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        } else {
            // 更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 修改密码
     * @param req
     */
    public void resetPassword(UserResetPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}

