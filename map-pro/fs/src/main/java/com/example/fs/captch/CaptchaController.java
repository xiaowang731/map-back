package com.example.fs.captch;

import com.example.fs.common.JsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Adss
 * @date 2024/9/9 下午1:31
 */
@RestController
@RequestMapping("/api/v1/code-img")
@Tag(name = "验证码")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaServiceImpl captchaService;

    @Operation(summary = "获取图形验证码")
    @GetMapping("/")
    public JsonResponse<CodeVO> codeImg() {
        CodeVO codeVO = captchaService.creatCodeImg();

        return new JsonResponse<CodeVO>().data(codeVO).success();
    }

}
