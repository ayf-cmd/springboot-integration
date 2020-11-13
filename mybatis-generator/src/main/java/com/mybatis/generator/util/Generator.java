package com.mybatis.generator.util;

import com.mybatis.generator.entity.BasisInfo;
import com.mybatis.generator.entity.JsonResult;
import com.mybatis.generator.entity.PropertyInfo;

import java.util.List;

public class Generator {
    // 路径信息
    public static final String ENTITY = "entity";
    public static final String VO = "vo";
    public static final String DTO = "dto";
    public static final String DAO = "dao";
    public static final String DAO_IMPL = "daoImpl";
    public static final String SERVICE = "service";
    public static final String SERVICE_IMPL = "serviceImpl";
    public static final String CONTROLLER = "controller";
    public static final String API = "api";
    public static final String API_IMPL = "apiImpl";
    public static final String LOGIC_DTO = "logicDto";
    public static final String QUERY_VO = "queryVo";
    public static final String UPDATE_VO = "updateVo";

    // ①创建实体类
    public static JsonResult createEntity(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), ENTITY);
        return FreemarkerUtil.createFile(bi, "entity.ftl", fileUrl);
    }

    // ①创建VO类
    public static JsonResult createVo(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), VO);
        return FreemarkerUtil.createFile(bi, "vo.ftl", fileUrl);
    }

    // ①创建DTO类
    public static JsonResult createDto(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), DTO);
        return FreemarkerUtil.createFile(bi, "dto.ftl", fileUrl);
    }

    // ②创建DAO
    public static JsonResult createDao(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), DAO);
        return FreemarkerUtil.createFile(bi, "dao.ftl", fileUrl);
    }

    // ③创建mapper配置文件
    public static JsonResult createDaoImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), DAO_IMPL);
        List<PropertyInfo> list = bi.getCis();
        String agile = "";
        for (PropertyInfo propertyInfo : list) {
            agile = agile + propertyInfo.getColumn() + ", ";
        }
        agile = agile.substring(0, agile.length() - 2);
        bi.setAgile(agile);
        return FreemarkerUtil.createFile(bi, "mapper.ftl", fileUrl);
    }

    // ④创建SERVICE
    public static JsonResult createService(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), SERVICE);
        return FreemarkerUtil.createFile(bi, "service.ftl", fileUrl);
    }

    // ⑤创建SERVICE_IMPL
    public static JsonResult createServiceImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), SERVICE_IMPL);
        return FreemarkerUtil.createFile(bi, "serviceImpl.ftl", fileUrl);
    }
    
    // ④创建API
    public static JsonResult createApi(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), API);
        return FreemarkerUtil.createFile(bi, "api.ftl", fileUrl);
    }
    
    // ⑤创建API_IMPL
    public static JsonResult createApiImpl(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), API_IMPL);
        return FreemarkerUtil.createFile(bi, "apiImpl.ftl", fileUrl);
    }

    // ⑥创建CONTROLLER
    public static JsonResult createController(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), CONTROLLER);
        return FreemarkerUtil.createFile(bi, "controller.ftl", fileUrl);
    }
    
    // ⑥创建logicDto
    public static JsonResult createLogicDto(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), LOGIC_DTO);
        return FreemarkerUtil.createFile(bi, "logicDto.ftl", fileUrl);
    }
    
    // ⑥创建queryVo
    public static JsonResult createQueryVo(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), QUERY_VO);
        return FreemarkerUtil.createFile(bi, "queryVo.ftl", fileUrl);
    }
    
    // ⑥创建updateVo
    public static JsonResult createUpdateVo(String url, BasisInfo bi) {
        String fileUrl = getGeneratorFileUrl(url, bi.getEntityName(), UPDATE_VO);
        return FreemarkerUtil.createFile(bi, "updateVo.ftl", fileUrl);
    }
    
    // 生成文件
    public static String getGeneratorFileUrl(String url, String entityName, String type) {
        if (type.equals("entity")) {
            return url + entityName + ".java";
        } else if (type.equals("vo")) {
            return url + entityName + "Vo.java";
        } else if (type.equals("queryVo")) {
            return url + entityName + "QueryVo.java";
        } else if (type.equals("dto")) {
            return url + entityName + "Dto.java";
        } else if (type.equals("dao")) {
            return url + entityName + "Mapper.java";
        } else if (type.equals("daoImpl")) {
            return url + entityName + "Mapper.xml";
        } else if (type.equals("service")) {
            return url + entityName + "Service.java";
        } else if (type.equals("serviceImpl")) {
            return url + entityName + "ServiceImpl.java";
        } else if (type.equals("controller")) {
            return url + entityName + "Controller.java";
        } else if (type.equals("api")){
            return url + entityName + "Api.java";
		} else if(type.equals("apiImpl")){
            return url + entityName + "ApiImpl.java";
		} else if(type.equals("logicDto")) {
            return url + entityName + "LogicDto.java";
		} else if (type.equals("updateVo")) {
            return url + entityName + "UpdateVo.java";
        }
        return null;
    }

}
