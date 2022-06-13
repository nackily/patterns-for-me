package com.aoligei.creational.prototype;


/**
 * Main
 *
 * @author xg-ran
 * @date 2022-06-06 10:38:06
 * @since 1.0.0
 */
public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        classic();
//        useManager();
    }

    private static void classic() {
        // 实例化原型购房合同
        Contract sales = new SalesContract();
        // 实例化原型租售合同
        Contract lease = new LeaseContract();
        System.out.println("|==> 现有[张三]欲购置[李四]的房子-------------------------------------|");
        Contract clone4Sales = sales.clone();
        clone4Sales.signed("李四", "张三");
        System.out.println("|==> 现有[杰克]欲租赁[汤姆]的房子-------------------------------------|");
        Contract clone4Lease = lease.clone();
        clone4Lease.signed("汤姆", "杰克");
    }

    private static void useManager() {
        ContractManager.loadCache();
        System.out.println("|==> 现有[张三]欲购置[李四]的房子-------------------------------------|");
        Contract clone4Sales = ContractManager.newInstance("sales");
        clone4Sales.signed("李四", "张三");
        System.out.println("|==> 现有[杰克]欲租赁[汤姆]的房子-------------------------------------|");
        Contract clone4Lease = ContractManager.newInstance("lease");
        clone4Lease.signed("汤姆", "杰克");
    }


}
