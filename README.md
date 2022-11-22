# MyBatis Helper

[![IDEA 2022.1](https://img.shields.io/badge/IDEA-2022.1-yellowgreen.svg)](https://plugins.jetbrains.com/plugin/19649-mybatis-helper)
[![Apache-2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

基于<https://gitee.com/wuzhizhan/free-mybatis-plugin>升级，支持IDEA 2022.1版本且提供长期支持。

[中文](README.md) | [ENGLISH](README_EN.md)

## Description

- A idea plugin for mybatis . 

- Mybatis Helper 是一款增强idea对mybatis支持的插件，主要功能如下：
  - 生成mapper xml文件
  - 快速从代码跳转到mapper及从mapper返回代码
  - mybatis自动补全及语法错误提示

## 使用方法

> - Mybatis Helper 是一个提高mybatis编码的插件。
>
> - 实现了dao代码跳转到mapper，mapper跳转回dao，mapper文件、statement查询自动生成功能。

### 灵活使用 ALT+ENTER 和 CTRL+B 实现提示和跳转

- 生成mapper文件
  - ALT+ENTER弹出<br/>
    ![](https://foruda.gitee.com/images/1659794512679996483/create_mapper.png)
    ![](https://foruda.gitee.com/images/1659794493510553804/choose_mapper_folder.png)
- 生成statement语句
  - ALT+ENTER弹出<br/>
    ![](https://foruda.gitee.com/images/1659794455321493024/create_statement.png)
- dao跳转到mapper（也快捷键CTRL+B跳入）<br/>
  ![](https://foruda.gitee.com/images/1659794427237042999/to_mapper.png)
- mapper跳转回dao（也可快捷键CTRL+B跳入)<br/>
  ![](https://foruda.gitee.com/images/1659794396599082459/to_code.png)

### MyBatis generator gui使用方法

- 配置数据库<br/>
  - 选择数据库类型
	- ![](https://foruda.gitee.com/images/1659794364318681565/nybatis_generator_connect.png)
	- 配置数据库连接信息 
  - ![](https://foruda.gitee.com/images/1659794325498455693/nybatis_generator_connect_setting.png)
- 在需要生成代码的表上右键，选择MyBatis-Generator，打开配置界面。<br/>
  ![](https://foruda.gitee.com/images/1659794275156953254/nybatis_generator_operate.png)
- 配置生成参数<br/>
  ![](https://foruda.gitee.com/images/1659794169707582082/nybatis_generator_setting.png)
- 注意：当数据库用MySQL 8，在URL上定义时区，推荐使用'?serverTimezone=GMT'，配置中勾选上MySQL 8选项。<br>


## 参考

- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator
- mybatis-generator-gui https://github.com/zouzg/mybatis-generator-gui
- MyBatisCodeHelper-Pro https://github.com/gejun123456/MyBatisCodeHelper-Pro