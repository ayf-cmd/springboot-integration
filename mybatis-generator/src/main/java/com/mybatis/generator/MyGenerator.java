package com.mybatis.generator;

import com.mybatis.generator.entity.BasisInfo;
import com.mybatis.generator.entity.TableEntity;
import com.mybatis.generator.util.EntityInfoUtil;
import com.mybatis.generator.util.Generator;
import com.mybatis.generator.util.MySqlToJavaUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyGenerator {

    // 项目路径
    public static final String BASE_PATH = System.getProperty("user.dir");
    // 基础信息：项目名、作者、版本
    public static final String PROJECT = "mybatis-generator";
    public static final String PACKAGE = "com.generatorbiz";
    public static final String AUTHOR = "anyunfeng";
    public static final String VERSION = "V1.0";
    // 数据库连接信息：连接URL、用户名、秘密、数据库名
    public static final String URL = "jdbc:mysql://localhost:3306/fintell_credit_dev?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
    public static final String NAME = "root";
    public static final String PASS = "admin";
    public static final String DATABASE = "fintell_credit_dev";
    public static final String TIME = "2019年6月11日";
    public static final String AGILE = new Date().getTime() + "";

    public static void main(String[] args) {
    	List<TableEntity> tables = new ArrayList<>();
    	tables.add(new TableEntity("AuthBusiInfo", "auth_busi", "业务线配置", "auth"));

    	for(TableEntity entity : tables) {
	        BasisInfo bi = new BasisInfo(PROJECT, AUTHOR, VERSION, URL, NAME, PASS, DATABASE, TIME, AGILE, PACKAGE + "." + entity.getModule());
	        bi.setTable(entity.getTable());
	        bi.setSign(getSign(entity.getTable()));
	        bi.setSubPackage(entity.getTable().split("_")[1].toLowerCase());
	        bi.setSwaggerTags(entity.getClassComment() + "服务接口");
	        bi.setEntityName(MySqlToJavaUtil.getClassName(entity.getTable()));
	        bi.setObjectName(MySqlToJavaUtil.changeToJavaFiled(entity.getTable()));
	        bi.setEntityComment(entity.getClassComment());
	        try {
	            bi = EntityInfoUtil.getInfo(bi);
                String baseDir = BASE_PATH + File.separator + PROJECT +  "/src/main/java/" + PACKAGE.replace(".", "/") + File.separator + entity.getModule();
                System.err.println(Generator.createController(baseDir + "/controller/", bi).toString());
	            System.err.println(Generator.createDao(baseDir + "/dao" + File.separator, bi).toString());
	            System.err.println(Generator.createService(baseDir + "/service" + File.separator, bi).toString());
	            System.err.println(Generator.createServiceImpl(baseDir + "/service" + File.separator, bi).toString());
                System.err.println(Generator.createDaoImpl(baseDir + "/mapper" + File.separator, bi).toString());
                System.err.println(Generator.createEntity( baseDir + "/entity" + File.separator, bi).toString());
	            System.err.println(Generator.createDto(baseDir + "/dto" + File.separator, bi).toString());
	            System.err.println(Generator.createLogicDto(baseDir + "/dto" + File.separator, bi).toString());
	            System.err.println(Generator.createQueryVo(baseDir + "/vo" + File.separator, bi).toString());
	            System.err.println(Generator.createUpdateVo(baseDir + "/vo" + File.separator, bi).toString());
                System.err.println(Generator.createVo(baseDir + "/vo" + File.separator, bi).toString());

//                System.err.println(Generator.createApi(baseDir + "/api" + File.separator, bi).toString());
//                System.err.println(Generator.createApiImpl(baseDir + "/api" + File.separator, bi).toString());
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	}
    }

    /**
     * 获取表的区分字段
     * @param tableName 表名, eg: gen_test_demo
     * @return 区分字段 eg: test
     */
    private static String getSign(String tableName) {
        String[] split = tableName.split("_");
        if (split.length == 3) {
            return split[1];
        } else if (split.length == 4) {
            return split[1] + "_" + split[2];
        }
        return split[1];
    }

}
