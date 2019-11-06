package com.mapbar.lite.core;

import com.mapbar.lite.embed.EmbedZookeeperServer;
import com.mapbar.lite.job.MyDataflowJob;
import com.mapbar.lite.job.MySimpleJob;
import io.elasticjob.lite.api.JobScheduler;
import io.elasticjob.lite.config.JobCoreConfiguration;
import io.elasticjob.lite.config.LiteJobConfiguration;
import io.elasticjob.lite.config.dataflow.DataflowJobConfiguration;
import io.elasticjob.lite.config.script.ScriptJobConfiguration;
import io.elasticjob.lite.config.simple.SimpleJobConfiguration;
import io.elasticjob.lite.event.JobEventConfiguration;
import io.elasticjob.lite.event.rdb.JobEventRdbConfiguration;
import io.elasticjob.lite.reg.base.CoordinatorRegistryCenter;
import io.elasticjob.lite.reg.zookeeper.ZookeeperConfiguration;
import io.elasticjob.lite.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/16 15:41
 * @Description: 测试simple job的创建
 */
public class JavaJobTest {

    /**
     * zk相关配置
     */
    private static final int EMBED_ZOOKEEPER_PORT = 4181;
    private static final String ZOOKEEPER_CONNECTION_STRING = "localhost:" + EMBED_ZOOKEEPER_PORT;
    private static final String JOB_NAMESPACE = "elastic-job-example";

    /**
     * 持久化相关配置
     */
    private static final String EVENT_RDB_STORAGE_DRIVER = "org.h2.Driver";
    private static final String EVENT_RDB_STORAGE_URL = "jdbc:h2:mem:job_event_storage";
    private static final String EVENT_RDB_STORAGE_USERNAME = "sa";
    private static final String EVENT_RDB_STORAGE_PASSWORD = "";

    private static CoordinatorRegistryCenter setUpRegistryCenter() {
        ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(ZOOKEEPER_CONNECTION_STRING, JOB_NAMESPACE);
        CoordinatorRegistryCenter result = new ZookeeperRegistryCenter(zkConfig);
        result.init();
        return result;
    }

    /**
     * rdb
     * @return
     */
    private static DataSource setUpEventTraceDataSource() {
        BasicDataSource result = new BasicDataSource();
        result.setDriverClassName(EVENT_RDB_STORAGE_DRIVER);
        result.setUrl(EVENT_RDB_STORAGE_URL);
        result.setUsername(EVENT_RDB_STORAGE_USERNAME);
        result.setPassword(EVENT_RDB_STORAGE_PASSWORD);
        return result;
    }

    public static void main(String[] args) {
        // 使用内嵌zk，模拟启动zk
        EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
        CoordinatorRegistryCenter regCenter = setUpRegistryCenter();
        JobEventConfiguration jobEventConfig = new JobEventRdbConfiguration(setUpEventTraceDataSource());
        createSimpleJob(regCenter, jobEventConfig);
//        createDataFlowJob(regCenter, jobEventConfig);
//        createScriptJob(regCenter, jobEventConfig);
    }

    /**
     * 创建一个简单分布式作业
     * @param regCenter         注册中心
     * @param jobEventConfig    作业事件配置
     */
    private static void createSimpleJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder("javaSimpleJob", "0/5 * * * * ?", 3)
                .shardingItemParameters("0=beijing,1=shanghai,2=guangzhou").build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, MySimpleJob.class.getCanonicalName());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build(), jobEventConfig).init();
    }

    /**
     * 创建一个数据流分布式作业
     * @param regCenter         注册中心
     * @param jobEventConfig    作业事件配置
     */
    private static void createDataFlowJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder("javaDataFlowJob", "0/2 * * * * ?", 3)
                .shardingItemParameters("0=beijing,1=shanghai,2=guangzhou").build();
        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(coreConfig, MyDataflowJob.class.getCanonicalName(), true);
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(dataflowJobConfiguration).build(), jobEventConfig).init();
    }

    /**
     * 创建一个脚本分布式作业
     * @param regCenter         注册中心
     * @param jobEventConfig    作业事件配置
     */
    private static void createScriptJob(final CoordinatorRegistryCenter regCenter, final JobEventConfiguration jobEventConfig) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration
                .newBuilder("javaScriptJob", "0/2 * * * * ?", 3)
                .shardingItemParameters("0=beijing,1=shanghai,2=guangzhou").build();
        ScriptJobConfiguration scriptJobConfiguration = new ScriptJobConfiguration(coreConfig, buildScriptCommandLine());
        new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(scriptJobConfiguration).build(), jobEventConfig).init();
    }

    private static final String OS_NAME = "os.name";
    private static final String WINDOWS = "Windows";
    private static final String SCRIPT_PATH_WIN = "/script/demo.bat";
    private static final String SCRIPT_PATH_UNIX = "/script/demo.sh";
    private static final String FILE_PERMISSION_RWX = "rwxr-xr-x";


    private static String buildScriptCommandLine() {
        if (System.getProperties().getProperty(OS_NAME).contains(WINDOWS)) {
            return Paths.get(JavaJobTest.class.getResource(SCRIPT_PATH_WIN).getPath().substring(1)).toString();
        }
        Path result = Paths.get(JavaJobTest.class.getResource(SCRIPT_PATH_UNIX).getPath());
        try {
            Files.setPosixFilePermissions(result, PosixFilePermissions.fromString(FILE_PERMISSION_RWX));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
