package com.panlingxiao.xml.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by panlingxiao on 2016/7/13.
 */

/*
 *　使用@XmlRootElement可以修饰类或者枚举类型
 *  它代表XML文档的根元素
 */
@XmlRootElement
//可以定义元素的输出顺序



public class Country {

    private String countryName;
    private double countryPopulation;

    private ArrayList<State> listOfStates;


    public String getCountryName() {
        return countryName;
    }

    @XmlElement
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<State> getListOfStates() {
        return listOfStates;
    }

    @XmlElement
    public void setListOfStates(ArrayList<State> listOfStates) {
        this.listOfStates = listOfStates;
    }

    public double getCountryPopulation() {
        return countryPopulation;
    }

    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "stateList")
    // XmlElement sets the name of the entities in collection
    @XmlElement(name = "state")
    public void setCountryPopulation(double countryPopulation) {
        this.countryPopulation = countryPopulation;
    }

    static class State{
        private String stateName;
        long statePopulation;

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public long getStatePopulation() {
            return statePopulation;
        }

        public void setStatePopulation(long statePopulation) {
            this.statePopulation = statePopulation;
        }
    }
}
