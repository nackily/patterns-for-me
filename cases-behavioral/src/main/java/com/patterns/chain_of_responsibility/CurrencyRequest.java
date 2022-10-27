package com.patterns.chain_of_responsibility;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 货币
 *
 * @author coder
 * @date 2022-07-19 11:53:02
 * @since 1.0.0
 */
public class CurrencyRequest {

    /**
     * 待分配币值
     */
    private int toAllocateAmount;

    /**
     * 已分配币值及数量
     */
    private final Map<String, Integer> allocated = new HashMap<>();

    public CurrencyRequest(int amount) {
        this.toAllocateAmount = amount;
    }

    /**
     * 分配面额
     * @param denomination 本次分配面额
     * @param allocatedNum 本次分配数量
     * @param toAllocateAmount 剩余待分配币值
     */
    public void addAllocated(String denomination, int allocatedNum, int toAllocateAmount) {
        this.toAllocateAmount = toAllocateAmount;
        allocated.put(denomination, allocatedNum);
    }

    /**
     * 出钞
     */
    public void cashOut() {
        if (toAllocateAmount > 0) {
            System.out.println(MessageFormat.format("    系统无法提供本次服务，有【{0}】元无法分配纸币",
                    toAllocateAmount));
        } else {
            // 出钞
            allocated.forEach((key, value) ->
                    System.out.println(MessageFormat.format("    面额【{0}】：【{1}】张", key, value))
            );
        }
    }


    public int getToAllocateAmount() {
        return toAllocateAmount;
    }

}
