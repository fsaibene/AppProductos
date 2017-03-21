package com.example.fsaibene.appproductos.selection.specification.selector;

import com.example.fsaibene.appproductos.selection.specification.Specification;

import java.util.HashMap;

/**
 * Created by fsaibene on 15/3/2017.
 */

public interface RetrofitSpecification extends Specification {
    RetrofitGrammar asRetrofitGrammar();
    class RetrofitGrammar{
        HashMap<String, String> headers ;
        HashMap<String, String> paths;
        HashMap<String, String> params;
    }
}
