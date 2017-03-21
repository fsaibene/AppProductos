package com.example.fsaibene.appproductos.selection.specification;

/**
 * Created by fsaibene on 10/3/2017.
 */

public interface MemorySpecification<T> extends Specification{
    boolean isSatisfiedBy(T item);
}
