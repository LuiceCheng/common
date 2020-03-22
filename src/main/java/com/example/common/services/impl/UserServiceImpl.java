package com.example.common.services.impl;

import com.example.common.config.execptions.BussinessException;
import com.example.common.config.redis.RedisRepository;
import com.example.common.dao.IBaseDao;
import com.example.common.dao.IUserDao;
import com.example.common.entity.GlobalConstants;
import com.example.common.entity.Msg;
import com.example.common.enums.EnError;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import com.example.common.sms.key.SmsCacheKey;
import com.example.common.utils.CommonUtil;
import com.example.common.utils.EncryptUtil;
import com.example.common.utils.IdWorker;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private IUserDao userDao;

    @Override
    public void setDefaults(User record) {
        String userId = record.getUserId();
        if(StringUtils.isEmpty(userId)){
            userId = IdWorker.getUUID();
            record.setUserId(userId);
        }
        String password = record.getPassword();
        if(StringUtils.isNotEmpty(password)){
            String ps = EncryptUtil.AESencode(password, GlobalConstants.AES_KEY);
            record.setPassword(ps);
        }
    }

    @Override
    public IBaseDao<User> getRepositoryDao() {
        return this.userDao;
    }

    @Override
    public String getPrimaryKeyValue(User record) {
        return record.getUserId();
    }

    @Override
    public void emptyPrimaryKeyValue(User record) {
        record.setUserId("");
    }

    @Override
    public String getSmsVcodeToken(String phone, String vcode) {
        User user = new User();
        user.setUserName(phone);
        Msg<List<User>> listMsg = this.selectByExample(user);
        boolean b = CommonUtil.collectionIsEmpty(listMsg);
        if (b) {
            throw new BussinessException(EnError.NO_MATCH.getCode(), EnError.NO_MATCH.getDescription());
        }

        String s = redisRepository.get(SmsCacheKey.keyOfSmsVcode(phone));
        if (null == s) {
            throw new BussinessException("短信验证码已过期");
        }
        if (!s.equals(vcode)) {
            throw new BussinessException("短信验证码错误");
        }
        String token = RandomStringUtils.random(30, true, true);
        redisRepository.setExpire(SmsCacheKey.keyOfSmsVcodeToken(phone), token, 5 * 60);
        return token;
    }


    @Override
    public void modifyPwdWidthVcodeToken(String vcodeToken, String phone, String newPwd) {
        // 校验短信验证码
        String cacheToken = redisRepository.get(SmsCacheKey.keyOfSmsVcodeToken(phone));
        if (null == cacheToken) {
            throw new BussinessException("认证过期，请重新获取验证码");
        }
        if (!cacheToken.equals(vcodeToken)) {
            throw new BussinessException("认证失败");
        }

        User user = new User();
        user.setUserName(phone);
        Msg<List<User>> listMsg = this.selectByExample(user);
        boolean b = CommonUtil.collectionIsEmpty(listMsg);
        if (b) {
            throw new BussinessException(EnError.NO_MATCH.getCode(), EnError.NO_MATCH.getDescription());
        }

//        SysUser record = baseMapper.findByUsername(phone);
//        if (null == record) {
//            throw new BussinessException("用户不存在");
//        }
//        if (BaseConstant.ZERO.equals(record.getStatus())) {
//            throw new BussinessException("用户已被禁用");
//        }

        String salt = RandomStringUtils.random(20, true, true);
        newPwd = EncryptUtil.MD5(newPwd, salt);

        User updateUser = new User();
//        updateUser.setId(record.getId());
//        updateUser.setPassword(newPwd);
//        updateUser.setSalt(salt);
//        updateUser.updateById();
        this.updateByPrimaryKey(updateUser);
    }

    @Override
    public Msg<List<User>> getUserByName(User user) {
        Msg<List<User>> msg = new Msg<>();
        List<User> userByName = userDao.getUserByName(user);
        if(CollectionUtils.isEmpty(userByName)){
            msg.setResult(EnError.NO_MATCH);
            return msg;
        }
        msg.setData(userByName);
        return msg;
    }
}
