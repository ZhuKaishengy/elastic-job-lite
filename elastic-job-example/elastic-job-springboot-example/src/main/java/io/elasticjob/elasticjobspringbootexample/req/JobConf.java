package io.elasticjob.elasticjobspringbootexample.req;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhukaishengy
 * @Date: 2019/10/17 18:41
 * @Description: job配置的封装，用于api调用
 */
@Data
@Accessors(chain = true)
public class JobConf {
    /**
     * 作业名称
     */
    @NotNull(message = "不能为空")
    private String jobName;

    /**
     * 作业类型（SIMPLE，DATAFLOW，SCRIPT）
     * {@link io.elasticjob.lite.api.JobType}
     */
    private String jobType;

    /**
     * 任务类路径
     */
    @NotNull(message = "不能为空")
    private String jobClass;

    /**
     * 调度类型（CRON，SIMPLE）
     * {@link io.elasticjob.lite.config.ScheduleTypeEnum}
     */
    @NotNull(message = "不能为空")
    private String scheduleType;

    /**
     * cron表达式，用于控制作业触发时间
     */
    private String cron;

    /**
     * 多长时间后开始第一次执行任务，单位为秒, 如果小于等于0，则立即启动任务
     */
    private int delay;

    /**
     * 重试时间间隔,单位为秒,为0代表不重试
     */
    private int period;

    /**
     * 除第一次执行外的重试次数，如果为0则为无限次重试
     */
    private int times;

    /**
     * 作业分片总数
     */
    private int shardingTotalCount = 1;

    /**
     * 分片序列号和参数用等号分隔，多个键值对用逗号分隔
     * <p>分片序列号从0开始，不可大于或等于作业分片总数<p>
     * <p>如：0=a,1=b,2=c<p>
     */
    private String shardingItemParameters = "";

    /**
     * 作业自定义参数
     * <p>作业自定义参数，可通过传递该参数为作业调度的业务方法传参，用于实现带参数的作业<p>
     * <p>例：每次获取的数据量、作业实例从数据库读取的主键等<p>
     */
    private String jobParameter = "";

    /**
     * 是否开启任务执行失效转移，开启表示如果作业在一次任务执行中途宕机，允许将该次未完成的任务在另一作业节点上补偿执行
     */
    private boolean failover = true;

    /**
     * 是否开启错过任务重新执行
     */
    private boolean misfire = false;

    /**
     * 作业描述信息
     */
    private String description = "";

    /**
     * 设置使用本地作业配置覆盖注册中心的作业配置。
     */
    private boolean overwrite = false;

    /**
     * 是否流式处理数据
     * <p>如果流式处理数据, 则fetchData不返回空结果将持续执行作业<p>
     * <p>如果非流式处理数据, 则处理数据完成后作业结束<p>
     */
    private boolean streamingProcess = false;

    /**
     * 脚本型作业执行命令行
     */
    private String scriptCommandLine = "";

    /**
     * 监控作业运行时状态
     * <p>每次作业执行时间和间隔时间均非常短的情况，建议不监控作业运行时状态以提升效率。<p>
     * <p>因为是瞬时状态，所以无必要监控。请用户自行增加数据堆积监控。并且不能保证数据重复选取，应在作业中实现幂等性。<p>
     * <p>每次作业执行时间和间隔时间均较长的情况，建议监控作业运行时状态，可保证数据不会重复选取。<p>
     */
    private boolean monitorExecution = true;

    /**
     * 作业监控端口
     * <p>建议配置作业监控端口, 方便开发者dump作业信息。<p>
     * <p>使用方法: echo “dump” | nc 127.0.0.1 9888<p>
     */
    private int monitorPort = -1;

    /**
     * 大允许的本机与注册中心的时间误差秒数
     * <p>如果时间误差超过配置秒数则作业启动时将抛异常<p>
     * <p>配置为-1表示不校验时间误差<p>
     */
    private int maxTimeDiffSeconds = -1;

    /**
     * 作业分片策略实现类全路径,默认使用平均分配策略
     */
    private String jobShardingStrategyClass = "";

    /**
     * 修复作业服务器不一致状态服务调度间隔时间，配置为小于1的任意值表示不执行修复,单位：分钟
     */
    private int reconcileIntervalMinutes = 10;

    /**
     * 作业事件追踪的数据源Bean引用
     */
    private String eventTraceRdbDataSource = "";

    /**
     * 前置后置任务监听实现类
     * <p>
     *     需实现ElasticJobListener接口，每个任务分片前后都会执行
     * </p>
     */
    private String listener = "";

    /**
     * 作业是否禁止启动，可用于部署作业时，先禁止启动，部署结束后统一启动
     */
    private boolean disabled = false;

    /**
     * 前置后置任务分布式监听实现类
     * <p>
     *     需继承AbstractDistributeOnceElasticJobListener类，一个任务只会执行一次，在分片开始前和结束后
     * </p>
     */
    private String distributedListener = "";

    /**
     * 最后一个作业执行前的执行方法的超时时间,单位：毫秒
     */
    private long startedTimeoutMilliseconds = Long.MAX_VALUE;

    /**
     * 最后一个作业执行后的执行方法的超时时间,单位：毫秒
     */
    private long completedTimeoutMilliseconds = Long.MAX_VALUE;

    /**
     * 扩展属性
     * <p>
     *     可以自定义异常处理类和quartz worker线程池的配置类，
     *     不传此参数，使用默认的。
     * </p>
     */
    private JobProperties jobProperties = new JobProperties();
}
