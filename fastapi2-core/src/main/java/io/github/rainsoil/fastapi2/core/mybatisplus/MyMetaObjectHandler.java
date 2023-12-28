package io.github.rainsoil.fastapi2.core.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.core.user.LoginUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自定义字段数据填充
 *
 * @author luyanan
 * @since 2023/09/19
 **/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	@Override
	public void insertFill(MetaObject metaObject) {

		log.debug("开始insert填充");

		LoginUser user = LoginUserUtils.getUser();
		if (null != user) {
			this.strictInsertFill(metaObject, "createBy", () -> user.getUserId(), Long.class);
			this.strictInsertFill(metaObject, "updateBy", () -> user.getUserId(), Long.class);
		}

		this.strictInsertFill(metaObject, "createTime", () -> new Date(), Date.class);
		this.strictInsertFill(metaObject, "updateTime", () -> new Date(), Date.class);

	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.debug("开始update填充");


		LoginUser user = LoginUserUtils.getUser();
		if (null != user) {
			this.strictUpdateFill(metaObject, "updateBy", () -> user.getUserId(), Long.class);
		}

		this.strictUpdateFill(metaObject, "updateTime", () -> new Date(), Date.class);
	}
}
