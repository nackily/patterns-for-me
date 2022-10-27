package com.patterns.prototype;

import com.patterns.prototype.contract.LeaseContract;
import com.patterns.prototype.contract.SalesContract;

import java.util.HashMap;
import java.util.Map;

/**
 * 合同管理器
 *
 * @author coder
 * @date 2022-06-06 16:01:24
 * @since 1.0.0
 */
public class ContractManager {

    private static final Map<String, AbstractContract> CACHE = new HashMap<>();

    public static void loadCache() {
        AbstractContract sales = new SalesContract();
        CACHE.put("sales", sales);
        AbstractContract lease = new LeaseContract();
        CACHE.put("lease", lease);
    }

    public static AbstractContract newInstance(String key){
        AbstractContract contract = CACHE.get(key);
        if (contract == null) {
            throw new RuntimeException("不支持的合同类型");
        }
        return contract.clone();
    }

}
