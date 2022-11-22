# MyBatis Helper

[![IDEA 2022.1](https://img.shields.io/badge/IDEA-2022.1-yellowgreen.svg)](https://plugins.jetbrains.com/plugin/19649-mybatis-helper)
[![Apache-2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

Based on <https://gitee.com/wuzhizhan/free-mybatis-plugin>, support IDEA 2022.1.

[中文](README.md) | [ENGLISH](README_EN.md)

## Description

A idea plugin for mybatis .
Mybatis Helper is an enchange plugin for idea to supoort mybatis,here is the main functions:

- generate mapper xml files
- navigate from the code to mapper and from the mapper back to code
- auto code and error tips 

## How To Use

### Generating and jumping with ALT+ENTER and CTRL+B

- generate mapper files
  - ALT+ENTER<br/>
    ![](https://foruda.gitee.com/images/1659794512679996483/create_mapper.png)
    ![](https://foruda.gitee.com/images/1659794493510553804/choose_mapper_folder.png)
- generate statement
  - ALT+ENTER<br/>
    ![](https://foruda.gitee.com/images/1659794455321493024/create_statement.png)
- from dao to  mapper(can also use CTRL+B)<br/>
  ![](https://foruda.gitee.com/images/1659794427237042999/to_mapper.png)
- from mapper to dao(can also use CTRL+B)<br/>
  ![](https://foruda.gitee.com/images/1659794396599082459/to_code.png)

### MyBatis generator gui usage

- database configuration<br/>
  ![](https://foruda.gitee.com/images/1659794364318681565/nybatis_generator_connect.png)
  ![](https://foruda.gitee.com/images/1659794325498455693/nybatis_generator_connect_setting.png)
- select one or more tables, right click and select MyBatis-Generator to open generatoe main UI.<br>
  ![](https://foruda.gitee.com/images/1659794275156953254/nybatis_generator_operate.png)
- MyBatis generator configuration<br/>
  ![](https://foruda.gitee.com/images/1659794169707582082/nybatis_generator_setting.png)
- notice：If your database is MySQL 8，please add '?serverTimezone=GMT' and select MySQL 8 option<br>

## Reference

- better-mybatis-generator https://github.com/kmaster/better-mybatis-generator
- mybatis-generator-gui https://github.com/zouzg/mybatis-generator-gui
- MyBatisCodeHelper-Pro https://github.com/gejun123456/MyBatisCodeHelper-Pro