package io.github.rainsoil.fastapi2.web.serverless.function;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.github.rainsoil.fastapi2.system.serverless.function.FunctionRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest()
public class FunctionControllerTest {
	@Autowired
	MockMvc mvc;

	private final static String TEXT_TYPE = "test";

	@Test
	public void page() throws Exception {

		FunctionRequest functionRequest = new FunctionRequest();
		functionRequest.setFunctionMethod("get");
		functionRequest.setFunctionTarget("sysDict");
		PageRequest pageRequest = new PageRequest();
		pageRequest.setSize(10);
		pageRequest.setCurrent(1);

		SysDict sysDict = new SysDict();
		sysDict.setType(TEXT_TYPE);
		pageRequest.setParam(sysDict);
		functionRequest.setFunctionParams(Arrays.asList(

				JSON.toJSONString(pageRequest)
		));

		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/serverless.function/invoke")
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(functionRequest));//阿里巴巴的json序列化


		String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("result:{}", contentAsString);
	}

	/**
	 * 添加的测试
	 *
	 * @return
	 * @since 2023/12/27
	 */
	@Test
	public void save() throws Exception {
		FunctionRequest functionRequest = new FunctionRequest();
		functionRequest.setFunctionMethod("save");
		functionRequest.setFunctionTarget("sysDict");
		SysDict sysDict = new SysDict();
		sysDict.setType(TEXT_TYPE);
		sysDict.setDescription("测试");
		functionRequest.setFunctionParams(Arrays.asList(

				JSON.toJSONString(sysDict)
		));
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/serverless.function/invoke")
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(functionRequest));//阿里巴巴的json序列化


		String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("result:{}", contentAsString);
	}


	/**
	 * 查询测试数据
	 *
	 * @return
	 * @since 2023/12/27
	 */
	private SysDict findTestData() throws Exception {


		FunctionRequest functionRequest = new FunctionRequest();
		functionRequest.setFunctionMethod("list");
		functionRequest.setFunctionTarget("sysDict");
		SysDict sysDict = new SysDict();
		sysDict.setType(TEXT_TYPE);
		functionRequest.setFunctionParams(Arrays.asList(

				JSON.toJSONString(sysDict)
		));
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/serverless.function/invoke")
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(functionRequest));//阿里巴巴的json序列化


		String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("result:{}", contentAsString);
		SysDict sysDict1 = JSON.parseObject(contentAsString).getObject("data", SysDict.class);

		return sysDict1;
	}

	@Test
	public void getOne() throws Exception {

		SysDict testData = findTestData();
		log.info(JSON.toJSONString(testData));
	}

	@Test
	public void update() throws Exception {
		SysDict testData = findTestData();


		FunctionRequest functionRequest = new FunctionRequest();
		functionRequest.setFunctionMethod("update");
		functionRequest.setFunctionTarget("sysDict");
		SysDict sysDict = new SysDict();
		sysDict.setType(TEXT_TYPE);
		sysDict.setDescription("测试2");
		sysDict.setId(testData.getId());
		functionRequest.setFunctionParams(Arrays.asList(

				JSON.toJSONString(sysDict)
		));
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/serverless.function/invoke")
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(functionRequest));//阿里巴巴的json序列化


		String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("result:{}", contentAsString);
	}

	/**
	 * 详情测试
	 *
	 * @return
	 * @since 2023/12/28
	 */
	@Test
	public void info() throws Exception {

		SysDict testData = findTestData();
		FunctionRequest functionRequest = new FunctionRequest();
		functionRequest.setFunctionMethod("info");
		functionRequest.setFunctionTarget("sysDict");

		functionRequest.setFunctionParams(Arrays.asList(

				testData.getId().toString()
		));
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post("/serverless.function/invoke")
						.contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(functionRequest));//阿里巴巴的json序列化


		String contentAsString = mvc.perform(requestBuilder).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		log.info("result:{}", contentAsString);
	}
}