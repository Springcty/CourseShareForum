//package com.software.blogserver;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//
//public class generator {
//    public static void main(String[] args) {
//        AutoGenerator autoGenerator = new AutoGenerator();
//        DataSourceConfig datasource = new DataSourceConfig();
//        datasource.setDriverName("com.mysql.cj.jdbc.Driver");
//        datasource.setUrl("jdbc:mysql://localhost:3306/blogs?serverTimezone=UTC");
//        datasource.setUsername("root");
//        datasource.setPassword("root");
//        autoGenerator.setDataSource(datasource);
//
//        GlobalConfig globalConfig = new GlobalConfig();
//        globalConfig.setOutputDir(System.getProperty("user.dir")+"/blog-server/src/main/java");
//        globalConfig.setOpen(false);
//        globalConfig.setAuthor("yzf");
//        globalConfig.setFileOverride(true);
//        globalConfig.setMapperName("%sDao");
//        globalConfig.setIdType(IdType.ASSIGN_ID);
//        autoGenerator.setGlobalConfig(globalConfig);
//
//        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setParent("com.software.blogserver");
//        autoGenerator.setPackageInfo(packageConfig);
//
//        //策略设置
//        StrategyConfig strategyConfig = new StrategyConfig();
////        strategyConfig.setInclude("Articles");
//        strategyConfig.setRestControllerStyle(true);
//        strategyConfig.setEntityLombokModel(true);
////        strategyConfig.setVersionFieldName("version");
////        strategyConfig.setLogicDeleteFieldName("deleted");
//        autoGenerator.setStrategy(strategyConfig);
//
//        autoGenerator.execute();
//
//    }
//}