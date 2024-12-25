package com.example.fs.captch;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.example.fs.common.exception.ParameterException;
import com.example.fs.common.util.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 17266
 * @version 1.0
 * @data 2023/8/30 15:56
 */
@Service
@RequiredArgsConstructor
public class CaptchaServiceImpl {

    public static final String CACHE_NAME = "identify:";

    private final RedisTemplate<String, Object> redisTemplate;

    public CodeVO creatCodeImg() {

        String uuid = generateUuid();
        LineCaptcha lineCaptcha = generateLineCaptcha();

        String code = lineCaptcha.getCode();
        String base64 = lineCaptcha.getImageBase64Data();

        redisTemplate.opsForValue().set(CACHE_NAME+uuid ,code , 5 , TimeUnit.MINUTES);

        CodeVO codeVO = new CodeVO();
        codeVO.setUuid(uuid);
        codeVO.setBase64(base64);

        return codeVO;
    }

    public boolean checkCode(String codeDto) {
        String[] parts = codeDto.split(":");
        if (parts.length != 2) {
            throw new ParameterException("验证码错误");
        }
        String uuid = parts[0];
        String code = parts[1];

        String trueCode = (String) redisTemplate.opsForValue().get(CACHE_NAME + uuid);

        Assert.notNull(trueCode,"验证码已过期。");
        if (code.equalsIgnoreCase(trueCode)) {
            redisTemplate.delete(CACHE_NAME + uuid);
            return true;
        }
        return false;
    }

    private String generateUuid() {
        // 生成临时的唯一标识符
        return UUID.randomUUID().toString();
    }

    private LineCaptcha generateLineCaptcha() {
        // 创建一个验证码  长，宽，字符数，干扰元素个数
        return CaptchaUtil.createLineCaptcha(200, 100, 4, 100);
    }
}
