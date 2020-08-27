package com.swagger.controller;

import com.swagger.entity.ReqEntity;
import com.swagger.entity.ResponseEntity;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
/**
 * produces : For example, "application/json, application/xml"  , 可选
 * consumes : For example, "application/json, application/xml"  , 可选
 * tags     : 类接口所属业务[正常应与此类下的接口方法配置一致],接口文档会依据tags进行分组展示 , 必填
 */
@Api(produces = "application/json", consumes = "application/json", tags = "订单服务")
public class SwaggerWeb {
    private static final String TAGS = "订单服务";

    /**
     * 订单新增 : 传递实体Bean通过@ApiParam进行注解
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "订单新增", notes = "订单新增:新增订单接口", hidden = false, response = ResponseEntity.class)
    @PostMapping(value = "/order")
    /**
     * @ApiParam 作用与实体Bean, 无需配置name和value.会依据实体Bean配置进行注解.
     * hidden : 是否隐藏参数
     * required : 是否必填
     */
    public ResponseEntity add(@ApiParam(hidden = false, required = false) @RequestBody @Valid ReqEntity reqEntity) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(reqEntity.toString()));
    }

    /**
     * 订单删除 : 传递非实体Bean使用@ApiImplicitParams进行注解
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "订单删除", notes = "订单删除:通过自增ID删除订单.", hidden = false, response = ResponseEntity.class)
    @ApiImplicitParams({
            /**
             * 注解说明 : 参考订单查询
             */
            @ApiImplicitParam(name = "id", value = "自增ID", example = "1", required = true)
    })
    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(id));
    }

    /**
     * 订单修改 : 传递实体Bean与Path
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "订单修改", notes = "订单修改:通过自增ID进行修改", hidden = false, response = ResponseEntity.class)
    @ApiImplicitParams({
            /**
             * 注解说明 : 参考订单查询
             */
            @ApiImplicitParam(name = "id", value = "自增ID", example = "1", required = true)
    })
    @PostMapping(value = "/order/{id}")
    /**
     * @ApiParam 作用与实体Bean, 无需配置name和value.会依据实体Bean配置进行注解.
     * hidden : 是否隐藏参数
     * required : 是否必填
     */
    public ResponseEntity update(@PathVariable("id") String id, @ApiParam(hidden = false, required = false) @RequestBody @Valid ReqEntity reqEntity) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(id, reqEntity.toString()));
    }

    /**
     * 订单查询 : 传递非实体Bean使用@ApiImplicitParams进行注解
     * tags : 接口所属业务组,接口文档会依据tags进行分组展示 , 如果与类的tags一致,可不填
     * value : 接口名称 , 必填
     * notes : 接口描述 , 可选
     * hidden : 是否隐藏,默认false , 可选
     * response : 返回的对象 , 必填
     */
    @ApiOperation(tags = TAGS, value = "订单查询", notes = "订单查询:通过订单编号和订单名称进行查询", hidden = false, response = ResponseEntity.class)
    @ApiImplicitParams({
            /**
             * name : 参数名称[需与方法中名称一致] , 必填
             * vale : 参数说明 , 必填
             * required : 是否必填 , 可选
             * example : 样例 , 可选
             */
            @ApiImplicitParam(name = "orderNo", value = "订单编号", example = "DDBH1130730655556190208", required = false),
            @ApiImplicitParam(name = "orderName", value = "订单名称", example = "销售单")
    })
    @GetMapping(value = "/order")
    public ResponseEntity get(String orderNo, String orderName) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(orderNo, orderName));
    }

    /**
     * 文件上传
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "文件上传", notes = "文件上传[选择文件进行上传]", hidden = false, response = ResponseEntity.class)
    @ApiImplicitParams({
            /**
             *  注解说明 : 参考订单查询
             */
            @ApiImplicitParam(name = "col1", value = "字段1", example = "c1", required = true)
    })
    @PostMapping(value = "/file")
    public Object file(String col1, @ApiParam(value = "上传的文件", required = true) MultipartFile file
    ) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(col1, file.getOriginalFilename()));
    }

    /**
     * 传递list参数
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "list传参", notes = "传递List参数", hidden = false, response = ResponseEntity.class)
    @PostMapping(value = "/list")
    public Object list(@ApiParam(value = "订单列表", required = false) @RequestBody List<Integer> ids) {
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(ids.toString()));
    }

    /**
     * 传递list实体类参数
     * 注解说明 : 参考订单查询
     */
    @ApiOperation(tags = TAGS, value = "list实体类传参", notes = "传递List实体类参数", hidden = false, response = ResponseEntity.class)
    @PostMapping(value = "/listBean")
    public Object listBean(@ApiParam(value = "实体Bean列表", required = false) @RequestBody List<ReqEntity> ids) {
        System.err.println(ids);
        return new ResponseEntity(200, "SUCCESS", Arrays.asList(ids.toString()));
    }

}




