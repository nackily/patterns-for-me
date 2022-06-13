package com.aoligei.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * 合同管理器
 *
 * @author xg-ran
 * @date 2022-06-06 16:01:24
 * @since 1.0.0
 */
public class ContractManager {

    private static final Map<String, Contract> CACHE = new HashMap<>();

    public static void loadCache() {
        Contract sales = new SalesContract();
        CACHE.put("sales", sales);
        Contract lease = new LeaseContract();
        CACHE.put("lease", lease);
    }

    public static Contract newInstance(String key){
        Contract contract = CACHE.get(key);
        if (contract == null) {
            throw new RuntimeException("不支持的合同类型");
        }
        return contract.clone();
    }

}
