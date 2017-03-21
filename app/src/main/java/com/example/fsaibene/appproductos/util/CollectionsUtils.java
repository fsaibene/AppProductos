package com.example.fsaibene.appproductos.util;

import com.example.fsaibene.appproductos.products.products.domain.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fsaibene on 15/3/2017.
 */

public class CollectionsUtils {
    public static <T> List<T> getPage(List<T> list, int pageNumber, int pageSize) {
        List<T> pagedItems = new ArrayList<>();
        int lowLimit, upperLimit, listSize, pagesNumber;

        // Sanidad
        if (pageSize <= 0 || pageNumber <= 0) {
            pageSize = list.size();
            pageNumber = 1;
        }

        listSize = list.size();

        // ¿La página es más grande que el contenido?
        if (listSize < pageSize && pageNumber == 1) {
            return list;
        }

        pagesNumber = listSize / pageSize;


        if (pageNumber > pagesNumber) {
            return pagedItems;
        }


        lowLimit = (pageNumber - 1) * pageSize;

        // ¿Llegamos al final?
        if (lowLimit == listSize) {
            return pagedItems;
        }

        upperLimit = lowLimit + pageSize;

        pagedItems = list.subList(lowLimit, upperLimit);

        return pagedItems;

    }
}
