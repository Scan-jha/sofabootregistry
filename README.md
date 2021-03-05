云平台引入SofaRegistry的开发文档
1、服务器搭建
将文件夹下的dareway-sofaregistry的registry-integration.tgz上传到服务器上并进行解压
在相关目录下进行解压：tar -zxvf registry-integration.tgz
 
在和bin同目录下新建文件夹logs目录：
 
启动sofaregistry需要占用三个端口号：9615、9622、9603，查看三个端口是否被占用。
若端口被占用，可以修改conf下的配置文件application.properties进行相关的配置，参考application.properties.example进行修改。
修改端口过程中请注意修改以下路径：
 
启动命令：sh bin/startup.sh
查看运行状态：
可访问三个角色提供的健康监测 API，或查看日志 logs/registry-startup.log：
# 查看meta角色的健康检测接口：
$ curl http://localhost:9615/health/check
{"success":true,"message":"... raftStatus:Leader"}

# 查看data角色的健康检测接口：
$ curl http://localhost:9622/health/check
{"success":true,"message":"... status:WORKING"}

# 查看session角色的健康检测接口：
$ curl http://localhost:9603/health/check
{"success":true,"message":"..."}

2、修改maven的setting.xml文件
1）将dareway-sofaregistry下的setting.xml文件复制到你的本地中，注意setting.xml的本地仓库路径需要自己构建。
2）在你的setting.xml文件中添加一下代码：
<profiles>
<profile>
    <id>default</id>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <repositories>
        <repository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>maven-snapshot</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <id>maven-snapshot</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </pluginRepository>
    </pluginRepositories>
</profile>
3、服务端开发
在ECase中下载demo工程作为服务端
 
将demo导入开发坏境中。
（1）	修改setting.xml的路径
（2）	修改pom.xml文件
添加的pom.xml依赖有：
<parent>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>sofaboot-dependencies</artifactId>
    <version>3.3.2</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>

<!--sofa依赖-->
<!--rpc-->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>rpc-sofa-boot-starter</artifactId>
</dependency>
<!--SOFARegistry 依赖-->
<dependency>
    <groupId>com.alipay.sofa</groupId>
    <artifactId>registry-client-all</artifactId>
    <version>5.2.0</version>
</dependency>
（3）	修改配置文件
添加以下信息：
#sofa配置
logging.level.*=INFO  #启动时打印出log是info的信息
com.alipay.sofa.rpc.bolt.port=22200   #远程调用端口号
spring.output.ansi.enabled=ALWAYS   #日志之间进行隔离
logging.path=./logs   #生成日志的文件名
com.alipay.sofa.rpc.registry-address=sofa://10.1.50.135:9603  #注册中心地址
（4）	发布服务
定义服务API和实现API类，并在实现类上添加相关注解。
 
启动工程，查看日志，服务是否注册成功。
4、调用端开发
同样的在ECase中下载demo作为调用端开发的工程项目，将demo导入开发中进行的（1）、（2）、（3）的操作步骤和服务端开发一样。
（5）	引用服务
同样定义和服务端相同服务API接口类，在controller中进行服务的引用。
 
引用服务添加的注解：
@SofaReference(
        binding=@SofaReferenceBinding(
        bindingType="bolt",
        methodInfos={
                @SofaMethod(name="getSofa",timeout=30),
                @SofaMethod(name="getJh",timeout=50)}))
private  ISofaTestService iSofaTestService;

之后，启动工程，访问浏览器：
 
