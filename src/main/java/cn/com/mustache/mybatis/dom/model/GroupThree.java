package cn.com.mustache.mybatis.dom.model;

import java.util.List;

import com.intellij.util.xml.SubTagList;

/**
 * @author Steven Han
 */
public interface GroupThree extends GroupTwo {

    @SubTagList("selectKey")
    List<SelectKey> getSelectKey();

}
