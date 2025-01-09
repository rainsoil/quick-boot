package ${packag}.service.impl;

import ${packag}.entity.${className}Entity;
import ${packag}.dos.${className}Do;
import ${packag}.mapper.${className}Mapper;
import ${packag}.service.I${className}Service;
import cn.t200.quickboot.data.mybatisplus.BaseServiceImpl2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
 * <p>
 * ${tableComment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@RequiredArgsConstructor
@Service
public class ${className}ServiceImpl extends BaseServiceImpl2<${className}Mapper, ${className}Entity, ${className}Do> implements I${className}Service {

}

