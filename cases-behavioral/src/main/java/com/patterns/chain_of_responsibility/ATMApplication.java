package com.patterns.chain_of_responsibility;

import com.patterns.chain_of_responsibility.handler.RMB100Allocator;
import com.patterns.chain_of_responsibility.handler.RMB10Allocator;
import com.patterns.chain_of_responsibility.handler.RMB50Allocator;

import java.util.Scanner;

/**
 * ATM应用
 *
 * @author coder
 * @date 2022-07-18 16:54:42
 * @since 1.0.0
 */
public class ATMApplication {

    private static final AbstractPaperCurrencyAllocator ALLOCATOR  = new RMB100Allocator();;
    static {
        // 【RMB100Allocator -> RMB50Allocator -> RMB10Allocator】
        AbstractPaperCurrencyAllocator rmb50 = new RMB50Allocator();
        AbstractPaperCurrencyAllocator rmb10 = new RMB10Allocator();
        ALLOCATOR.setNextAllocator(rmb50);
        rmb50.setNextAllocator(rmb10);
        // 【RMB100Allocator -> RMB50Allocator】
        // AbstractPaperCurrencyAllocator rmb50 = new RMB50Allocator();
        // ALLOCATOR.setNextAllocator(rmb50);
    }

    public static void main(String[] args) {
        while (true) {
            int amount;
            System.out.println("|==> 输入一个需要提取的金额：-------------------------------------|");
            Scanner s = new Scanner(System.in);
            amount = s.nextInt();
            if (amount % 10 > 0) {
                System.out.println("    错误的金额，只支持输入 10 的倍数");
            } else {
                CurrencyRequest req = new CurrencyRequest(amount);
                ALLOCATOR.allocate(req);
                req.cashOut();
            }
        }
    }
}
