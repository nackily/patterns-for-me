package com.aoligei.creational.builder.variant;

/**
 * 男性朋友
 *
 * @author xg-ran
 * @date 2022-05-31 15:11:30
 * @since 1.0.0
 */
public class MaleFriend {

    private String name;                    // 姓名
    private boolean ownHouse;               // 有房否
    private boolean ownCar;                 // 有车否
    private String satisfactionLevel;       // 满意程度

    public String getName() {
        return name;
    }

    public boolean isOwnHouse() {
        return ownHouse;
    }

    public boolean isOwnCar() {
        return ownCar;
    }

    public String getSatisfactionLevel() {
        return satisfactionLevel;
    }

    public MaleFriend(Builder builder) {
        this.name = builder.name;
        this.ownHouse = builder.ownHouse;
        this.ownCar = builder.ownCar;

        if (builder.ownCar && builder.ownHouse) {
            this.satisfactionLevel = "满意";
        } else if ((!builder.ownCar) && (!builder.ownHouse)){
            this.satisfactionLevel = "不满意";
        } else {
            this.satisfactionLevel = "一般";
        }
    }

    public static class Builder {

        private String name;
        private boolean ownHouse = false;
        private boolean ownCar = false;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ownHouse(boolean ownHouse) {
            this.ownHouse = ownHouse;
            return this;
        }

        public Builder ownCar(boolean ownCar) {
            this.ownCar = ownCar;
            return this;
        }

        public MaleFriend build() {
            return new MaleFriend(this);
        }
    }
}
