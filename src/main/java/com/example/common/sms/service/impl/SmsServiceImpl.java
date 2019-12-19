package com.example.common.sms.service.impl;

import com.example.common.config.execptions.BussinessException;
import com.example.common.config.redis.RedisRepository;
import com.example.common.entity.Msg;
import com.example.common.model.User;
import com.example.common.services.IUserService;
import com.example.common.sms.key.SmsCacheKey;
import com.example.common.sms.service.ISmsService;
import com.example.common.sms.util.UMSSmsUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * description:
 *
 * @author:cxs
 * @date: 2019/12/19 17:43
 */
@Service
public class SmsServiceImpl implements ISmsService {
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void sendSmsVcode(String phone) {
        umsSendVcode(phone);
    }

    private void validatePhone(String phone) {
        if(StringUtils.isEmpty(phone)) {
            throw new BussinessException("手机号码为空");
        }
        if(!phone.matches("[0-9]{11}")) {
            throw new BussinessException("手机号码格式错误");
        }
    }

    private void umsSendVcode(String phone) {
        validatePhone(phone);
        User user = new User();
        Msg<List<User>> listMsg = userService.selectByExample(user);

        if(null == listMsg || null == listMsg.getData() || listMsg.getData().size() == 0){
            throw new BussinessException("用户【" + phone + "】不存在");
        }
//        SysUser sysUser = sysUserMapper.findByUsername(phone);
//        if (null == sysUser) {
//            throw new BussinessException("用户【" + sysUser.getUsername() + "】不存在");
//        }
//
        String vcodeKey = SmsCacheKey.keyOfSmsVcode(phone);
        String vcodeSendIntervalKey = SmsCacheKey.keyOfSmsVcodeSendInterval(phone);
//      if(null != redisService.get(vcodeSendIntervalKey)) {
        if(null != redisRepository.get(vcodeSendIntervalKey)) {
            throw new BussinessException("请求发送验证码过于频繁");
        }
//
        String vcode = RandomStringUtils.random(6, false, true);
        String message = "尊敬的" + "张三" + "，您本次验证码为" + vcode + "请在5分钟内使用。";
        boolean flag = UMSSmsUtil.sendMsg(phone, message);
        if (!flag) {
            throw new BussinessException("发送失败，请重试");
        }
        redisRepository.setExpire(vcodeKey, vcode, 5 * 60);
        redisRepository.setExpire(vcodeSendIntervalKey, "", 1 * 60);
//        redisService.set(vcodeKey, vcode, 5, TimeUnit.MINUTES);
//        redisService.set(vcodeSendIntervalKey, "", 1, TimeUnit.MINUTES);
    }
}
