# [encrypt-spring-boot-starter](https://github.com/13186150814/encrypt-spring-boot-starter)

# 快速入门

## 简介

&emsp;&emsp; [encrypt-spring-boot-starter](https://github.com/13186150814/encrypt-spring-boot-starter) 框架提供 springboot 后端服务与前端之间 http 请求响应数据的加密传递，目前支持 AES 和国密 SM4 加密解密功能，使用非常方便，只需要在 Controller 方法上加上注解就可以完成接口数据的加密和解密操作。

> 注意：解密只能用于@RequestBody 修饰的的JSON传参方法，加密可以应用于所有的响应JSON数据的接口。

## 安装

&emsp;&emsp;encrypt-spring-boot-starter 框架基于 JDK 1.8，所以安装集成要求如下：

- JDK 1.8+
- Maven

### Release

---

### Spring Boot

Maven

Step 1. Add the JitPack repository to your build file

```
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

```
Step 2. Add the dependency

```
<dependency>
    <groupId>com.github.13186150814</groupId>
    <artifactId>encrypt-spring-boot-starter</artifactId>
    <version>latest-tag</version>
</dependency>

```

## 配置

encrypt-spring-boot-starter 的配置异常的简单，仅需要一些简单的配置即可使用 encrypt-spring-boot-starter 的强大功能！
> 在讲解配置之前，请确保您已经安装了 encrypt-spring-boot-starter，如果您尚未安装，请查看 安装 一章

### Spring Boot 工程
- 配置 @EnableEncryptBody 注解

```
@EnableEncryptBody
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}


```
> @EnableEncryptBody 是开启加密解密功能的注解，必须添加。

### application.yml 配置

```
spring:
  encrypt:
    key: 33035f4976800a78fc5187f6f8f4f914

```
> 此处是配置加密的秘钥（是一串32个字符的16进制字符串），需要和前端协商保持一致，不配置默认生成key并在info日志中打印。
> 此方法可以生成随机秘钥key：System.out.println(HexUtil.encodeHexStr(KeyUtil.generateKey(SM4.ALGORITHM_NAME).getEncoded()));


### 其他

> 下文示例代码中的 CommonResult 对象是自定义的公共返回对象（框架不提供此类），一般web项目后台都会自定义公共返回对象，而公共返回对象基本都有 code、msg、data三个属性，其中 code 和 msg 不涉密，所以框架会首先在返回对象中查找data属性，找到了就只加密data属性值，如果没找到就加密返回对象自身。


## 注解

本文将介绍 encrypt-spring-boot-starter 注解包相关类详解（更多详细描述可点击查看源码注释）

[@EnableEncryptBody](https://github.com/13186150814/encrypt-spring-boot-starter/tree/master/src/main/java/com/parkerchang/encrypt/annotation/EnableEncryptBody.java)

---
- 描述：加密解密功能开启注解。
- 使用位置:Spring Boot项目的启动类上（其他配置类也可以）。

> **配置章节有该注解使用示例**。


[@Decrypt](https://github.com/13186150814/encrypt-spring-boot-starter/tree/master/src/main/java/com/parkerchang/encrypt/annotation/Decrypt.java)

---
- 描述：解密注解，标识需要解密的接口方法。
- 使用位置:需要解密的Controller方法上，或者是方法参数对象上都可以。

```
@PostMapping("decrypt")
@Decrypt
public CommonResult<UserDTO> decrypt(@RequestBody UserDTO userDTO){
    return CommonResult.success(userDTO);
}
```
> 前端调用接口传入的是 UserDTO 对象的加密字符串，给方法加上 @Decrypt 注解后就会自动将传入的加密字符串解析为 UserDTO 对象，方法里面就可以正常使用 UserDTO 对象。


| 属性 | 类型 | 必须指定 | 默认值         | 描述                |
| ----- | ---- | -------- | ----------------- | --------------------- |
| value | Enum | 否      | EncryptMethod.SM4 | 数据解密方式，默认SM4 |

[@Encrypt](https://github.com/13186150814/encrypt-spring-boot-starter/tree/master/src/main/java/com/parkerchang/encrypt/annotation/Encrypt.java)

---
- 描述：加密注解，标识需要加密的接口方法。
- 使用位置:需要加密的Controller方法上。

```
@GetMapping("encrypt")
@Encrypt
public CommonResult<UserDTO> encrypt(){
    UserDTO dto = new UserDTO();
    dto.setId("id");
    dto.setName("name");
    return CommonResult.success(UserDTO);
}
```
> 只需要将 @Encrypt 注解加在需要加密的接口上（ @ResponseBody 修饰的接口即返回JSON数据的接口）公共返回对象 CommonResult （ CommonResult 类只是示例，实际使用中替换为自己项目的公共返回对象）中的data数据就会被加密。

| 属性 | 类型 | 必须指定 | 默认值         | 描述                |
| ----- | ---- | -------- | ----------------- | --------------------- |
| value | Enum | 否      | EncryptMethod.SM4 | 数据加密方式，默认SM4 |




