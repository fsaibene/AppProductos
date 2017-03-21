package com.example.fsaibene.appproductos.selection.specification.selector;

import java.util.List;

/**
 * Created by fsaibene on 15/3/2017.
 */

public interface ListSelector<T> extends Selector{
    List<T> selectListRows(List<T> items);
}
