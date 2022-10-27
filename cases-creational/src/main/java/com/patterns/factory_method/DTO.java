package com.patterns.factory_method;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO
 *
 * @author coder
 * @date 2022-07-11 15:51:39
 * @since 1.0.0
 */
@XmlRootElement(name = "object")
@XmlAccessorType(XmlAccessType.FIELD)
public class DTO {
    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DTO{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}