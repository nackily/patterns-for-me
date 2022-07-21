package com.aoligei.behavioral.chain_of_responsibility;

/**
 * 50元分配器
 *
 * @author coder
 * @date 2022-07-19 14:10:53
 * @since 1.0.0
 */
public class RMB50Allocator extends AbstractPaperCurrencyAllocator {
    @Override
    protected void allocate(CurrencyRequest request) {
        if (request.getToAllocateAmount() >= 50) {
            int allocatedNum = request.getToAllocateAmount() / 50;
            int remainingAmount = request.getToAllocateAmount() % 50;
            request.addAllocated("50", allocatedNum, remainingAmount);
        }
        if (request.getToAllocateAmount() > 0 && nextAllocator != null) {
            nextAllocator.allocate(request);
        }
    }
}
