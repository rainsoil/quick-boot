package io.github.rainsoil.fastapi2.system.serverless.function;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.rainsoil.fastapi2.common.core.R;
import io.github.rainsoil.fastapi2.common.security.annotation.IgnoreAuth;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 函数接口类
 *
 * @author luyanan
 * @since 2023/12/27
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("serverless.function")
public class FunctionController {


	private final FunctionFacade functionFacade;

	/**
	 * 调用接口
	 *
	 * @param functionRequest 参数
	 * @return java.lang.Object
	 * @since 2023/12/27
	 */
	@IgnoreAuth
	@ApiOperation(value = "调用接口")
	@PostMapping("invoke")
	public R<Object> invoke(@RequestBody FunctionRequest functionRequest) {
		return R.ok(functionFacade.invoke(functionRequest));
	}
}
