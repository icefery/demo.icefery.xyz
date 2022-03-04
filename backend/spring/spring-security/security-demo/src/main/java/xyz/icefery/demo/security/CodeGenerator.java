package xyz.icefery.demo.security;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {

    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig()
                .setOutputDir(projectPath + "/src/main/java/")
                .setFileOverride(false)
                .setOpen(false)
                .setAuthor("icefery")
                .setIdType(IdType.AUTO)
                .setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/security_demo")
                .setUsername("root")
                .setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig()
//                .setParent("xyz.icefery.demo.security");
        .setParent("generator");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig sg = new StrategyConfig()
                .setTablePrefix("sys_")
                .setInclude("sys_user",
                            "sys_role",
                            "sys_permission",
                            "sys_role_user",
                            "sys_role_permission")
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setChainModel(true)
                .setEntityLombokModel(true);
        mpg.setStrategy(sg);

        mpg.execute();
    }
}
