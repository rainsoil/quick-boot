package io.github.rainsoil.fastapi2.common.security;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastapi2.common.spring.SpringContextHolder;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 客户端处理
 *
 * @author luyanan
 * @since 2023/11/30
 **/
@Component
public class ClientUtils {

	@Autowired
	private ClientProperties clientProperties;

	/**
	 * 参数处理
	 *
	 * @param param 参数
	 * @return
	 * @since 2023/11/30
	 */
	public String paramHandler(String param) {
		return paramHandler(param, null);
	}

	/**
	 * 参数处理
	 *
	 * @param param   参数
	 * @param request 请求头
	 * @return
	 * @since 2023/11/30
	 */
	public String paramHandler(String param, HttpServletRequest request) {

		ClientProperties.Client client = getClientId(request);
		String sm4Key = SpringUtil.getProperty("sm4.key");
		param = URLUtil.decode(param);
		if (client.getEncryption()) {
			// 解密
			if (StrUtil.isBlank(sm4Key)) {
				throw new RuntimeException("sm4Key 不能为空");
			}
			// 解密
			SM4 sm4 = new SM4(HexUtil.decodeHex(sm4Key));
			param = sm4.decryptStr(param);
		}
		return param;

	}

	/**
	 * 获取客户端配置
	 *
	 * @return
	 * @since 2023/11/30
	 */
	public ClientProperties.Client getClientId() {
		return getClientId(null);
	}

	/**
	 * 获取客户端配置
	 *
	 * @param request 请求头
	 * @return
	 * @since 2023/11/30
	 */
	public ClientProperties.Client getClientId(HttpServletRequest request) {
		if (null == request) {
			request = SpringContextHolder.getRequest();
		}
		Map<String, ClientProperties.Client> client = clientProperties.getClient();

		String authorization = request.getHeader("Authorization");
		if (StrUtil.isBlank(authorization)) {
			return null;
		}
		if (!authorization.startsWith("Basic")) {

			SecurityContext context = SecurityContextHolder.getContext();
			if (null == context) {
				return null;
			}
			Authentication authentication = context.getAuthentication();
			if (null == authentication) {
				return null;
			}
			Object details = authentication.getPrincipal();
			if (null == details) {
				return null;
			}
			String clientId = JSON.parseObject(JSON.toJSONString(details)).getString("clientId");
			return Opt.ofBlankAble(clientId).map(a -> client.get(clientId)).orElse(null);
		}
		authorization = authorization.replace("Basic", "").trim();
		String s = null;
		try {
			s = Base64.decodeStr(authorization);
		} catch (Exception e) {
		}
		if (StrUtil.isBlank(s) || !s.contains(":")) {

			return null;
		}
		String clientId = s.split(":")[0];
		// 检查客户端id和密钥是否一致
		ClientProperties.Client client1 = client.get(clientId);
		if (null == client1) {
			return null;
		}
		String encode = Base64.encode(clientId + ":" + client1.getSecret());
		if (!encode.equals(authorization)) {
			return null;
		}
		return client1;
	}


}
